package com.hxy.isw.service.impl;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.StatisticService;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;
import com.sun.accessibility.internal.resources.accessibility;

@Repository
public class StatisticServiceImpl implements StatisticService{

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public JsonArray querywalletstatistic(AccountInfo emp,String starttime,String type,String companyid) throws Exception {
		// TODO Auto-generated method stub
		int j =0;
		int [] montharr= {31,28,31,30,31,30,31,31,30,31,30,31};
		int year = Integer.parseInt(starttime.split("-")[0]);
		int month = Integer.parseInt(starttime.split("-")[1]);
		int day = Integer.parseInt(starttime.split("-")[2]);
		if(Integer.parseInt(type)==1){
			j =12;
		}
		if(Integer.parseInt(type)==2){
			if(month==2){
			if(year%4 == 0 && year%100!=0 || year%400 == 0){
				j=29;
			}
		             }else{
				j=montharr[month];
			     }
	}
		
		if(Integer.parseInt(type)==3){j =24;}
		
		List<Map<String,Object>> lstMap = new LinkedList<Map<String,Object>>();
		Map<String, Object>map1 = new HashMap<String, Object>();
		StringBuffer querytotalrecharge = new StringBuffer("select SUM(uc.amount) 'a' from userrecharge uc,userinfo ui,accountinfo ai where uc.fuserinfoid = ui.id and ui.faccountid = ai.id  and uc.paystate =1 and ai.fcompanyid = ")
				.append(Long.parseLong(companyid));
		List<Object> lst0 = databaseHelper.getResultListBySql(querytotalrecharge.toString());
		StringBuffer querycompanyproportion = new StringBuffer("select  ai.proportion  from accountinfo ai where ai.role = 2 and ai.fcompanyid = ").append(Long.parseLong(companyid));
		Sys.out(querycompanyproportion);
		List<Object> lst1 = databaseHelper.getResultListBySql(querycompanyproportion.toString());
		StringBuffer querytotaluser = new StringBuffer("select count(ui.id) from userinfo ui,accountinfo ai where ui.faccountid = ai.id  and ai.fcompanyid = ").append(Long.parseLong(companyid));
		List<Object> lst2 = databaseHelper.getResultListBySql(querytotaluser.toString());
		StringBuffer querythsirecharge =new StringBuffer("select SUM(uc.amount) 'a' from userrecharge uc,userinfo ui,accountinfo ai where uc.fuserinfoid = ui.id and ui.faccountid = ai.id  and uc.paystate =1 and ai.fcompanyid = ")
				.append(Long.parseLong(companyid));
		if(Integer.parseInt(type)==1){
			querythsirecharge.append(" and year(uc.createtime)= ").append(year);
			}
			if(Integer.parseInt(type)==2){
				querythsirecharge.append(" and month(uc.createtime) = ").append(month);
			}
			if(Integer.parseInt(type)==3){
				querythsirecharge.append(" and day(uc.createtime) = ").append(day);
			}
			List<Object> lst4 = databaseHelper.getResultListBySql(querythsirecharge.toString());
		Double totalrecharge =0.0;
		Double proportion = 0.0;
		Integer totaluser = 0;
		Double thisrecharge = 0.0;
		if(lst0==null||lst0.size()==0||lst0.get(0)==null){
			totalrecharge = 0.0;
		}else{totalrecharge = Double.parseDouble(lst0.get(0).toString());}
		if(lst1==null||lst1.size()==0||lst1.get(0)==null){
			proportion = 0.0;
		}else{proportion = Double.parseDouble(lst1.get(0).toString());}
		if(lst2==null||lst2.size()==0||lst2.get(0)==null){
			totaluser = 0;
		}else{totaluser = Integer.parseInt(lst2.get(0).toString());}
		if(lst4==null||lst4.size()==0||lst4.get(0)==null){
			thisrecharge = 0.0;
		}else{thisrecharge = Double.parseDouble(lst4.get(0).toString());}
		map1.put("totaluser", totaluser);
		map1.put("totalrecharge", totalrecharge);
		map1.put("totalincome", totalrecharge*proportion);
		map1.put("thisrecharge", thisrecharge);
		map1.put("thisincome", thisrecharge*proportion);
		lstMap.add(map1);
		
		
		for (int i = 1; i <=j ; i++) {
			StringBuffer sql = new StringBuffer("");
			Map<String,Object> map = new HashMap<String,Object>();
			sql.append("select SUM(uc.amount) 'a' from userrecharge uc,userinfo ui,accountinfo ai where uc.fuserinfoid = ui.id and ui.faccountid = ai.id  and uc.paystate =1 and  year(uc.createtime) =")
			.append(year).append(" and ai.fcompanyid = ").append(Long.parseLong(companyid));
			if(Integer.parseInt(type)==1){
			sql.append(" and month(uc.createtime)= ").append(i);
			}
			if(Integer.parseInt(type)==2){
			sql.append(" and day(uc.createtime) = ").append(i);
			}
			if(Integer.parseInt(type)==3){
				sql.append(" and day(uc.createtime) = ").append(day).append(" and hour(uc.createtime) =").append(i);
			}
			List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
			map.put("amount", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
			if(Integer.parseInt(type)==1){
			map.put("time", i+"月");
			}
			if(Integer.parseInt(type)==2){
				map.put("time", i+"日");
				}
			if(Integer.parseInt(type)==3){
				map.put("time", i+"点");
				}
			lstMap.add(map);
		}
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		return jarr;		
		
	}

	@Override
	public JsonArray queryuserstatistic(AccountInfo emp, String starttime, String type, String companyid)
			throws Exception {
		// TODO Auto-generated method stub
		int j =0;
		int [] montharr= {31,28,31,30,31,30,31,31,30,31,30,31};
		int year = Integer.parseInt(starttime.split("-")[0]);
		int month = Integer.parseInt(starttime.split("-")[1]);
		int day = Integer.parseInt(starttime.split("-")[2]);
		if(Integer.parseInt(type)==1){
			j =12;
		}
		if(Integer.parseInt(type)==2){
			if(month==2){
			if(year%4 == 0 && year%100!=0 || year%400 == 0){
				j=29;
			}
		             }else{
				j=montharr[month];
			     }
	}
		
		if(Integer.parseInt(type)==3){j =24;}
		
		List<Map<String,Object>> lstMap = new LinkedList<Map<String,Object>>();
		Map<String, Object>map1 = new HashMap<String, Object>();
		StringBuffer querynewadd = new StringBuffer("select count(ui.id) from userinfo ui,accountinfo ai where date(ui.createtime) = curdate() and ui.faccountid = ai.id  and ai.fcompanyid = ").append(Long.parseLong(companyid));
				List<Object>lst0 = databaseHelper.getResultListBySql(querynewadd.toString());
		StringBuffer querytotaluser = new StringBuffer("select count(ui.id) from userinfo ui,accountinfo ai where ui.faccountid = ai.id  and ai.fcompanyid = ").append(Long.parseLong(companyid));
		List<Object> lst2 = databaseHelper.getResultListBySql(querytotaluser.toString());
		StringBuffer querythisadd =new StringBuffer("select count(ui.id) from userinfo ui,accountinfo ai where ui.faccountid = ai.id and ai.fcompanyid = ")
				.append(Long.parseLong(companyid));
		if(Integer.parseInt(type)==1){
			querythisadd.append(" and year(ui.createtime)= ").append(year);
			}
			if(Integer.parseInt(type)==2){
				querythisadd.append(" and month(ui.createtime) = ").append(month);
			}
			if(Integer.parseInt(type)==3){
				querythisadd.append(" and day(ui.createtime) = ").append(day);
			}
			List<Object> lst4 = databaseHelper.getResultListBySql(querythisadd.toString());
		
		Integer totaluser = 0;
		Integer newadd = 0;
		Integer thisadd = 0;
		if(lst0==null||lst0.size()==0||lst0.get(0)==null){
			newadd = 0;
		}else{newadd = Integer.parseInt(lst0.get(0).toString());}
		
		if(lst2==null||lst2.size()==0||lst2.get(0)==null){
			totaluser = 0;
		}else{totaluser = Integer.parseInt(lst2.get(0).toString());}
		if(lst4==null||lst4.size()==0||lst4.get(0)==null){
			thisadd = 0;
		}else{thisadd =Integer.parseInt(lst4.get(0).toString());}
		map1.put("totaluser", totaluser);
		map1.put("thisadd", thisadd);
		map1.put("newadd", newadd);
		lstMap.add(map1);
		
		for (int i = 1; i <=j ; i++) {
			StringBuffer sql = new StringBuffer("");
			Map<String,Object> map = new HashMap<String,Object>();
			sql.append("select count(ui.id) from userinfo ui,accountinfo ai where ui.faccountid = ai.id and  year(ui.createtime) =")
			.append(year).append(" and ai.fcompanyid = ").append(Long.parseLong(companyid));
			if(Integer.parseInt(type)==1){
			sql.append(" and month(ui.createtime)= ").append(i);
			}
			if(Integer.parseInt(type)==2){
			sql.append(" and day(ui.createtime) = ").append(i);
			}
			if(Integer.parseInt(type)==3){
				sql.append(" and day(ui.createtime) = ").append(day).append(" and hour(ui.createtime) =").append(i);
			}
			List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
			map.put("nums", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
			if(Integer.parseInt(type)==1){
			map.put("time", i+"月");
			}
			if(Integer.parseInt(type)==2){
				map.put("time", i+"日");
				}
			if(Integer.parseInt(type)==3){
				map.put("time", i+"点");
				}
			lstMap.add(map);
		}
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		return jarr;	
	}

	@Override
	public JsonArray queryuser(AccountInfo emp) throws Exception {
		// TODO Auto-generated method stub
		int [] montharr= {31,28,31,30,31,30,31,31,30,31,30,31};
		int m = new Date().getMonth()+1;
		int y = new Date().getYear()+1900;
		List<Map<String,Object>> lstMap = new LinkedList<Map<String,Object>>();
		for (int i = 1; i <=montharr[m] ; i++) {
		StringBuffer sql = new StringBuffer("");
		Map<String,Object> map = new HashMap<String,Object>();
		sql.append("select count(ui.id) from userinfo ui where   year(ui.createtime) =")
		.append(y).append(" and month(ui.createtime) = ").append(m)
		.append(" and day(ui.createtime) = ").append(i);
		/*Sys.out(sql);*/
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		map.put("nums", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
		map.put("time", i+"日");
		lstMap.add(map);
		}
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		return jarr;	
	}

	@Override
	public JsonArray queryincome(AccountInfo emp) throws Exception {
		// TODO Auto-generated method stub
		int [] montharr= {31,28,31,30,31,30,31,31,30,31,30,31};
		int m = new Date().getMonth()+1;
		int y = new Date().getYear()+1900;
		List<Map<String,Object>> lstMap = new LinkedList<Map<String,Object>>();
		for (int i = 1; i <=montharr[m] ; i++) {
		StringBuffer sql = new StringBuffer("");
		Map<String,Object> map = new HashMap<String,Object>();
		sql.append("select SUM(uc.amount) from userrecharge uc where uc.paystate = 1 and uc.state = 0 and year(uc.createtime) =")
		.append(y).append(" and month(uc.createtime) = ").append(m)
		.append(" and day(uc.createtime) = ").append(i);
		Sys.out(sql);
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		map.put("nums", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
		map.put("time", i+"日");
		lstMap.add(map);
		}
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		return jarr;	
	}

	@Override
	public JsonArray querycash(AccountInfo emp) throws Exception {
		// TODO Auto-generated method stub
		int [] montharr= {31,28,31,30,31,30,31,31,30,31,30,31};
		int m = new Date().getMonth()+1;
		int y = new Date().getYear()+1900;
		List<Map<String,Object>> lstMap = new LinkedList<Map<String,Object>>();
		for (int i = 1; i <=montharr[m] ; i++) {
		StringBuffer sql = new StringBuffer("");
		Map<String,Object> map = new HashMap<String,Object>();
		sql.append("select SUM(ci.amount) from cashinfo ci where ci.state = 2 and year(ci.createtime) =")
		.append(y).append(" and month(ci.createtime) = ").append(m)
		.append(" and day(ci.createtime) = ").append(i);
		/*Sys.out(sql);*/
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		map.put("nums", lst==null?"0":lst.size()==0?"0":lst.get(0)==null?"0":lst.get(0).toString());
		map.put("time", i+"日");
		lstMap.add(map);
		}
		
		String result = new Gson().toJson(lstMap);
		JsonArray jarr = (JsonArray) new JsonParser().parse(result);
		return jarr;	
	}

	//公司
	@Override
	public List<Map<String, Object>> querycompanywalletstatistic(AccountInfo acc, String starttime,
			String type) throws Exception {
		
		
		Calendar now = Calendar.getInstance(); 
		List<Map<String, Object>> lstmap= new ArrayList<Map<String,Object>>();
		
		if(type.equals("1")){
			StringBuffer sql=new StringBuffer("");
			sql.append("select date_format(time, '%Y') createtime,sum(amount) sumamount from proportioninfo ");
			sql.append(accsql(acc));
			
			sql.append("order by date_format(time, '%Y') ");
			List<Object[]> lst=databaseHelper.getResultListBySql(sql.toString());
			if (lst.size()>0&&lst.get(0)!=null) {
				for(Object[] o:lst){
	 				Map<String, Object> map =new HashMap<String, Object>();
	 				map.put("time", o[0].toString()==null?"":o[0].toString()+"年");
	 				map.put("amount", new DecimalFormat("#.00").format(Double.parseDouble(o[1].toString())));
	 				lstmap.add(map);
	 			}
				
			}
			
			
		}else if(type.equals("2")){
			int month=12;
			if(starttime.equals("")||starttime.equals(now.get(Calendar.YEAR)+"")){
				
				month=(now.get(Calendar.MONTH) + 1);
				starttime=now.get(Calendar.YEAR)+"";
				
			}
			for(int i=1;i<=month;i++){
				String time="";
				if(i<10){
					time=starttime+"-"+"0"+i;
				}else{
					time=starttime+"-"+i;
				}
				StringBuffer sql=new StringBuffer("select mytable.sumamount from  ");
				sql.append("(select date_format(time, '%Y-%m') createtime,sum(amount) sumamount from proportioninfo ");
				sql.append(accsql(acc));
				sql.append(")mytable  where mytable.createtime='"+time+"' ");
				List<Object> lst=databaseHelper.getResultListBySql(sql.toString());
				Double amount=lst.isEmpty()||lst.get(0)==null?0:Double.parseDouble(lst.get(0).toString());
				Map<String, Object> map =new HashMap<String, Object>();
 				map.put("time", i+"月");
 				map.put("amount", amount==0?0:new DecimalFormat("#.00").format(amount));
 				lstmap.add(map);
				
			}
		
		}else{ 
 			if(starttime.equals("")||(starttime.split("-")[0].equals(now.get(Calendar.YEAR)+"")&&Integer.parseInt(starttime.split("-")[1])==(now.get(Calendar.MONTH) + 1))){
 				
 				for(int i=1;i<=now.get(Calendar.DAY_OF_MONTH);i++){
 					
 					String month=(now.get(Calendar.MONTH) + 1)+"";
 					String day=i+"";
 					if(now.get(Calendar.MONTH) + 1<10){
 						month="0"+(now.get(Calendar.MONTH) + 1);
 					}
 					if(i<10){
 						day="0"+i;
 					}
 					String time=now.get(Calendar.YEAR)+"-"+month+"-"+day;
 					StringBuffer sql= new StringBuffer("select mytable.sumamount from  ");
 					sql.append("(select date_format(time, '%Y-%m-%d') createtime,sum(amount) sumamount from proportioninfo ");
 					sql.append(accsql(acc));
 					sql.append(")mytable  where mytable.createtime='"+time+"' ");
 					List<Object> mylist1=databaseHelper.getResultListBySql(sql.toString());
 					Double amount= mylist1.isEmpty()||mylist1.get(0)==null?0:Double.parseDouble(mylist1.get(0).toString());
 					Map<String, Object> map =new HashMap<String, Object>();
 	 				map.put("time", day+"日");
 	 				map.put("amount", amount==0?0:new DecimalFormat("#.00").format(amount));
 	 				lstmap.add(map);
 				}
 			}else{
 				
 				int [] arr={31,28,31,30,31,30,31,31,30,31,30,31};
 				int year=Integer.parseInt(starttime.split("-")[0]);
 				int month=Integer.parseInt(starttime.split("-")[1]);
 				if(year%4==0||(year%100==0&&year%400==0)){
 					arr[1]=29;
 				}
 				String mymonth=month+"";
 				
 				for(int i=1;i<=arr[month-1];i++)	{
 					String day=i+"";
 					if(month<10){
 						mymonth="0"+month;
 					}
 					if(i<10){
 						day="0"+i;
 					}
 					String time=year+"-"+mymonth+"-"+day;
 					StringBuffer sql= new StringBuffer("select mytable.sumamount from  ");
 					sql.append("(select date_format(time, '%Y-%m-%d') createtime,sum(amount) sumamount from proportioninfo ");
 					sql.append(accsql(acc));
 					sql.append(")mytable  where mytable.createtime='"+time+"' ");
 					List<Object> mylist1=databaseHelper.getResultListBySql(sql.toString());
 					Double amount= mylist1.isEmpty()||mylist1.get(0)==null?0:Double.parseDouble(mylist1.get(0).toString());
 					Map<String, Object> map =new HashMap<String, Object>();
 	 				map.put("time", day+"日");
 	 				map.put("amount", amount==0?0:new DecimalFormat("#.00").format(amount));
 	 				lstmap.add(map);
 					
 				}
 				
 			}
 		}
		return lstmap;
	}

	
	
	public StringBuffer accsql(AccountInfo acc)throws Exception{
		StringBuffer accsql=new StringBuffer("");
		int role=acc.getRole();
		if(role==2||role==3){
			accsql.append("where fcompanyid="+acc.getFcompanyid()+" and role=2  ");
		}else if(role==4||role==5){
			accsql.append("where faccountinfoid="+acc.getId()+"  ");
		}else{
			throw new Exception("登陆人不合法");
		}
		
		return accsql;
	}
	
	
	
	@Override
	public Map<String, Object> queryuserandmoney(AccountInfo acc) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		int role=acc.getRole();
		long companyid=acc.getFcompanyid();
		StringBuffer sql1=new StringBuffer();
		StringBuffer sql2=new StringBuffer();
		StringBuffer sql3=new StringBuffer();
		StringBuffer sql4=new StringBuffer();
		StringBuffer sql5=new StringBuffer();
		if(role==2||role==3){
			
			sql1.append("select IFNULL(count(id),0) from userinfo where faccountid in (select id from accountinfo where fcompanyid="+companyid+" and role=5) and state !=-2 ");
			sql2.append("select IFNULL(SUM(amount),0) from proportioninfo where fcompanyid="+companyid+" and role>0 ");
			sql3.append("select IFNULL(SUM(amount),0) from proportioninfo where fcompanyid="+companyid+" and role =2  ");
			sql4.append("select IFNULL(SUM(amount),0) from proportioninfo where fcompanyid="+companyid+" and role>0 and to_days(time) = to_days(now()) ");
			sql5.append("select IFNULL(SUM(amount),0) from proportioninfo where fcompanyid="+companyid+" and role=2 and to_days(time) = to_days(now()) ");
	
		}else if (role==4){
			long jilid=acc.getId();
			sql1.append("select count(id) from userinfo where faccountid in ( select id from accountinfo where fparentid="+jilid+") and state != -2 ");
		    
			sql2.append("select IFNULL(SUM(amount),0) from proportioninfo where faccountinfoid="+jilid+" OR faccountinfoid in (select id from accountinfo where fparentid="+jilid+" ) ");
			sql3.append("select IFNULL(SUM(amount),0) from proportioninfo where  faccountinfoid="+jilid+"  ");
			sql4.append("select IFNULL(SUM(amount),0) from proportioninfo where (faccountinfoid="+jilid+" OR faccountinfoid in (select id from accountinfo where fparentid="+jilid+" )) and to_days(time) = to_days(now()) ");
			sql5.append("select IFNULL(SUM(amount),0) from proportioninfo where  faccountinfoid="+jilid+" and to_days(time) = to_days(now()) ");
		}else if(role==5){
			long ywyid=acc.getId();
			sql1.append("select IFNULL(count(id),0) from userinfo where faccountid ="+ywyid+" and state != -2 ");
			sql2.append("select IFNULL(SUM(amount),0) from proportioninfo where  faccountinfoid="+ywyid+" ");
			sql3.append("select IFNULL(SUM(amount),0) from proportioninfo where  faccountinfoid="+ywyid+"  ");
			sql4.append("select IFNULL(SUM(amount),0) from proportioninfo where  faccountinfoid="+ywyid+" and to_days(time) = to_days(now()) ");
			sql5.append("select IFNULL(SUM(amount),0) from proportioninfo where  faccountinfoid="+ywyid+" and to_days(time) = to_days(now()) ");
		}
		

		//累计推广用户
		List<Object> countusers=databaseHelper.getResultListBySql(sql1.toString());
		map.put("countusers", countusers.get(0)==null?"0":countusers.get(0).toString());
		
		//累计总收益
		List<Object> countmoney=databaseHelper.getResultListBySql(sql2.toString());
		double money=countmoney.isEmpty()?0.00:Double.parseDouble(countmoney.get(0).toString());
		map.put("countmoney", money);
		
	
		//累积净收益
		List<Object> myinmoneylist=databaseHelper.getResultListBySql(sql3.toString());
		double myinmoney=myinmoneylist.isEmpty()?0.00:Double.parseDouble(myinmoneylist.get(0).toString());
		map.put("myinmoney", new DecimalFormat("#.00").format(myinmoney));
		
		
		//今日总收益
		List<Object> todaycountmoney=databaseHelper.getResultListBySql(sql4.toString());
		double todaymoney=todaycountmoney.isEmpty()?0.00:Double.parseDouble(todaycountmoney.get(0).toString());
		map.put("todaymoney", todaymoney);
		
		//今日净收益
		List<Object> todayinmoneylist=databaseHelper.getResultListBySql(sql4.toString());
		double todayinmoney=todayinmoneylist.isEmpty()?0.00:Double.parseDouble(todayinmoneylist.get(0).toString());
	    map.put("todayinmoney", new DecimalFormat("#.00").format(todayinmoney));
		return map;
	}

	@Override
	public List<Map<String, Object>> queryusersstatistic(AccountInfo acc, String starttime, String type)
			throws Exception {
		Calendar now = Calendar.getInstance(); 
		List<Map<String, Object>> lstmap= new ArrayList<Map<String,Object>>();
		
		if(type.equals("1")){
			StringBuffer sql=new StringBuffer("");
			sql.append("select date_format(createtime, '%Y')time,count(id) from userinfo  where state !=-2  ");
			sql.append(usersql(acc));
			sql.append("GROUP BY date_format(createtime, '%Y') ");
			List<Object[]> lst=databaseHelper.getResultListBySql(sql.toString());
			for(Object[] o:lst){
 				Map<String, Object> map =new HashMap<String, Object>();
 				map.put("time", o[0].toString()+"年");
 				map.put("amount", Integer.parseInt(o[1].toString()));
 				lstmap.add(map);
 			}
			
		}else if(type.equals("2")){
			int month=12;
			if(starttime.equals("")||starttime.equals(now.get(Calendar.YEAR)+"")){
				
				month=(now.get(Calendar.MONTH) + 1);
				starttime=now.get(Calendar.YEAR)+"";
				
			}
			for(int i=1;i<=month;i++){
				String time="";
				if(i<10){
					time=starttime+"-"+"0"+i;
				}else{
					time=starttime+"-"+i;
				}
				StringBuffer sql=new StringBuffer("select mytable.countid from   ");
				sql.append("(select date_format(createtime, '%Y-%m')time,count(id) countid  from userinfo  where state !=-2  ");
				sql.append(usersql(acc));
				sql.append("GROUP BY date_format(createtime, '%Y-%m')) mytable where mytable.time='"+time+"' ");
				List<Object> lst=databaseHelper.getResultListBySql(sql.toString());
				int amount=lst.isEmpty()||lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
				Map<String, Object> map =new HashMap<String, Object>();
 				map.put("time", i+"月");
 				map.put("amount", amount==0?0:amount);
 				lstmap.add(map);
				
			}
		
		}else{ 
 			if(starttime.equals("")||(starttime.split("-")[0].equals(now.get(Calendar.YEAR)+"")&&Integer.parseInt(starttime.split("-")[1])==(now.get(Calendar.MONTH) + 1))){
 				
 				for(int i=1;i<=now.get(Calendar.DAY_OF_MONTH);i++){
 					
 					String month=(now.get(Calendar.MONTH) + 1)+"";
 					String day=i+"";
 					if(now.get(Calendar.MONTH) + 1<10){
 						month="0"+(now.get(Calendar.MONTH) + 1);
 					}
 					if(i<10){
 						day="0"+i;
 					}
 					String time=now.get(Calendar.YEAR)+"-"+month+"-"+day;
 					StringBuffer sql=new StringBuffer("select mytable.countid from   ");
 					sql.append("(select date_format(createtime, '%Y-%m-%d')time,count(id) countid  from userinfo  where state !=-2  ");
 					sql.append(usersql(acc));
 					sql.append("GROUP BY date_format(createtime, '%Y-%m-%d')) mytable where mytable.time='"+time+"' ");
 					List<Object> mylist1=databaseHelper.getResultListBySql(sql.toString());
 					int amount= mylist1.isEmpty()||mylist1.get(0)==null?0:Integer.parseInt(mylist1.get(0).toString());
 					Map<String, Object> map =new HashMap<String, Object>();
 	 				map.put("time", day+"日");
 	 				map.put("amount", amount==0?0:amount);
 	 				lstmap.add(map);
 				}
 			}else{
 				
 				int [] arr={31,28,31,30,31,30,31,31,30,31,30,31};
 				int year=Integer.parseInt(starttime.split("-")[0]);
 				int month=Integer.parseInt(starttime.split("-")[1]);
 				if(year%4==0||(year%100==0&&year%400==0)){
 					arr[1]=29;
 				}
 				String mymonth=month+"";
 				
 				for(int i=1;i<=arr[month-1];i++)	{
 					String day=i+"";
 					if(month<10){
 						mymonth="0"+month;
 					}
 					if(i<10){
 						day="0"+i;
 					}
 					String time=year+"-"+mymonth+"-"+day;
 					StringBuffer sql=new StringBuffer("select mytable.countid from   ");
 					sql.append("(select date_format(createtime, '%Y-%m-%d')time,count(id) countid  from userinfo  where state !=-2  ");
 					sql.append(usersql(acc));
 					sql.append("GROUP BY date_format(createtime, '%Y-%m-%d')) mytable where mytable.time='"+time+"' ");
 					List<Object> mylist1=databaseHelper.getResultListBySql(sql.toString());
 					int amount= mylist1.isEmpty()||mylist1.get(0)==null?0:Integer.parseInt(mylist1.get(0).toString());
 					Map<String, Object> map =new HashMap<String, Object>();
 	 				map.put("time", day+"日");
 	 				map.put("amount", amount==0?0:amount);
 	 				lstmap.add(map);
 					
 				}
 				
 			}
 		}
		return lstmap;
	}
	
	public StringBuffer usersql(AccountInfo acc)throws Exception{
		StringBuffer usersql=new StringBuffer("");
		int role=acc.getRole();
		if(role==2||role==3){
			usersql.append("and faccountid in (select id from accountinfo where fcompanyid="+acc.getFcompanyid()+" and role=5)  ");
		}else if(role==4){
			usersql.append("and faccountid in (select id from accountinfo where fparentid="+acc.getId()+" and role=5)  ");
		}else if(role==5){
			usersql.append("and faccountid ="+acc.getId()+"  ");
		}else{
			throw new Exception("登陆人不合法");
		}
		
		return usersql;
	}

	@Override
	public Map<String, Object> querymyadduser(AccountInfo acc) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		//累计推广用户
		StringBuffer allsql=new StringBuffer("select count(id) countid  from userinfo  where state !=-2  ");
		allsql.append(usersql(acc));
		List<Object> allusers=databaseHelper.getResultListBySql(allsql.toString());
		map.put("allusers", allusers.get(0)==null?"0":allusers.get(0).toString());
   
		//今日新增用户
		StringBuffer todaysql=new StringBuffer("select count(id) countid  from userinfo  where state !=-2  ");
		todaysql.append(usersql(acc));
		todaysql.append("and to_days(createtime) = to_days(now()) ");
		List<Object> todayusers=databaseHelper.getResultListBySql(todaysql.toString());
		map.put("todayusers", todayusers.get(0)==null?"0":todayusers.get(0).toString());
	
		
		//本月推广用户
		StringBuffer monthsql=new StringBuffer("select count(id) countid  from userinfo  where state !=-2  ");
		monthsql.append(usersql(acc));
		monthsql.append("and date_format(createtime,'%Y%m') = date_format(NOW(), '%Y%m')  ");
		List<Object> monthusers=databaseHelper.getResultListBySql(monthsql.toString());
		map.put("monthusers", monthusers.get(0)==null?"0":monthusers.get(0).toString());
		return map;
		
	}
	
}
