package com.hxy.isw.service.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hxy.isw.entity.DeliveryApply;
import com.hxy.isw.entity.Designation;
import com.hxy.isw.entity.ExpressageInfo;
import com.hxy.isw.entity.Giftbox;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.MachineUsed;
import com.hxy.isw.entity.ShippingAddress;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.ToysPhotos;
import com.hxy.isw.entity.ToysType;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.VideoInfo;
import com.hxy.isw.service.AppServiceToys;
import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.KdniaoTrackQueryAPI;
import com.hxy.isw.util.Sys;
@Repository
public class AppServiceToysImpl implements AppServiceToys {

	/*发送数据缓冲区*/  
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);  
	
	@Autowired
	DatabaseHelper databaseHelper;
	@Override
	public Map<String, Object> queryGiftbox(String userid, Integer state, Integer start, Integer limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(g.id) from giftbox g where g.state=").append(state)
				.append(" and g.fuserinfoid=").append(Long.parseLong(userid));
		//int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int count =databaseHelper.getSqlCount(buffer.toString());
		
		int pages = ConstantUtil.pages(count, limit);
		if(count>0){
			StringBuffer hql = new StringBuffer("select g from Giftbox g where g.state=").append(state)
					.append(" and g.fuserinfoid=").append(Long.parseLong(userid));
			List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
			for (Object objects : lst){
				Giftbox g= (Giftbox) objects;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("giftboxid", g.getId());
				map.put("toysname", g.getToysname());
				map.put("photo", g.getPhoto());
				map.put("time", g.getCreatetime().toString());
				map.put("machineno", g.getMachineno());
				map.put("state", g.getState());
				rowList.add(map);
				
			}
		}
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", rowList);
		return resultMap;
	}
	@Override
	public String queryexpressage(String giftboxid) throws Exception {
		
		StringBuffer buffer = new StringBuffer("select e from ExpressageInfo e where e.fgiftboxid=").append(Long.parseLong(giftboxid));
		ExpressageInfo expressageinfo = new ExpressageInfo();
		List<Object> lst1=databaseHelper.getResultListByHql(buffer.toString());
		if(lst1.size()==0)throw new Exception("发货人未填写物流信息");
		if(lst1.size()>0){
			expressageinfo=(ExpressageInfo)lst1.get(0);
		}
		ExpressageInfo oi = (ExpressageInfo)databaseHelper.getObjectById(ExpressageInfo.class, expressageinfo.getId());
		if(oi==null)throw new Exception("订单信息错误");
		
		
		//查看订单物流
//		String hql = "select expressageinfo from  ExpressageInfo expressageinfo where expressageinfo.id="+expressageinfo1.getId();
//		List<Object> lst = databaseHelper.getResultListByHql(hql);
//		if(lst.size()==0)throw new Exception("发货人未填写物流信息");
//		
//		ExpressageInfo expressageinfo = (ExpressageInfo)lst.get(0);
		String result = "";
		
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		result = api.getOrderTracesByJson(expressageinfo.getExpressagecode(), expressageinfo.getExpressageno());
		return result;
		
	}
	@Override
	public Map<String, Object> searchToystype(Integer start, Integer limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(1) from toystype t where t.state=0");
		int count =databaseHelper.getSqlCount(buffer.toString());
		int pages = ConstantUtil.pages(count, limit);
		if(count>0){
			StringBuffer hql = new StringBuffer("select t from ToysType t where t.state=0");
			List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
			for(Object objects:lst){
				ToysType t = (ToysType) objects;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("toystypeid", t.getId());
				map.put("name", t.getName());
				rowList.add(map);
			}
		}
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", rowList);
		return resultMap;
	}
	@Override
	public Map<String, Object> searchToysdetail(String mechinesid) {
		StringBuffer hql = new StringBuffer("select t from ToysInfo t,MachineInfo m where t.id = m.ftoysid and m.id=").append(Long.parseLong(mechinesid))
				.append(" and m.state<>-1 and t.state=0");
				
		List<Object> lst=databaseHelper.getResultListByHql(hql.toString());
		ToysInfo t1 = (ToysInfo) lst.get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg","success");
		map.put("info", "获取玩具详情成功");
		map.put("toysname",t1.getName());
		map.put("specifications", t1.getSpecifications());
		map.put("size", t1.getSize());
		map.put("material", t1.getMaterial());
		map.put("weight", t1.getWeight());
		map.put("price", t1.getPrice());
	StringBuffer queryphotos = new StringBuffer("select tp from ToysPhotos tp where tp.ftoysid = ").append(t1.getId());
	List<Object>tpList = databaseHelper.getResultListByHql(queryphotos.toString());
	List<Map<String, Object>>tpListmap = new ArrayList<Map<String,Object>>();
	for (Object object : tpList) {
		ToysPhotos tp = (ToysPhotos) object;
		Map<String, Object>pmap = new HashMap<String, Object>();
		pmap.put("id", tp.getId());
		pmap.put("toysid", tp.getFtoysid());
		pmap.put("photourl", tp.getPhotourl());
		tpListmap.add(pmap);
	}
		map.put("photos", tpListmap);
		return map;
	}
	@Override
	public Map<String, Object> searchApplydelivery(String giftboxid,String userid) throws Exception {
		//giftboxid无效   废弃
		
		StringBuffer buffer = new StringBuffer("select d from ShippingAddress d where d.common = 1 and d.fuserinfoid=").append(Long.parseLong(userid));
		List<Object> lst =databaseHelper.getResultListByHql(buffer.toString());
		if(lst.size()==0)throw new Exception("你还没有设置默认收获地址");
		
		ShippingAddress adress =(ShippingAddress)lst.get(0);
		
		StringBuffer query = new StringBuffer("select g from Giftbox g where g.state = 0 and g.fuserinfoid=").append(Long.parseLong(userid));
		List<Object> querylst =databaseHelper.getResultListByHql(query.toString()); 
		Date date=new Date();
		for (Object object : querylst) {
			Giftbox g = (Giftbox)object;
			DeliveryApply apply = new DeliveryApply();
			apply.setFgiftboxid(g.getId());
			apply.setConsigneename(adress.getConsigneename());
			apply.setConsigneemobile(adress.getConsigneemobile());
			apply.setProvince(adress.getProvince());
			apply.setCity(adress.getCity());
			apply.setArea(adress.getArea());
			apply.setAddress(adress.getAddress());
			apply.setFuserinfoid(Long.parseLong(userid));
			apply.setState(0);
			apply.setCreatetime(date);
			databaseHelper.persistObject(apply);
		
			g.setState(1);
			databaseHelper.updateObject(g);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "success");
		map.put("info", "申请发货成功");
		return map;
		

}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized String play(String userid,String machineid,String operation,String direction,String visualangle) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		if(machineid==null||machineid.length()==0)throw new Exception("machineid错误");
		if(operation==null||operation.length()==0)throw new Exception("参数operation错误"); //move--动   stop--停   play--抓
		if(visualangle==null||visualangle.length()==0)throw new Exception("参数visualangle错误");
		
		//direction ：方向    former--向前    backward--向后    aleft-- 向左   aright-- 向右   
		
		 //检查用户在此机器的状态
        StringBuffer queryisused = new StringBuffer("select mu from MachineUsed mu where mu.fuserinfoid = ")
        		.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machineid));
        List<Object> muList = databaseHelper.getResultListByHql(queryisused.toString());
        
        if(muList.size()==0||((MachineUsed)muList.get(0)).getState()!=2)throw new Exception("请先点击开始游戏按钮");
        
        MachineInfo machineInfo = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machineid));
        
        if(NIOSServer.clientsLMap.get(Long.parseLong(machineInfo.getMachineno()))==null)throw new Exception("娃娃睡着了，正在叫醒。。。");
        
        //第一位  1 预留   --- （此意义已废弃）
      	//第二位  0投币  1移动  2停止  3抓取---（此意义已废弃）
        operation = "move".equals(operation)?"1":"stop".equals(operation)?"2":"play".equals(operation)?"3":null;
        if(operation==null)throw new Exception("参数operation错误");
        
        if(direction!=null&&direction.length()>0){
	        if("1".equals(visualangle)){
	        	//视角为正面时，前后左右为正常方向
	        }else{
	        	//视角为侧面时，前后左右要重新解析
	        	if("former".equals(direction))direction = "aleft";//侧面向前=正面向左
	        	else if("backward".equals(direction))direction = "aright";//侧面向后=正面向右
	        	else if("aleft".equals(direction))direction = "backward";//侧面向左=正面向后
	        	else if("aright".equals(direction))direction = "former";//侧面向右=正面向前
	        }
        }
        //第三位  1 向前  2向后 3向左  4向右  0无----（此意义已废弃）
        direction = "former".equals(direction)?"1":"backward".equals(direction)?"2":"aleft".equals(direction)?"3":"aright".equals(direction)?"4":null;
        //if(direction==null)throw new Exception("参数direction错误");
        
        String info = "{1"+operation+""+direction+"}";
        
        //最新定义   P--投币  F--前移  B--后移  L--左移  R--右移  S--停  D--抓
        if("2".equals(operation)){//停
        	info = "SS";
        }else if("3".equals(operation)){//抓
        	info = "DD";
        }else if("1".equals(operation)){//移动
        	if(direction==null)throw new Exception("参数direction错误");
        	if("1".equals(direction)){//向前
        		info = "FF";
        	}else if("2".equals(direction)){//向后
        		info = "BB";
        	}else if("3".equals(direction)){//向左
        		info = "LL";
        	}else if("4".equals(direction)){//向右
        		info = "RR";
        	}
        }
        MachineUsed mu = (MachineUsed)muList.get(0);
		sBuffer.clear();  
        sBuffer.put(info.getBytes());  
        sBuffer.flip(); 
        Sys.out("NIOSServer.clientsLMap.size..."+NIOSServer.clientsLMap.size());
        
    	Sys.out("send to machine..machineno:"+machineInfo.getMachineno()+"..info..."+info);
    	try {
			NIOSServer.clientsLMap.get(Long.parseLong(machineInfo.getMachineno())).write(sBuffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			handlePipe(e, machineInfo);
		}
    	sBuffer.clear();  
    	
    	if("DD".equals(info)){//抓取时更改娃娃机使用状态
    		mu.setCreatetime(new Date());
    		mu.setState(3);
    		databaseHelper.updateObject(mu);
    		
    		
    		NIOSServer.savemchuser.put(machineInfo.getMachineno(), Long.parseLong(userid));
    	}

        Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "success");
		map.put("info", "指令发送成功");
		String json =  new Gson().toJson(map);
		
		return json;
    		
		
	}
	
	private void handlePipe(Exception e,MachineInfo mi){
		String mess = e.getMessage();
		if(mess.indexOf("Broken pipe")!=-1){
			Iterator<Map.Entry<Long, SocketChannel>> it = NIOSServer.clientsLMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Long, SocketChannel> entry = it.next();
				if (entry.getKey().equals(Long.parseLong(mi.getMachineno()))) {
					Sys.out("delete this key: " + mi.getMachineno() + " = " + entry.getValue());
					it.remove(); // OK
				}
			}
		}
	}
	@Override
	public String uploadvideo(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		JsonObject jObject = paresData(request);
		
		Long userid = jObject.get("userid")==null?null:jObject.get("userid").getAsLong();
		Long machineid = jObject.get("machineid")==null?null:jObject.get("machineid").getAsLong();
		int successed = jObject.get("successed")==null?0:jObject.get("successed").getAsInt();
		
		if(userid==null)throw new Exception("参数userid错误");
		if(machineid==null)throw new Exception("参数machineid错误");
		
		int i = jObject.get("i")==null?0:jObject.get("i").getAsInt();
		if(i==0)throw new Exception("视频未正常上传");
		
		String vedioUrl = jObject.get("filename"+i).getAsString();
		
		VideoInfo vi = new VideoInfo();
		vi.setFmachineid(machineid);
		vi.setFuserid(userid);
		vi.setState(0);
		vi.setTime(new Date());
		vi.setVideourl(vedioUrl);
		vi.setSuccessed(successed);
		String hql="select t from ToysInfo t where  t.id =(select m.ftoysid from MachineInfo m where m.id="+machineid+" )";
		ToysInfo ts=(ToysInfo) databaseHelper.getResultListByHql(hql).get(0);
		vi.setToyname(ts.getName());
		vi.setToyphoto(ts.getPhoto());
		databaseHelper.persistObject(vi);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "success");
		map.put("info", "视频上传成功");
		map.put("videourl",ConstantUtil.server_url+vedioUrl);
		String json =  new Gson().toJson(map);
		
		return json;
	}

	
	private JsonObject paresData(HttpServletRequest request) throws Exception{
		JsonObject obj = new JsonObject();
		String savepath = "/video";
		String newfile = "";
		//获得磁盘文件条目工厂。  
		DiskFileItemFactory factory = new DiskFileItemFactory();  
		//获取文件上传需要保存的路径，upload文件夹需存在。  
//    String path =  ConstantUtil.PROJECT_PATH.replace("target/classes/", "src/main/webapp/upload");  
		String path =  ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", savepath);  
		//设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。  
		factory.setRepository(new File(path));  
		//设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
		factory.setSizeThreshold(10*1024*1024);  
		//上传处理工具类（高水平API上传处理？）  
		ServletFileUpload upload = new ServletFileUpload(factory);  
//     Sys.out("request.get.."+request.getParameter("zcj"));
		//调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。  
		List<FileItem> list = (List<FileItem>)upload.parseRequest(request);  
		Sys.out("List<FileItem> list.size:"+list.size());
		
		int i = 1;
		for(FileItem item:list){  
		    //获取表单属性名字。  
		    String name = item.getFieldName();  
		    //如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
		    if(item.isFormField()){  
		        //获取用户具体输入的字符串，  
		        String value = item.getString();  
		        //request.setAttribute(name, value);  
		        Sys.out("name:"+name+",value:"+value);
		        obj.addProperty(name, value);
		    }  
		    //如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
		    else{   
		    	//Map<String,String> map = new HashMap<String,String>();
		        //获取路径名  
		        String value = item.getName();  
		        //取到最后一个反斜杠。  
		        int start = value.lastIndexOf("\\");  
		        //截取上传文件的 字符串名字。+1是去掉反斜杠。  
		        String filename = value.substring(start+1);  
		        filename = new Date().getTime() + filename.substring(filename.indexOf("."));
		        request.setAttribute(name, filename);  
		          
		        /*第三方提供的方法直接写到文件中。 
		         * item.write(new File(path,filename));*/  
		        //收到写到接收的文件中。  
		        OutputStream out = new FileOutputStream(new File(path,filename));  
		        InputStream in = item.getInputStream();  
		          
		        int length = 0;  
		        byte[] buf = new byte[1024*1024*10];  
		        Sys.out("size:"+ item.getSize());  
		        newfile = filename;
		        
		        while((length = in.read(buf))!=-1){  
		            out.write(buf,0,length);  
		        }  
		        in.close();  
		        out.close();  
		        
		        Sys.out("newfile:"+ savepath+"/"+newfile);  
		        obj.addProperty("filename"+i, savepath+"/"+newfile);
		        i++;
		    }  
		}  
		obj.addProperty("i", i-1);
		
		return obj;
	}
}