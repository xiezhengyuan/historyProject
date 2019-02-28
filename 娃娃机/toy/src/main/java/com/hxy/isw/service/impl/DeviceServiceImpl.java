package com.hxy.isw.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.DeliveryApply;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.Expressage;
import com.hxy.isw.entity.ExpressageInfo;
import com.hxy.isw.entity.FileInfo;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.NotifyInfo;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.ToysPhotos;
import com.hxy.isw.entity.ToysType;
import com.hxy.isw.service.DeviceService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.KdniaoTrackQueryAPI;
import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;

@Repository
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	DatabaseHelper databaseHelper;
	@Override
	public Map<String, Object> findMachineList(Employee em, String machineno, String ftoysid, Integer start,
			Integer limit)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM machineinfo mi WHERE 1=1 and mi.empid=").append(em.getId());
		StringBuffer hql = new StringBuffer("SELECT mi FROM MachineInfo mi WHERE 1=1 and mi.empid=").append(em.getId());

		if (!StringUtils.isEmpty(machineno)) {
			sql.append(" AND mi.machineno LIKE '%").append(machineno).append("%'");
			hql.append(" AND mi.machineno LIKE '%").append(machineno).append("%'");
		}
		if (!StringUtils.isEmpty(ftoysid) && !ftoysid.equals("0")) {
			sql.append(" AND mi.ftoysid=").append(ftoysid);
			hql.append(" AND mi.ftoysid=").append(ftoysid);
		}

		Integer count = databaseHelper.getSqlCount(sql.toString());
		int pages = ConstantUtil.pages(count, limit);
		resultMap.put("pages", pages);

		// StringBuffer hql = new StringBuffer("SELECT mi,ti FROM MachineInfo
		// mi,ToysInfo ti WHERE mi.ftoysid=ti.id");

		List<Object> resultList = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		for (Object objects : resultList) {
			MachineInfo mi = (MachineInfo) objects;
			// ToysInfo ti = (ToysInfo) objects[1];
			StringBuffer buffer=new StringBuffer("select IFNULL(SUM(c.balance),0) from consumption c where c.paystate=1 and c.fmachineid=")
					.append(mi.getId());
			System.out.println(buffer);
			int balance=Integer.parseInt(databaseHelper.getResultListBySql(buffer.toString()).get(0).toString());
			Map<String, Object> map = new HashMap<String, Object>();
			if (mi.getFtoysid() != null) {
				ToysInfo toysInfo = (ToysInfo) databaseHelper.getObjectById(ToysInfo.class, mi.getFtoysid());
				map.put("toysname", toysInfo.getName());
			} else {
				map.put("toysname", "");
			}
			map.put("id", mi.getId());
			map.put("machineno", mi.getMachineno());// 设备编号
			map.put("ftoysid", mi.getFtoysid());// 玩具ID
			map.put("price", mi.getPrice());// 玩具价格（没有绑定则为0）
			map.put("popularity", mi.getPopularity());// 人气（没有绑定则为0）
			map.put("createtime", mi.getCreatetime().toString());
			map.put("state", mi.getState());// -1失效 0空闲 1正在使用
			map.put("isnew", mi.getIsnew());// 是否上新 0否 1是
			map.put("facevideo", mi.getFacevideo());// 正面直播流地址
			map.put("sidevideo", mi.getSidevideo());// 侧面直播流地址
			map.put("views", mi.getViews());// 观看人数
			map.put("subscribe", mi.getSubscribe());// 预约人数
			map.put("stock", mi.getStock());// 玩具数量（没有绑定则为0）
			map.put("balance", balance);
			// map.put("toysname", ti.getName());
			list.add(map);
		}
		resultMap.put("list", list);
		return resultMap;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void bindToys(Employee em, MachineInfo machineInfo) throws Exception {
		ToysInfo toysInfo = (ToysInfo) databaseHelper.getObjectById(ToysInfo.class, machineInfo.getFtoysid());
		MachineInfo mi = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, machineInfo.getId());
		mi.setEmpid(em.getId());
		
		mi.setFtoysid(toysInfo.getId());
		mi.setPrice(toysInfo.getPrice());
		mi.setIsnew(machineInfo.getIsnew());
		mi.setStock(machineInfo.getStock());
		mi.setFacevideo(machineInfo.getFacevideo());
		mi.setSidevideo(machineInfo.getSidevideo());
		mi.setState(machineInfo.getState());
		databaseHelper.updateObject(mi);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addmachine(Employee em,String addmachineid) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM machineinfo mi WHERE mi.machineno='")
				.append(addmachineid + "'");
		Integer count = databaseHelper.getSqlCount(sql.toString());
		if (count > 0) {
			throw new Exception("该编号已存在");
		}
		MachineInfo machineInfo=new MachineInfo();
		machineInfo.setState(0);
		machineInfo.setMachineno(addmachineid);
		machineInfo.setCreatetime(new Date());
		machineInfo.setEmpid(em.getId());
		databaseHelper.persistObject(machineInfo);
	}
	
	@Override
	public Map<String, Object> findAlltoys(Employee em)throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("SELECT ti FROM ToysInfo ti WHERE ti.state=0 and ti.empid=").append(em.getId());
		System.out.println(hql);
		List resultList = databaseHelper.getResultListByHql(hql.toString());

		for (Object object : resultList) {
			ToysInfo toysInfo = (ToysInfo) object;
			list.add(JsonUtil.obj2map(toysInfo));
		}
		map.put("rows", list);
		return map;
	}

	@Override
	public Map<String, Object> findAlltoysType(Employee em)throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("SELECT tt FROM ToysType tt WHERE tt.state=0");
		List<Object> resultList = databaseHelper.getResultListByHql(hql.toString());
		for (Object object : resultList) {
			ToysType tt = (ToysType) object;
			list.add(JsonUtil.obj2map(tt));
		}
		map.put("rows", list);
		map.put("op", "success");
		map.put("msg", "");
		return map;
	}
	
	@Override
	public Map<String, Object> toysInfoList(Employee em, int start, int limit, String name, String ftoystypeid)throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM toysinfo ti WHERE ti.state=0 and ti.empid=").append(em.getId());
		StringBuffer hql = new StringBuffer(
				"SELECT ti,tt FROM ToysInfo ti,ToysType tt WHERE ti.state=0 AND ti.ftoystypeid=tt.id and ti.empid=").append(em.getId());
		if (!StringUtils.isEmpty(name)) {
			sql.append(" AND ti.name LIKE '%").append(name).append("%'");
			hql.append(" AND ti.name LIKE '%").append(name).append("%'");
		}
		if (!StringUtils.isEmpty(ftoystypeid) && !ftoystypeid.equals("0")) {
			sql.append(" AND ti.ftoystypeid=").append(ftoystypeid);
			hql.append(" AND ti.ftoystypeid=").append(ftoystypeid);
		}

		Integer count = databaseHelper.getSqlCount(sql.toString());
		Integer pages = ConstantUtil.pages(count, limit);
		resultMap.put("pages", pages);

		List<Object[]> resultList = databaseHelper.getResultListByHql(hql.append(" ORDER BY ti.createtime DESC").toString(), start, limit);
		for (Object[] object : resultList) {
			Map<String, Object> map = new HashMap<String, Object>();
			ToysInfo toysInfo = (ToysInfo) object[0];
			ToysType toysType = (ToysType) object[1];
			map = JsonUtil.obj2map(toysInfo);
			map.put("toystype", toysType.getName());
			list.add(map);
		}
		resultMap.put("rows", list);
		return resultMap;
	}
	
	
	
	@Override
	public Map<String, Object> toysTypeList(Employee em, int start, int limit, String name) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();	

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM toystype tt WHERE tt.state=0 ");
		Integer count = databaseHelper.getSqlCount(sql.toString());
		int pages = ConstantUtil.pages(count, limit);
		resultMap.put("pages", pages);

		StringBuffer hql = new StringBuffer("SELECT tt FROM ToysType tt WHERE tt.state=0");

		List resultList = databaseHelper.getResultListByHql(StringUtils.isEmpty(name) ? hql.toString()
				: hql.append("AND tt.name LIKE '%").append(name).append("%'").toString(), start, limit);
		for (Object object : resultList) {
			ToysType tt = (ToysType) object;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", tt.getId());
			map.put("name", tt.getName());
			map.put("createtime", tt.getCreatetime());
			map.put("state", tt.getState());
			list.add(map);
		}
		resultMap.put("rows", list);
		return resultMap;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addToys(ToysInfo toysInfo, String imgarr,Employee em) {

		if (toysInfo.getId() == null) {
			// 添加玩具表
			toysInfo.setEmpid(em.getId());
			databaseHelper.persistObject(toysInfo);

			JsonArray arr = (JsonArray) JsonUtil.getJsonParser().parse(imgarr);
			// 添加玩具图片表
			for (JsonElement jsonElement : arr) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				Long id = jsonObject.get("id").getAsLong();
				String url = jsonObject.get("url").getAsString();
				String name = jsonObject.get("name").getAsString();

				ToysPhotos toysPhotos = new ToysPhotos();
				toysPhotos.setFtoysid(toysInfo.getId());
				toysPhotos.setFfileinfoid(id);
				toysPhotos.setPhotourl(url);
				toysPhotos.setState(0);
				toysPhotos.setCreatetime(new Date());
				databaseHelper.persistObject(toysPhotos);

				// 修改图片表引用状态
				FileInfo fileInfo = (FileInfo) databaseHelper.getObjectById(FileInfo.class, id);
				fileInfo.setState(1);
				databaseHelper.updateObject(fileInfo);

			}

		} else {
			// 修改
			toysInfo.setEmpid(em.getId());
			databaseHelper.updateObject(toysInfo);
			
			JsonArray arr = (JsonArray) JsonUtil.getJsonParser().parse(imgarr);

			StringBuffer hql = new StringBuffer("SELECT tp FROM ToysPhotos tp WHERE tp.state=0 AND tp.ftoysid=")
					.append(toysInfo.getId());
			// 失效
			StringBuffer sqlfileinfo1 = new StringBuffer("UPDATE fileinfo fi SET fi.state=-1 WHERE 1=1");
			StringBuffer sqltoysphotos1 = new StringBuffer("UPDATE toysphotos tp SET tp.state=-1 WHERE 1=1");
			
			//所有玩具图片集合
			List<Object> resultList = databaseHelper.getResultListByHql(hql.toString());
			for (Object object : resultList) {
				ToysPhotos photos = (ToysPhotos) object;
				/* 修改fileinfo表图片状态 为失效 */
				databaseHelper.executeNativeSql(
						sqlfileinfo1.append(" AND fi.id=").append(photos.getFfileinfoid()).toString());
			}
			/* 修改toysphotos表图片状态 为失效 */
			databaseHelper
					.executeNativeSql(sqltoysphotos1.append(" AND tp.ftoysid=").append(toysInfo.getId()).toString());

			// 生效
			StringBuffer sqlfileinfo2 = new StringBuffer("UPDATE fileinfo fi SET fi.state=1 WHERE 1=1");
			StringBuffer sqltoysphotos2 = new StringBuffer("UPDATE toysphotos tp SET tp.state=0 WHERE 1=1");
			// 是否存在
			StringBuffer sqlexist = new StringBuffer("SELECT COUNT(1) FROM toysphotos tp WHERE 1=1");

			for (JsonElement jsonElement : arr) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				Long id = jsonObject.get("id").getAsLong();
				String url = jsonObject.get("url").getAsString();
				String name = jsonObject.get("name").getAsString();

				int count = databaseHelper.getSqlCount(
						sqlexist.append(" AND tp.ftoysid=").append(toysInfo.getId()).append(" AND tp.ffileinfoid=")
								.append(id).append(" AND tp.photourl='").append(url).append("'").toString());
				if(count>0){
					//如果之前已经存在过  修改状态
					databaseHelper.executeNativeSql(sqlfileinfo2.append(" AND fi.id=").append(id).toString());
					databaseHelper.executeNativeSql(sqltoysphotos2.append(" AND tp.ffileinfoid=").append(id).toString());
				}else{
					//否则新增
					ToysPhotos toysPhotos = new ToysPhotos();
					toysPhotos.setFtoysid(toysInfo.getId());
					toysPhotos.setFfileinfoid(id);
					toysPhotos.setPhotourl(url);
					toysPhotos.setState(0);
					toysPhotos.setCreatetime(new Date());
					databaseHelper.persistObject(toysPhotos);

					// 修改图片表引用状态
					FileInfo fileInfo = (FileInfo) databaseHelper.getObjectById(FileInfo.class, id);
					fileInfo.setState(1);
					databaseHelper.updateObject(fileInfo);
				}
			}
		}

	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addToysType(Employee em, ToysType tt) throws Exception {
		StringBuffer sql;
		if (tt.getId() == null) {
			sql = new StringBuffer("SELECT COUNT(1) FROM toystype tt WHERE tt.name='").append(tt.getName())
					.append("' AND tt.state=0");

			Integer sqlCount = databaseHelper.getSqlCount(sql.toString());
			if (sqlCount > 0) {
				throw new NullPointerException("该分类已存在");
			}

			ToysType toysType = new ToysType();
			
			toysType.setName(tt.getName());
			toysType.setCreatetime(new Date());
			toysType.setState(0);
			databaseHelper.persistObject(toysType);
		} else {
			sql = new StringBuffer("SELECT COUNT(1) FROM toystype tt WHERE tt.name='").append(tt.getName())
					.append("' AND tt.state=0 AND tt.id!= ").append(tt.getId());
			Integer sqlCount = databaseHelper.getSqlCount(sql.toString());
			if (sqlCount > 0) {
				throw new NullPointerException("该分类已存在");
			}

			ToysType toysType = (ToysType) databaseHelper.getObjectById(ToysType.class, tt.getId());
			toysType.setName(tt.getName());
			databaseHelper.updateObject(toysType);
		}

	}
	
	
	@Override
	public Map<String, Object> searchDeliveryapply(Integer start, String state,Integer limit,Employee em) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		
		/*String countsql= " select count(d1.createtime) from (select count(createtime) createtime  from deliveryapply where state=0 and consigneename like '%"+keyword+"%' group by createtime)d1 ";*/
		StringBuffer countsql;
		
		if(Long.parseLong(state)==1){
			countsql =new StringBuffer("select count(d.id) from deliveryapply d,giftbox g,machineinfo m,expressageinfo e WHERE d.fgiftboxid=g.id and g.machineno=m.machineno and e.fdeliveryapplyid=d.id and m.empid=")
					.append(em.getId()).append(" and d.state=").append(state);
		}else {
			countsql =new StringBuffer("select count(d.id) from deliveryapply d,giftbox g,machineinfo m WHERE d.fgiftboxid=g.id and g.machineno=m.machineno and m.empid=")
					.append(em.getId()).append(" and d.state=").append(state);
		}	
		int records = databaseHelper.getSqlCount(countsql.toString());
		//System.out.println(count);
		int total = ConstantUtil.pages(records, limit);
		if(records>0){
			/*StringBuffer sql = new StringBuffer("select id ,consigneename,consigneemobile,CONCAT(province,city,area,address)useraddress,createtime, count(createtime) toynum  ")
					.append(" from deliveryapply where state=0 and consigneename like '%"+keyword+"%' group by createtime order by createtime desc ");
			*/
			StringBuffer sql;
			
			if(Long.parseLong(state)==1){
				sql=new StringBuffer("select d.id,d.consigneename,d.consigneemobile,d.createtime,g.toysname,CONCAT(province,city,area,address) useraddress,e.expressageno,e.id werst,e.expressagecompany from deliveryapply d,giftbox g,machineinfo m,expressageinfo e WHERE d.fgiftboxid=g.id and g.machineno=m.machineno and e.fdeliveryapplyid=d.id and m.empid=")
						.append(em.getId()).append(" and d.state=").append(state);
				
			}else{
				sql=new StringBuffer("select d.id,d.consigneename,d.consigneemobile,d.createtime,g.toysname,CONCAT(province,city,area,address) useraddress from deliveryapply d,giftbox g,machineinfo m WHERE d.fgiftboxid=g.id and g.machineno=m.machineno and m.empid=")
						.append(em.getId()).append(" and d.state=").append(state);
				
			}
			List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString(),start,limit);
			
			for (Object[] obj: lst){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj[0]);
				map.put("consigneename", obj[1]);
				map.put("consigneemobile", obj[2]);
				map.put("createtime", obj[3]);
				map.put("address", obj[5]);
				map.put("toys", obj[4]);
				if(Long.parseLong(state)==1){
					map.put("did", obj[6]);
					map.put("eid",obj[7]);
					map.put("expressagecompany", obj[8]);
				}
			/*	StringBuffer sql1=new StringBuffer("select toysname,count(ftoysid)number  from giftbox where id in ");
				sql1.append(" (select fgiftboxid from deliveryapply where createtime ='"+obj[4]+"') group by ftoysid  ");
				List<Object[]> lst1=databaseHelper.getResultListBySql(sql1.toString());
				String  toys="";
				for (Object[] o: lst1){
					toys+=o[0].toString()+"x"+o[1].toString()+"，";
				}*/
				
				rowList.add(map);
			}
		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	
	
	@Override
	public void sendtouser(String id, String content) throws Exception {
		
		DeliveryApply d=(DeliveryApply) databaseHelper.getObjectById(DeliveryApply.class, Long.parseLong(id));
		long userid= d.getFuserinfoid();
		NotifyInfo ni=new NotifyInfo();
		ni.setType(0);
		ni.setFuserinfoid(userid);
		ni.setTitle("关于娃娃发货的反馈");
		ni.setContent(content);
		ni.setCreatetime(new Date());
		ni.setState(0);
		databaseHelper.persistObject(ni);
		
	}
	
	@Override
	public void deleteapp(String id) throws Exception {

       StringBuffer sql= new StringBuffer("  update deliveryapply set state =-1 where createtime  =  ");
       sql.append(" (select a1.createtime  from (select createtime from deliveryapply where id= "+id+" )a1 ) ");
      int count = databaseHelper.executeNativeSql(sql.toString());
       if(count==0)throw new Exception("参数错误");
		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addexpressageinfo(String id, String expressageno, String expressageid) throws Exception {
		Expressage e =(Expressage)databaseHelper.getObjectById(Expressage.class, Long.parseLong(expressageid));
		
		//发货申请表
		StringBuffer updsql1=new StringBuffer(" update deliveryapply set state =1 where createtime  =  ");
		updsql1.append(" (select a1.createtime  from (select createtime from deliveryapply where id= "+id+" )a1 ) ");
		databaseHelper.executeNativeSql(updsql1.toString());
		
		//礼物盒
		/*StringBuffer updsql2=new StringBuffer(" update giftbox set state=2 where id in  ");
		updsql2.append(" (select fgiftboxid from deliveryapply  where  createtime  = (select createtime from deliveryapply where id= "+id+" )) ");
		*/
		DeliveryApply d =(DeliveryApply)databaseHelper.getObjectById(DeliveryApply.class, Long.parseLong(id));
		StringBuffer updsql2=new StringBuffer(" update giftbox set state=2 where id=").append(d.getFgiftboxid());
		databaseHelper.executeNativeSql(updsql2.toString());
		
		//物流表
		/*DeliveryApply d=(DeliveryApply) databaseHelper.getObjectById(DeliveryApply.class, Long.parseLong(id));
		*/
		ExpressageInfo ex=new ExpressageInfo();
		ex.setFdeliveryapplyid(d.getId());
		ex.setFgiftboxid(d.getFgiftboxid());
		ex.setExpressageno(expressageno);
		ex.setExpressagecompany(e.getExpressagecompany());
		ex.setExpressagecode(e.getExpressagecode());
		ex.setState(0);
		ex.setCreatetime(new Date());
		databaseHelper.persistObject(ex);
		
	}
	
	@Override
	public String queryexpressagedetail(String id) throws Exception {
		// TODO Auto-generated method stub
				//查看订单是否已发货
		
		ExpressageInfo oi = (ExpressageInfo)databaseHelper.getObjectById(ExpressageInfo.class, Long.parseLong(id));
				if(oi==null)throw new Exception("订单信息错误");
				
				
				//查看订单物流
				String hql = "select expressageinfo from  ExpressageInfo expressageinfo where expressageinfo.state =0 and expressageinfo.id="+Long.parseLong(id);
				List<Object> lst = databaseHelper.getResultListByHql(hql);
				if(lst.size()==0)throw new Exception("发货人未填写物流信息");
				
				ExpressageInfo expressageinfo = (ExpressageInfo)lst.get(0);
				String result = "";
				
				KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
				result = api.getOrderTracesByJson(expressageinfo.getExpressagecode(), expressageinfo.getExpressageno());
				return result;
	}
	
	@Override
	public JsonArray queryexpressage() throws Exception {
		StringBuffer queryexpressage = new StringBuffer("select expr from Expressage expr where expr.state = 0");
		List<Object>eList = databaseHelper.getResultListByHql(queryexpressage.toString());
		return JsonUtil.castLst2Arr4SingleTime(eList);
		
	}

	@Override
	public String outportinmoneylog(Employee em, String starttime, String endtime) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer("select m.machineno,SUM(c.golds) from consumption c,machineinfo m where c.fmachineid=m.id and m.state!=-1 and c.paystate=1 and m.empid=")
				.append(em.getId());
		buffer.append(addtime(starttime, endtime));
		List<Object[]> lst = databaseHelper.getResultListBySql(buffer.toString());
		String filename = gen_excelinmoney(lst);

		return filename;
	}
	
	
	private StringBuffer addtime(String starttime, String endtime) {
		StringBuffer timesql = new StringBuffer("");
		if (starttime == "" && endtime == "") {
			timesql.append(" GROUP BY m.machineno");
		} else if (starttime == "" && endtime != "") {
			endtime = endtime + " 23:59:59";
			timesql.append(" and c.createtime<='" + endtime + "' ")
			.append("GROUP BY m.machineno");

		} else if (starttime != "" && endtime == "") {
			starttime = starttime + " 00:00:00";
			timesql.append(" and c.createtime>='" + starttime + "' ").append("GROUP BY m.machineno");
		} else {
			starttime = starttime + " 00:00:00";
			endtime = endtime + " 23:59:59";
			timesql.append(" and c.createtime>='" + starttime + "' and c.createtime<='" + endtime + "' ").append("GROUP BY m.machineno");
		}
		return timesql;
	}
	
private String gen_excelinmoney(List<Object[]> lst) {
		
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet sheet = hssfworkbook.createSheet("new sheet");
		HSSFRow row = sheet.createRow(0);
		int count = 0;

		row.createCell(0).setCellValue("编号");
		/*row.createCell(2).setCellValue("消费时间");*/

		row.createCell(1).setCellValue("喵币");
		
		for (Object[] objects : lst) {
			HSSFRow _row = sheet.createRow(++count);
			_row.createCell(0).setCellValue(objects[0].toString());
			_row.createCell(1).setCellValue(objects[1].toString());
			/*_row.createCell(2).setCellValue(objects[2].toString());*/
		}

		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = _sdf.format(new Date());
		time = time.replace(" ", "").replace(":", "").replace("-", "");
		// System.out.println("count:"+count);
		/*
		 * String path = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/",
		 * ""); path = path + "html";
		 */
		// String path =
		// ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp/html/excel");
		String path = ConstantUtil.environment.equals("maven")
				? ConstantUtil.PROJECT_PATH.replace("target/classes/", "src/main/webapp/excel/")
				: ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", "excel/");// tomcat
		// path = ConstantUtil.PROJECT_PATH.replace("target/classes/",
		// "src/main/webapp/excel/");//maven
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		path += "excel_" + time + ".xls";

		try {
			FileOutputStream fileoutputstream = new FileOutputStream(path);
			hssfworkbook.write(fileoutputstream);
			fileoutputstream.flush();
			fileoutputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "excel_" + time + ".xls";
	}

@Override
public Map<String, Object> querymiaobi(Employee em, String starttime, String endtime,Integer start,Integer limit) {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	Map<String, Object> resultMap = new HashMap<String, Object>();	
	StringBuffer buffer1 = new StringBuffer("select count(m.id) from machineinfo m where m.state!=-1 and m.empid=")
			.append(em.getId());
	
	System.out.println(buffer1);
	Integer count = databaseHelper.getSqlCount(buffer1.toString());
	int pages = ConstantUtil.pages(count, limit);
	resultMap.put("pages", pages);
	if(count>0){
	StringBuffer buffer = new StringBuffer("select m.machineno,SUM(c.golds) from consumption c,machineinfo m where c.fmachineid=m.id and m.state!=-1 and c.type=-1 and m.empid=")
			.append(em.getId());
	
	buffer.append(addtime(starttime, endtime));
	List<Object[]> lst = databaseHelper.getResultListBySql(buffer.toString());
	for(Object[] obj:lst){
		String machineno =obj[0].toString();
		String golds=obj[1].toString();
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("machineno", machineno);
		map.put("golds", golds);
		
		list.add(map);
		
	}
	}
	resultMap.put("pages", pages);
	resultMap.put("rows", list);
	
	return resultMap;
}

@Override
public JsonArray queryStatistics(String date, Employee em) throws Exception {
	// TODO Auto-generated method stub
	int [] montharr= {31,28,31,30,31,30,31,31,30,31,30,31};
	System.out.println(date);
	int year;
	int month;
	if(date.length()==0){
		month = new Date().getMonth()+1;
		year= new Date().getYear()+1900;
	}else{
		String[] s=date.split("-");
		year=Integer.parseInt(s[0]);
		month=Integer.parseInt(s[1]);
	}
	
	
	List<Map<String,Object>> lstMap = new LinkedList<Map<String,Object>>();
	for (int i = 1; i <=montharr[month-1] ; i++) {
		StringBuffer buffer =new StringBuffer("select SUM(c.golds) from consumption c,machineinfo m where c.fmachineid=m.id and m.state!=-1 and c.type=-1 and m.empid=")
				.append(em.getId()).append(" and year(c.createtime) =").append(year)
				.append(" and month(c.createtime) =").append(month).append(" and day(c.createtime) =").append(i);;
				System.out.println(buffer);
				Map<String,Object> map = new HashMap<String,Object>();
				List<Object> lst = databaseHelper.getResultListBySql(buffer.toString());
				
				map.put("nums", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
				map.put("time", i+"日");
				map.put("month", month);
				lstMap.add(map);
	}
	String result = new Gson().toJson(lstMap);
	JsonArray jarr = (JsonArray) new JsonParser().parse(result);
	return jarr;
}
}
