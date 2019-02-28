package com.hxy.isw.service.impl;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.Consumption;
import com.hxy.isw.entity.Designation;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.MachineUsed;
import com.hxy.isw.entity.MachineUserMark;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.UserCollection;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.AppServiceMachine;
import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Repository
public class AppServiceMachineImpl implements AppServiceMachine{
	@Autowired
	DatabaseHelper databaseHelper;
	
	/*发送数据缓冲区*/  
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);  
	
	@Override
	public String machines(String userid, String toystypeid, String orderby, String liftby, Integer start,
			Integer limit)throws Exception{
		// TODO Auto-generated method stub
        if(userid==null||userid.length()==0)throw new Exception("用户id错误");
       
		int count = countmachine(userid,toystypeid);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		 
		if(count>0){
			StringBuffer query = new StringBuffer();
			if(toystypeid==""||toystypeid==null){
				 query = new StringBuffer("select mi,ti from MachineInfo mi,ToysInfo ti where mi.state >-1 and ti.id = mi.ftoysid and mi.ftoysid is not null ");
					
				/*if(Integer.parseInt(liftby)==0)query.append(" order by mi.popularity asc ");
				if(Integer.parseInt(liftby)==1)query.append(" order by mi.popularity desc ");*/
				 
				 //update by lixq 2017年9月22日09:39:10
				 if(orderby.equals("1")){//人气排序
					 if(liftby.equals("0")){
						 query.append(" order by mi.popularity asc ");
					 }else{
						 query.append(" order by mi.popularity desc ");
					 }
				 }else if(orderby.equals("2")){//价格排序
					 if(liftby.equals("0")){
						 query.append(" order by mi.price asc ");
					 }else{
						 query.append(" order by mi.price desc ");
					 }
				 }else{//默认
					 if(liftby.equals("0")){
						 query.append(" order by mi.id asc ");
					 }else{
						 query.append(" order by mi.id desc ");
					 }
				 }
				 
			} else if(toystypeid.equals("0")){
				 query = new StringBuffer("select mi,ti from MachineInfo mi,ToysInfo ti where mi.state >-1 and mi.ftoysid is not null and ti.id = mi.ftoysid ")
							.append("and mi.id in (select uc.fmachineid from UserCollection uc where uc.fuserid="+userid+" and uc.state =0 )");
						 if(orderby.equals("1")){//人气排序
							 if(liftby.equals("0")){
								 query.append(" order by mi.popularity asc ");
							 }else{
								 query.append(" order by mi.popularity desc ");
							 }
						 }else if(orderby.equals("2")){//价格排序
							 if(liftby.equals("0")){
								 query.append(" order by mi.price asc ");
							 }else{
								 query.append(" order by mi.price desc ");
							 }
						 }else{//默认
							 if(liftby.equals("0")){
								 query.append(" order by mi.id asc ");
							 }else{
								 query.append(" order by mi.id desc ");
							 }
						 }
			}
			
			
			else{
				 query = new StringBuffer("select mi,ti from MachineInfo mi,ToysInfo ti where mi.state >-1 and mi.ftoysid is not null and ti.id = mi.ftoysid and ti.ftoystypeid = ")
							.append(Long.parseLong(toystypeid));
					/*	if(Integer.parseInt(liftby)==0)query.append(" order by mi.popularity asc ");
						if(Integer.parseInt(liftby)==1)query.append(" order by mi.popularity desc ");*/
						
						//update by lixq 2017年9月22日09:39:10
						 if(orderby.equals("1")){//人气排序
							 if(liftby.equals("0")){
								 query.append(" order by mi.popularity asc ");
							 }else{
								 query.append(" order by mi.popularity desc ");
							 }
						 }else if(orderby.equals("2")){//价格排序
							 if(liftby.equals("0")){
								 query.append(" order by mi.price asc ");
							 }else{
								 query.append(" order by mi.price desc ");
							 }
						 }else{//默认
							 if(liftby.equals("0")){
								 query.append(" order by mi.id asc ");
							 }else{
								 query.append(" order by mi.id desc ");
							 }
						 }
						
			}
			
			List<Object[]> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);

			for (Object[] object : lst) {
				MachineInfo mi = (MachineInfo)object[0];
				ToysInfo ti = (ToysInfo)object[1];
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("machinesid", mi.getId());
				map.put("machineno", mi.getMachineno());// 设备编号
				map.put("usedstate", mi.getState());
				map.put("isnew", mi.getIsnew());
				map.put("toysname",ti.getName());
				map.put("toysphoto",ti.getPhoto());
				map.put("price",mi.getPrice());
			
				lstMap.add(map);
			}
		}
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}

	private int countmachine(String userid,String toystypeid){
		StringBuffer count = new StringBuffer();
		if(toystypeid==""||toystypeid==null){
			 count = new StringBuffer("select count(mi.id) from MachineInfo mi,ToysInfo ti where mi.state >-1 and ti.id = mi.ftoysid and mi.ftoysid is not null");
		}else if(toystypeid.equals("0")){
			count =new StringBuffer("select count(mi.id) from MachineInfo mi where mi.state >-1 and mi.ftoysid is not null  ");
			count.append("and mi.id in (select uc.fmachineid from UserCollection uc where uc.fuserid="+userid+" and uc.state =0 )");
		}
		
		else{
			count = new StringBuffer("select count(mi.id) from MachineInfo mi,ToysInfo ti where mi.state >-1 and ti.id = mi.ftoysid and mi.ftoysid is not null and ti.ftoystypeid = ")
					.append(Long.parseLong(toystypeid));
		}
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String machinedetail(String userid, String machinesid) throws Exception {
		// TODO Auto-generated method stub
		 if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		 
		 UserInfo userInfo = (UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		 MachineInfo machineInfo = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machinesid));
		 String consql="select count(id) from usercollection where fuserid ="+userid+" and fmachineid ="+machinesid+"  and state = 0";
		 int count =databaseHelper.getSqlCount(consql);
		    Map<String,Object> map = new HashMap<String,Object>();
			map.put("msg", "success");
			map.put("info", "消息");
			map.put("facevideourl", machineInfo.getFacevideo());
			map.put("sidevideourl", machineInfo.getSidevideo());
			
			StringBuffer find =  new StringBuffer("select mu from MachineUsed mu where mu.state = 1 ").append(" and mu.fmachineid = ").append(machineInfo.getId());
			List<Object> flst = databaseHelper.getResultListByHql(find.toString());
			
			map.put("subscribe",flst.size());
			map.put("machineno", machineInfo.getMachineno());// 设备编号
			map.put("views", machineInfo.getViews());
			map.put("price", machineInfo.getPrice());
			map.put("userbalance",userInfo.getBalance());
            map.put("userfreevoucher", userInfo.getFreevoucher());
            map.put("state", machineInfo.getState());//0空闲 1正在使用
            map.put("iscollection", count>0?"1":"0");
            //检查用户在此机器的状态
            StringBuffer queryisused = new StringBuffer("select mu from MachineUsed mu where mu.fuserinfoid = ")
            		.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machinesid));
            List<Object> muList = databaseHelper.getResultListByHql(queryisused.toString());
            
            if(muList.size()>0){
            	MachineUsed machineUsed = (MachineUsed) muList.get(0);
            	map.put("issubscribe", machineUsed.getState());
            	if(machineUsed.getState()==1){//如果是预约状态，则给出当前用户的排序
            		map.put("indexs", machineUsed.getIndexs());
            		if(machineUsed.getIndexs()==0){//
            			long t1 = machineUsed.getCreatetime().getTime();//用户预约到号时的时间
            			long t2 = new Date().getTime();//当前时间
            			
            			long diff = t2-t1<0l?0l:(t2-t1)/1000l;
            			
            			map.put("countdown", ConstantUtil.countdownofstartplay-diff<0?0:ConstantUtil.countdownofstartplay-diff);
            		}
            	}else if(machineUsed.getState()==2){//如果是游戏状态
            		
        			long t1 = machineUsed.getCreatetime().getTime();//用户结束一局时的时间
        			long t2 = new Date().getTime();//当前时间
        			
        			long diff = t2-t1<0l?0l:(t2-t1)/1000l;
        			
        			map.put("countdown", ConstantUtil.gametime-diff<0?0:ConstantUtil.gametime-diff);
            		
            	}else if(machineUsed.getState()==3){//如果是一局结束后等待的状态
            		
        			long t1 = machineUsed.getCreatetime().getTime();//用户结束一局时的时间
        			long t2 = new Date().getTime();//当前时间
        			
        			long diff = t2-t1<0l?0l:(t2-t1)/1000l;
        			
        			map.put("countdown", ConstantUtil.countdownofrestartplay+ConstantUtil.countdownofresset-diff<0?0:ConstantUtil.countdownofrestartplay+ConstantUtil.countdownofresset-diff);
            		
            	}
   		   }
            if(machineInfo.getFtoysid()!=null){
            ToysInfo toysInfo = (ToysInfo) databaseHelper.getObjectById(ToysInfo.class, machineInfo.getFtoysid());
            StringBuffer queryliketoy = new StringBuffer("select mi,t,tt from MachineInfo mi,ToysInfo t,ToysType tt where mi.ftoysid = t.id and t.ftoystypeid = tt.id and tt.id = ")
            		.append(toysInfo.getFtoystypeid()).append(" and mi.id != ").append(machinesid);
            List<Map<String,Object>> lstMap1 = new ArrayList<Map<String,Object>>();
            List<Object[]>tList = databaseHelper.getResultListByHql(queryliketoy.toString());
            for (Object[] objects : tList) {
            	MachineInfo m = (MachineInfo)objects[0];
				ToysInfo t = (ToysInfo) objects[1];
				Map<String, Object>map1 = new HashMap<String, Object>();
				map1.put("machinesid", m.getId());
				map1.put("toysname",t.getName());
				map1.put("toysphoto",t.getPhoto());
				lstMap1.add(map1);
			}
            map.put("liketoy", lstMap1);
            }
			String json = JsonUtil.getGson().toJson(map);
			
			return json;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String views(String userid, String machinesid) throws Exception {
		// TODO Auto-generated method stub
		 if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		 if(machinesid==null||machinesid.length()==0)throw new Exception("machinesid错误");
		 
		StringBuffer querymused = new StringBuffer("select mu from MachineUsed mu where mu.fuserinfoid = ")
				.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machinesid));
		List<Object>muList = databaseHelper.getResultListByHql(querymused.toString());
		if(muList.size()==0){
			MachineUsed machineUsed = new MachineUsed();
			machineUsed.setCreatetime(new Date());
			machineUsed.setFmachineid(Long.parseLong(machinesid));
			machineUsed.setFuserinfoid(Long.parseLong(userid));
			machineUsed.setIndexs(0);
			machineUsed.setState(0);
			machineUsed.setUpdatetime(new Date());
			databaseHelper.persistObject(machineUsed);
		}else{
			MachineUsed machineUsed = (MachineUsed)muList.get(0);
			if(machineUsed.getState()==-1){
				machineUsed.setState(0);
				machineUsed.setIndexs(0);
				machineUsed.setCreatetime(new Date());
				machineUsed.setUpdatetime(new Date());
				databaseHelper.updateObject(machineUsed);
			}
		}
		MachineUserMark mUserMark = new MachineUserMark();
		mUserMark.setCreatetime(new Date());
		mUserMark.setFmachineid(Long.parseLong(machinesid));
		mUserMark.setFuserinfoid(Long.parseLong(userid));
		mUserMark.setType(0);
		databaseHelper.persistObject(mUserMark);
		
		MachineInfo machineInfo = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machinesid));
		machineInfo.setViews(machineInfo.getViews()+1);
		machineInfo.setPopularity(machineInfo.getPopularity()+1);
		databaseHelper.updateObject(machineInfo);
		
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("msg", "success");
		map.put("info", "进入房间");
		
		String json = JsonUtil.getGson().toJson(map);
		
		return json;
	}

	@Override
	public String exitroom(String userid, String machinesid) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		if(machinesid==null||machinesid.length()==0)throw new Exception("用户id错误");
		MachineInfo machineInfo = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machinesid));
		machineInfo.setViews((machineInfo.getViews()-1)<0?0:(machineInfo.getViews()-1));
		databaseHelper.updateObject(machineInfo);
		
		StringBuffer querymused = new StringBuffer("select mu from MachineUsed mu where mu.fuserinfoid = ")
				.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machinesid));
		List<Object> muList = databaseHelper.getResultListByHql(querymused.toString());
		if(muList.size()>0){
			MachineUsed machineUsed = (MachineUsed)muList.get(0);
			if(machineUsed.getState()==0){
				machineUsed.setCreatetime(new Date());
				machineUsed.setState(-1);
				databaseHelper.updateObject(machineUsed);
			}
		}
		
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("msg", "success");
		map.put("info", "退出房间");
		
		String json = JsonUtil.getGson().toJson(map);
		
		return json;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized String subscribe(String userid, String machinesid) throws Exception {
		// TODO Auto-generated method stub
		 if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		 //如果机器是空闲，直接开始游戏，不需要预约
		 MachineInfo mi = (MachineInfo)databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machinesid));
		 if(mi.getState()==0)throw new Exception("目前机器处于空闲状态，可以直接投币");
		 
		 //查看此机器当前预约排号
		 StringBuffer queryindex = new StringBuffer("select mu from MachineUsed mu where mu.state = 1 ")
					.append(" and mu.fmachineid = ").append(Long.parseLong(machinesid)).append(" order by mu.indexs desc");
		 List<Object> indexList = databaseHelper.getResultListByHql(queryindex.toString());
		 
		 //当前预约排号
		 int indexs = indexList.size()==0?0:((MachineUsed)indexList.get(0)).getIndexs();
		 
		 //查看当前用户在此房间所处的状态
		 StringBuffer querymused = new StringBuffer("select mu from MachineUsed mu where  mu.fuserinfoid = ")
					.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machinesid));
			List<Object>muList = databaseHelper.getResultListByHql(querymused.toString());
			
			if(muList.size()>0){
				MachineUsed machineUsed = (MachineUsed) muList.get(0);
				
				if(machineUsed.getState()==1)throw new Exception("你已预约，请不要重复预约");
				if(machineUsed.getState()==2)throw new Exception("你已经开始游戏了，不能预约");
				
				machineUsed.setState(1);
				machineUsed.setIndexs(indexs+1);
				machineUsed.setCreatetime(new Date());
				//mi.setSubscribe(mi.getSubscribe()+1);
				databaseHelper.updateObject(machineUsed);
				
			}else{
				MachineUsed mUsed = new MachineUsed();
				mUsed.setCreatetime(new Date());
				mUsed.setFmachineid(Long.parseLong(machinesid));
				mUsed.setFuserinfoid(Long.parseLong(userid));
				mUsed.setState(1);
				mUsed.setIndexs(indexs+1);
				//mi.setSubscribe(mi.getSubscribe()+1);
				
				databaseHelper.persistObject(mUsed);
			}
			
			//将机器状态置为使用中
			mi.setState(1);
			databaseHelper.updateObject(mi);
			
			MachineUserMark mUserMark = new MachineUserMark();
			mUserMark.setCreatetime(new Date());
			mUserMark.setFmachineid(Long.parseLong(machinesid));
			mUserMark.setFuserinfoid(Long.parseLong(userid));
			mUserMark.setType(1);
			databaseHelper.persistObject(mUserMark);
			
			Map<String,Object>map = new HashMap<String,Object>();
			map.put("msg", "success");
			map.put("info", "预约成功");
			map.put("indexs", indexs);
			
			String json = JsonUtil.getGson().toJson(map);
			
			return json;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String cancelsubscribe(String userid, String machinesid) throws Exception {
		// TODO Auto-generated method stub
		 if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		 StringBuffer querymused = new StringBuffer("select mu from MachineUsed mu where mu.fuserinfoid = ")
					.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machinesid));
			List<Object> muList = databaseHelper.getResultListByHql(querymused.toString());
			
			if(muList.size()==0)throw new Exception("你没有预约，不能取消");
			
			MachineInfo mi = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machinesid));
			if(muList.size()>0){
				MachineUsed machineUsed = (MachineUsed) muList.get(0);
				
				if(machineUsed.getState()!=1)throw new Exception("你不是预约状态，不能取消");
				
				//找出所有预约排在此用户之后的用户
				StringBuffer find =  new StringBuffer("select mu from MachineUsed mu where mu.state = 1 ")
						.append(" and mu.fmachineid = ").append(Long.parseLong(machinesid)).append(" and mu.indexs > ").append(machineUsed.getIndexs());
				List<Object> lst = databaseHelper.getResultListByHql(find.toString());
				for (Object object : lst) {
					MachineUsed machineused = (MachineUsed)object;
					
					//将这些用户的排号前移
					machineused.setIndexs((machineused.getIndexs()-1)<0?0:(machineused.getIndexs()-1));
					machineused.setCreatetime(new Date());
					databaseHelper.updateObject(machineused);
					
					//通知到号的用户
					if(machineused.getIndexs()<=0){
						
						String info = "{\"type\":\"1\",\"info\":\"轮到你开始游戏了\",\"countdown\":\"15\"}";
						
						sBuffer.clear();  
				        sBuffer.put(info.getBytes());  
				        sBuffer.flip(); 
				        Sys.out("NIOSServer.clientsLMap.size..."+NIOSServer.clientsLMap.size());
				        try {
							if(NIOSServer.clientsLMap.get(machineused.getFuserinfoid())!=null){
								Sys.out("send to user..userid:"+machineused.getFuserinfoid()+"...info..."+info);
								NIOSServer.clientsLMap.get(machineused.getFuserinfoid()).write(sBuffer);
								sBuffer.clear();  
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							handlePipeofApp(e, machineused);
							sBuffer.clear();
						}
				        
					}
				}
				
				//如果此用户之后没有用户在预约状态
				if(lst.size()==0){
					//检查机器有没有用户正在使用
					querymused = new StringBuffer("select mu from MachineUsed mu where mu.fmachineid = ").append(Long.parseLong(machinesid))
							.append(" and mu.state > 1 ");
					List ul = databaseHelper.getResultListByHql(querymused.toString());
					//如果没有，则将机器状态置为空闲
					if(ul.size()==0)mi.setState(0);
				}
				
				machineUsed.setState(0);
				machineUsed.setIndexs(0);
				machineUsed.setCreatetime(new Date());
				mi.setSubscribe(mi.getSubscribe()-1<0?0:mi.getSubscribe()-1);
				databaseHelper.updateObject(machineUsed);
				databaseHelper.updateObject(mi);
			}
			
			Map<String,Object>map = new HashMap<String,Object>();
			map.put("msg", "success");
			map.put("info", "取消成功");
			
			String json = JsonUtil.getGson().toJson(map);
			
			return json;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized void use(String userid, String machinesid) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		if(machinesid==null||machinesid.length()==0)throw new Exception("machinesid错误");
		
		ReentrantLock takeLock = new ReentrantLock();  
		
		//takeLock.lock();
		
		MachineInfo mi = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machinesid));
		
		if(NIOSServer.clientsLMap.get(Long.parseLong(mi.getMachineno()))==null)throw new Exception("娃娃睡着了，正在叫醒。。。");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		//检查用户是否有足够的喵币和免玩券
		if(ui.getFreevoucher()<1){
			if(ui.getBalance()<mi.getPrice())throw new Exception("你的喵币不足，请充值");
		}
		StringBuffer querymused = new StringBuffer("select mu from MachineUsed mu where mu.fuserinfoid = ")
					.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machinesid));
		List<Object> muList = databaseHelper.getResultListByHql(querymused.toString());
		
		//如果机器正在使用
		if(mi.getState()==1){
			
			if(muList.size()==0)throw new Exception("机器正在使用中，你不能开始游戏");
			
			MachineUsed mu = (MachineUsed)muList.get(0);
			
			//如果此用户处于观看中
			if(mu.getState()==0)throw new Exception("机器正在使用中，你需要先预约");
			
			//如果此用户处于预约状态且没有到号
			if(mu.getState()==1&&mu.getIndexs()>0)throw new Exception("你还在排队中，请耐心等待");
			
			//如果此用户处于游戏中
			if(mu.getState()==2)throw new Exception("你已经在游戏了");
			
			//如果用户处于一局结束等待状态时，需要等待复位
			if(mu.getState()==3){
				long t1 = mu.getCreatetime().getTime();//用户结束一局时的时间
				long t2 = new Date().getTime();//当前时间
				
				long diff = (t2-t1)/1000l;
				if(diff<ConstantUtil.countdownofresset)throw new Exception("请等待娃娃机复位");
			}
		}
		
		//将机器状态置为使用中
		mi.setState(1);
		databaseHelper.updateObject(mi);
		
		//将用户在此机器中的状态置为游戏中
		MachineUsed machineUsed = null;
		if(muList.size()>0){
			machineUsed = (MachineUsed) muList.get(0);
			
			machineUsed.setState(2);
			machineUsed.setCreatetime(new Date());
			databaseHelper.updateObject(machineUsed);
			
			
		}else{
			machineUsed = new MachineUsed();
			machineUsed.setCreatetime(new Date());
			machineUsed.setFmachineid(Long.parseLong(machinesid));
			machineUsed.setFuserinfoid(Long.parseLong(userid));
			machineUsed.setIndexs(0);
			machineUsed.setState(2);
			
			databaseHelper.persistObject(machineUsed);
		}
		
		
		
		MachineUserMark mUserMark = new MachineUserMark();
		mUserMark.setCreatetime(new Date());
		mUserMark.setFmachineid(Long.parseLong(machinesid));
		mUserMark.setFuserinfoid(Long.parseLong(userid));
		mUserMark.setType(2);
		databaseHelper.persistObject(mUserMark);
		
		//
		
		//有免玩券就优先使用
		if(ui.getFreevoucher()<1){
			//更新用户的喵币
			ui.setBalance(ui.getBalance()-mi.getPrice());
			//添加到消费明细表
			Consumption consumption = new Consumption();
			consumption.setBalance(ui.getBalance());
			consumption.setCreatetime(new Date());
			consumption.setFmachineid(mi.getId());
			consumption.setFuserinfoid(ui.getId());
			consumption.setGolds(mi.getPrice());
			consumption.setOrderno("");
			consumption.setType(-1);
			databaseHelper.persistObject(consumption);
		}else{
			//更新用户的免玩券数量
			ui.setFreevoucher(ui.getFreevoucher()-1);
		}
		
		
		
	
		ui.setIsplay(1);
		ui.setUsingmachineid(Long.parseLong(machinesid));
		long number =ui.getExperiencenum()+1;
		ui.setExperiencenum(ui.getExperiencenum()+1);
        String sql1= "select d FROM Designation d where d.state=0 order by d.experience  asc ";
       
        List<Object> lst=databaseHelper.getResultListByHql(sql1);
        for (int i = 1; i < lst.size(); i++) {
        	//后一个称号
        	Designation de= (Designation) lst.get(i);
        	if(number<de.getExperience()){
        		//前一个称号
        		Designation d= (Designation) lst.get(i-1);
        		ui.setDesignation(d.getDesignation());
        		ui.setLevel(d.getLevel());
        		break;
        	}
        	
		}
        //最后一个
        Designation des= (Designation) lst.get(lst.size()-1);
        if(number>=des.getExperience()){
        	ui.setDesignation(des.getDesignation());
        	ui.setLevel(des.getLevel());
        }
		databaseHelper.updateObject(ui);
		
		
		
		//TODO  通知机器投币成功，并且让机器开始倒计时
		
		//已废弃
		/*//第一位  1 预留
		//第二位  0投币  1移动  2停止  3抓取
		//第三位  1 向前  2向后 3向左  4向右  0无
		 */		
		
		//新的意义
		//P--投币  F--前移  B--后移  L--左移  R--右移  S--停  D--抓
		String info = "{100}";
		info = "PP";
		sBuffer.clear();  
        sBuffer.put(info.getBytes());  
        sBuffer.flip(); 
        Sys.out("NIOSServer.clientsLMap.size..."+NIOSServer.clientsLMap.size());
       
    	Sys.out("send to machine..machineno:"+mi.getMachineno()+"..info..."+info);
    	try {
			NIOSServer.clientsLMap.get(Long.parseLong(mi.getMachineno())).write(sBuffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			handlePipe(e, mi);
		}
    	sBuffer.clear();  	
    	//takeLock.unlock();
    	
    	
        	
	}

	@Override
	public synchronized String finish(String userid, String machinesid) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		if(machinesid==null||machinesid.length()==0)throw new Exception("machinesid错误");
		
		MachineInfo mi = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(machinesid));
		
		
		 StringBuffer querymused = new StringBuffer("select mu from MachineUsed mu where mu.fuserinfoid = ")
					.append(Long.parseLong(userid)).append(" and mu.fmachineid = ").append(Long.parseLong(machinesid));
		List<Object> muList = databaseHelper.getResultListByHql(querymused.toString());
		
		
		//将用户在此机器中的状态置为观看中
		
		String  sql="update userinfo set isplay =0 where id= "+userid+"";
		databaseHelper.executeNativeSql(sql);
		
		if(muList.size()>0){
			MachineUsed machineUsed = (MachineUsed) muList.get(0);
			
			machineUsed.setState(0);
			machineUsed.setCreatetime(new Date());
			databaseHelper.updateObject(machineUsed);
			
		}else{
			MachineUsed mUsed = new MachineUsed();
			mUsed.setCreatetime(new Date());
			mUsed.setFmachineid(Long.parseLong(machinesid));
			mUsed.setFuserinfoid(Long.parseLong(userid));
			mUsed.setIndexs(0);
			mUsed.setState(0);
			
			databaseHelper.persistObject(mUsed);
		}
		

		//找出所有预约排在此用户之后的用户
		StringBuffer find =  new StringBuffer("select mu from MachineUsed mu where mu.state = 1 ")
				.append(" and mu.fmachineid = ").append(Long.parseLong(machinesid)).append(" and mu.indexs > 0");
		List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		for (Object object : lst) {
			MachineUsed machineused = (MachineUsed)object;
			
			//将这些用户的排号前移
			machineused.setIndexs((machineused.getIndexs()-1)<0?0:(machineused.getIndexs()-1));
			machineused.setCreatetime(new Date());
			databaseHelper.updateObject(machineused);
			
			//通知到号的用户
			if(machineused.getIndexs()<=0){
				
				String info = "{\"type\":\"1\",\"info\":\"轮到你开始游戏了\",\"countdown\":\"15\"}";
				
				sBuffer.clear();  
		        sBuffer.put(info.getBytes());  
		        sBuffer.flip(); 
		        Sys.out("NIOSServer.clientsLMapOfApp.size..."+NIOSServer.clientsLMapOfApp.size());
		        try {
					if(NIOSServer.clientsLMapOfApp.get(machineused.getFuserinfoid())!=null){
						Sys.out("send to user..userid:"+machineused.getFuserinfoid()+"...info..."+info);
						NIOSServer.clientsLMapOfApp.get(machineused.getFuserinfoid()).write(sBuffer);
						sBuffer.clear();  
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					handlePipeofApp(e, machineused);
					sBuffer.clear();  
				}
		        
			}
		}
		
		
		
		if(lst.size()==0){
			//检查机器有没有用户正在使用
			querymused = new StringBuffer("select mu from MachineUsed mu where mu.fmachineid = ").append(Long.parseLong(machinesid))
					.append(" and mu.state > 1 ");
			List ul = databaseHelper.getResultListByHql(querymused.toString());
			//如果没有，则将机器状态置为空闲
			if(ul.size()==0)mi.setState(0);
		}
		
		databaseHelper.updateObject(mi);
		
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("msg", "success");
		map.put("info", "操作成功");
		map.put("userstate", 0);
		
		String json = JsonUtil.getGson().toJson(map);
		
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
	
	private void handlePipeofApp(Exception e,MachineUsed machineused){
		String mess = e.getMessage();
		if(mess.indexOf("Broken pipe")!=-1){
			Iterator<Map.Entry<Long, SocketChannel>> it = NIOSServer.clientsLMapOfApp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Long, SocketChannel> entry = it.next();
				if (entry.getKey().equals(machineused.getFuserinfoid())) {
					Sys.out("delete app this key: " + machineused.getFuserinfoid() + " = " + entry.getValue());
					it.remove(); // OK
				}
			}
		}
	}

	@Override
	public String collection(String userid, String machinesid) throws Exception {
		
		String sql="select count(id) from usercollection where fuserid ="+userid+" and fmachineid ="+machinesid+" and state =0  ";
		int count =databaseHelper.getSqlCount(sql);
		if(count>0)throw new Exception("您已收藏该商品，请不要重复收藏");
		UserCollection uc= new UserCollection();
		uc.setFuserid(Long.parseLong(userid));
		uc.setFmachineid(Long.parseLong(machinesid));
		uc.setCreatetime(new Date());
		uc.setState(0);
		databaseHelper.persistObject(uc);
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("msg", "success");
		map.put("info", "操作成功");
		map.put("userstate", 0);
		
		String json = JsonUtil.getGson().toJson(map);
		
		return json;
	}

	@Override
	public String removecollection(String userid, String machinesid) throws Exception {
		String sql="select count(id) from usercollection where fuserid ="+userid+" and fmachineid ="+machinesid+" and state =0  ";
		int count =databaseHelper.getSqlCount(sql);
		if(count==0)throw new Exception("您还未收藏该商品，请先收藏");
		String sql1="update usercollection set state=-1 where fuserid ="+userid+" and fmachineid ="+machinesid+" and state =0 ";
		databaseHelper.executeNativeSql(sql1);
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("msg", "success");
		map.put("info", "操作成功");
		map.put("userstate", 0);
		
		String json = JsonUtil.getGson().toJson(map);
		
		return json;
	}
}
