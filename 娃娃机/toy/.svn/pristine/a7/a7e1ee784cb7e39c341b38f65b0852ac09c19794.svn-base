package com.hxy.isw.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.DeliveryApply;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.Expressage;
import com.hxy.isw.entity.ExpressageInfo;

import com.hxy.isw.entity.Giftbox;
import com.hxy.isw.entity.NotifyInfo;
import com.hxy.isw.service.DeliveryapplyService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.KdniaoTrackQueryAPI;

@Repository
public class DeliveryapplyServiceImpl implements DeliveryapplyService {

	@Autowired
	DatabaseHelper databaseHelper;
	@Override
	public Map<String, Object> searchDeliveryapply(String empid,String keyword,String toyname,int start,int limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		
		StringBuffer countsql= new StringBuffer("select count(de.id) from");
		
		int records = databaseHelper.getSqlCount(addsql(countsql, empid, keyword,toyname).toString());
		int total = ConstantUtil.pages(records, limit);
		
		if(records>0){
			StringBuffer sql = new StringBuffer("select de.id,de.consigneename,de.consigneemobile,CONCAT(de.province,de.city,de.area,address)useraddress,gi.toysname,gi.photo,em.`name`,de.createtime from  ");
				
			List<Object[]> lst = databaseHelper.getResultListBySql(addsql(sql, empid, keyword,toyname).toString(),start,limit);
			
			for (Object[] obj: lst){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj[0]);
				map.put("consigneename", obj[1]);
				map.put("consigneemobile", obj[2]);
				map.put("address", obj[3]);
				map.put("toysname", obj[4]);
				map.put("toyurl", obj[5]);
				map.put("empname", obj[6]);
				map.put("createtime", obj[7]);
				rowList.add(map);
		
			}
		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	
	public StringBuffer addsql(StringBuffer sql,String empid,String keyword,String toyname){
		sql.append(" deliveryapply de join giftbox gi on de.fgiftboxid=gi.id ");
		sql.append(" join machineinfo ma on gi.fmachineid=ma.id ");
		sql.append(" join employee em on ma.empid=em.id ");
		sql.append(" where de.state = 0 and  gi.state=1 and (de.consigneename like '%"+keyword+"%' or de.consigneemobile like '%"+keyword+"%') ");
		sql.append(" and gi.toysname like '%"+toyname+"%' and em.id ="+empid+" order by de.createtime  ");
		return sql;
	}
	
	
	@Override
	public JsonArray queryexpressage() throws Exception {
		StringBuffer queryexpressage = new StringBuffer("select expr from Expressage expr where expr.state = 0");
		List<Object>eList = databaseHelper.getResultListByHql(queryexpressage.toString());
		return JsonUtil.castLst2Arr4SingleTime(eList);
		
	}
	
		
	
	@Override
	public Map<String, Object> queryexpressageinfo(String empid,String keyword,String toyname,int start,int limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer(" select count(ex.id) ");
		
		int count = databaseHelper.getSqlCount(addbuffer(buffer, empid, keyword, toyname).toString());
		
		int pages = ConstantUtil.pages(count, limit);
		
		if(count>0){
			StringBuffer Sql = new StringBuffer(" select ex.id,ex.expressageno, gi.toysname,de.consigneename,de.consigneemobile,em.`name` ");
				
			List<Object[]> lst=databaseHelper.getResultListBySql(addbuffer(Sql, empid, keyword, toyname).toString(), start, limit);
			for (Object[] obj : lst){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj[0]);
				map.put("orderno", obj[1]);
				map.put("toysname",obj[2]);
				map.put("consigneename",obj[3]);
				map.put("consigneemobile",obj[4]);
				map.put("empname",obj[4]);
				rowList.add(map);
			}
		}
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	
	public StringBuffer addbuffer(StringBuffer sql,String empid,String keyword,String toyname){
		sql.append(" from expressageinfo ex  join deliveryapply de  on ex.fdeliveryapplyid = de.id ");
		sql.append(" join giftbox gi on ex.fgiftboxid =gi.id  ");
		sql.append(" join machineinfo ma on gi.fmachineid=ma.id  ");
		sql.append(" join employee em on ma.empid = em.id ");
		sql.append("  where (de.consigneename like '%"+keyword+"%' or de.consigneemobile like '%"+keyword+"%')   ");
		sql.append("  and gi.toysname like '%"+toyname+"%' and em.id ="+empid+" order by de.createtime  ");
		return sql;
	}
	@Override
	public String queryexpressagedetail(String id) throws Exception {
		// TODO Auto-generated method stub
				//查看订单是否已发货
		
		ExpressageInfo oi = (ExpressageInfo)databaseHelper.getObjectById(ExpressageInfo.class, Long.parseLong(id));
				if(oi==null)throw new Exception("订单信息错误");
				
				
				//查看订单物流
				String hql = "select expressageinfo from  ExpressageInfo expressageinfo where expressageinfo.state = 1 and expressageinfo.id="+Long.parseLong(id);
				List<Object> lst = databaseHelper.getResultListByHql(hql);
				if(lst.size()==0)throw new Exception("发货人未填写物流信息");
				
				ExpressageInfo expressageinfo = (ExpressageInfo)lst.get(0);
				String result = "";
				
				KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
				result = api.getOrderTracesByJson(expressageinfo.getExpressagecode(), expressageinfo.getExpressageno());
				return result;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addexpressageinfo(String id, String expressageno, String expressagecompany) throws Exception {
		
		
		//发货申请表
		StringBuffer updsql1=new StringBuffer(" update deliveryapply set state =1  where id= "+id+"");
		databaseHelper.executeNativeSql(updsql1.toString());
		
		//礼物盒
		StringBuffer updsql2=new StringBuffer(" update giftbox set state=2 where id =  ");
		updsql2.append(" (select fgiftboxid from deliveryapply  where id= "+id+") ");
		
		//物流表
		DeliveryApply d=(DeliveryApply) databaseHelper.getObjectById(DeliveryApply.class, Long.parseLong(id));
		
		ExpressageInfo ex=new ExpressageInfo();
		ex.setFdeliveryapplyid(d.getId());
		ex.setFgiftboxid(d.getFgiftboxid());
		ex.setExpressageno(expressageno);
		ex.setExpressagecompany(expressagecompany);
		ex.setExpressagecode(expressagecompany);
		ex.setState(0);
		ex.setCreatetime(new Date());
		databaseHelper.persistObject(ex);
		
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
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteapp(String id,String type) throws Exception {

      String sql="  update deliveryapply set state =-1 where id ="+id+"";
      int count = databaseHelper.executeNativeSql(sql);
      if(count==0)throw new Exception("参数错误");
      if(type.equals("1")){
    	  String sql1="update giftbox set state =-1 where id =( select fgiftboxid from deliveryapply where id =100000)";
    	  databaseHelper.executeNativeSql(sql1);
      }else{
    	  String sql1="update giftbox set state =0 where id =( select fgiftboxid from deliveryapply where id =100000)";
    	  databaseHelper.executeNativeSql(sql1);
      }
     	
	}
	

}
