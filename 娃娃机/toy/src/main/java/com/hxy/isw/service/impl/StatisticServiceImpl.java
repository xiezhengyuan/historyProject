package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.service.StatisticService;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;

@Repository
public class StatisticServiceImpl implements StatisticService {
    
	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public String queryuserstatistic(String starttime, String endtime) throws Exception {
		// TODO Auto-generated method stub
		int year = Integer.parseInt(starttime.split("-")[0]);
		int month = Integer.parseInt(starttime.split("-")[1]);
		int start_day = Integer.parseInt(starttime.split("-")[2]);
		int end_day = Integer.parseInt(endtime.split("-")[2]);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		
		for (int i = start_day; i <= end_day; i++) {
			StringBuffer sql = new StringBuffer("");
			Map<String,Object> map = new HashMap<String,Object>();
			sql.append("select count(ui.id) 'a' from userinfo ui where  ui.state = 0  and  year(ui.createtime) =")
			.append(year).append(" and month(ui.createtime)=")
			.append(month).append(" and day(ui.createtime) =").append(i);
			
			List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
			map.put("nums", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
			map.put("time", i+"日");
			lstMap.add(map);
		}
		
		/*//今日活跃用户
		StringBuffer activeuser = new StringBuffer("select distinct ual.fuserid from useractivelog ual where date(ual.lasttime) = curdate() ");
		
		List lst = databaseHelper.getResultListBySql(activeuser.toString());
		int actives = lst.size();*/
		
		//总用户数
		StringBuffer user = new StringBuffer("select count(ui.id) from UserInfo ui ");
		
		List lst = databaseHelper.getResultListByHql(user.toString());
		int totaluser = lst==null?0:lst.size()==0?0:lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		
		JsonObject obj = new JsonObject();
		obj.add("rows", jarr);
	/*	obj.addProperty("actives", actives);*/
		obj.addProperty("totaluser", totaluser);
		return obj.toString();
	}

	@Override
	public String querytoystatistic(String starttime, String endtime) throws Exception {
		// TODO Auto-generated method stub
		int year = Integer.parseInt(starttime.split("-")[0]);
		int month = Integer.parseInt(starttime.split("-")[1]);
		int start_day = Integer.parseInt(starttime.split("-")[2]);
		int end_day = Integer.parseInt(endtime.split("-")[2]);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		
		for (int i = start_day; i <= end_day; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			StringBuffer sql = new StringBuffer("");
			sql.append("select count(mu.id) 'a' from machineusermark mu where mu.type = 2 and year(mu.createtime) =")
			.append(year).append(" and month(mu.createtime)=")
			.append(month).append(" and day(mu.createtime) =").append(i);
			
			Sys.out(sql.toString());
			List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
			Integer totalplay = lst==null?0:lst.size()==0?0:lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
			map.put("totalplay", totalplay);
			
			sql = new StringBuffer("");
			sql.append("select count(gb.id) 'a' from giftbox gb where year(gb.createtime) =")
			.append(year).append(" and month(gb.createtime)=")
			.append(month).append(" and day(gb.createtime) =").append(i);
			
			/*Sys.out(sql.toString());*/
			lst = databaseHelper.getResultListBySql(sql.toString());
			Integer totalsuccess = lst==null?0:lst.size()==0?0:lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
			map.put("totalsuccess", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
			
			/*Double playrate = (double) (totalsuccess/totalplay);*/
			
			map.put("time", i+"日");
			lstMap.add(map);
		}
		
		//总用户数
		/*StringBuffer user = new StringBuffer("select sum(ui.sesame) from UserInfo ui ");
		
		List lst = databaseHelper.getResultListByHql(user.toString());
		Double totalsesame = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());*/
		StringBuffer sql1 = new StringBuffer("");
		sql1.append("select count(mu.id) 'a' from machineusermark mu where mu.type = 2 and year(mu.createtime) =")
		.append(year).append(" and month(mu.createtime)=").append(month);
		List<Object> lst1 = databaseHelper.getResultListBySql(sql1.toString());
		Integer totalplay = lst1==null?0:lst1.size()==0?0:lst1.get(0)==null?0:Integer.parseInt(lst1.get(0).toString());
		
		StringBuffer sql2 = new StringBuffer("");
		sql2.append("select count(gb.id) 'a' from giftbox gb where year(gb.createtime) =")
		.append(year).append(" and month(gb.createtime)=").append(month);
		List<Object> lst2 = databaseHelper.getResultListBySql(sql2.toString());
		Integer totalsuccess = lst2==null?0:lst2.size()==0?0:lst2.get(0)==null?0:Integer.parseInt(lst2.get(0).toString());
		Double playrate = 0.0;
		if(totalplay!=0){
			 playrate = (double) (totalsuccess/totalplay);
		}else{
			 playrate = 0.0;
		}
		
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		
		JsonObject obj = new JsonObject();
		obj.add("rows", jarr);
		obj.addProperty("playrate", playrate);
		return obj.toString();
	}

	@Override
	public String querypointstatistic(String starttime, String endtime) throws Exception {
		// TODO Auto-generated method stub
		int year = Integer.parseInt(starttime.split("-")[0]);
		int month = Integer.parseInt(starttime.split("-")[1]);
		int start_day = Integer.parseInt(starttime.split("-")[2]);
		int end_day = Integer.parseInt(endtime.split("-")[2]);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		
		for (int i = start_day; i <= end_day; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			StringBuffer sql = new StringBuffer("");
			sql.append("select sum(pl.amount) 'a' from paymentslog pl where pl.type = 2 and pl.state = 1 and year(pl.createtime) =")
			.append(year).append(" and month(pl.createtime)=")
			.append(month).append(" and day(pl.createtime) =").append(i);
			
		/*	Sys.out(sql.toString());*/
			List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
			map.put("harvest", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
			
			sql = new StringBuffer("");
			sql.append("select sum(pl.amount) 'a' from paymentslog pl where pl.type = 2 and pl.state = -1 and year(pl.createtime) =")
			.append(year).append(" and month(pl.createtime)=")
			.append(month).append(" and day(pl.createtime) =").append(i);
			
			/*Sys.out(sql.toString());*/
			lst = databaseHelper.getResultListBySql(sql.toString());
			map.put("consume", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
			
			map.put("time", i+"日");
			lstMap.add(map);
		}
		
		//总用户数
		StringBuffer user = new StringBuffer("select sum(ui.points) from UserInfo ui ");
		
		List lst = databaseHelper.getResultListByHql(user.toString());
		Double totalpoints = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		
		JsonObject obj = new JsonObject();
		obj.add("rows", jarr);
		obj.addProperty("totalpoints", totalpoints);
		return obj.toString();
	}

	@Override
	public String queryfruitstatistic(String starttime, String endtime) throws Exception {
		// TODO Auto-generated method stub
		int year = Integer.parseInt(starttime.split("-")[0]);
		int month = Integer.parseInt(starttime.split("-")[1]);
		int start_day = Integer.parseInt(starttime.split("-")[2]);
		int end_day = Integer.parseInt(endtime.split("-")[2]);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		
		for (int i = start_day; i <= end_day; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			StringBuffer sql = new StringBuffer("");
			sql.append("select sum(i.income) 'a' from income i where i.state = 0 and year(i.createtime) =")
			.append(year).append(" and month(i.createtime)=")
			.append(month).append(" and day(i.createtime) =").append(i);
			
		/*	Sys.out(sql.toString());*/
			List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
			map.put("income", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
			
			map.put("time", i+"日");
			lstMap.add(map);
		}
		
		//总果实数
		StringBuffer user = new StringBuffer("select sum(i.income) from Income i ");
		
		List lst = databaseHelper.getResultListByHql(user.toString());
		Double totalincome = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		
		JsonObject obj = new JsonObject();
		obj.add("rows", jarr);
		obj.addProperty("totalincome", totalincome);
		return obj.toString();
	}
	
	
	
}
