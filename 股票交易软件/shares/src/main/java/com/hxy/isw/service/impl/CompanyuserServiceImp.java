package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hxy.isw.service.CompanyuserService;
import com.hxy.isw.util.DatabaseHelper;

@Repository
public class CompanyuserServiceImp implements CompanyuserService {
	
	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public int comcountuserinfo(long companyid,String selecttype,String querybyuserinfo, String querybyaccountinfo) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u");
		sql.append(companysql(querybyuserinfo, querybyaccountinfo));
		if(selecttype.equals("1")){
			sql.append("and u.state=0 ");
		}else{
			sql.append("and u.state=-1 ");
		}
		sql.append("and u.faccountid in (select a4.id from accountinfo a4 where a4.fcompanyid="+companyid+") ");
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> comqueryuserinfo(long companyid,String selecttype,String querybyuserinfo, String querybyaccountinfo, int start,
			int limit) throws Exception {
		StringBuffer sql=new StringBuffer("select u.id,u.name,u.nickname, u.mobile,u.totalrecharge,u.golds,u.virtualcapital,u.createtime,u.state from userinfo u ");
		sql.append(companysql(querybyuserinfo, querybyaccountinfo));
		if(selecttype.equals("1")){
			sql.append("and u.state=0 ");
		}else{
			sql.append("and u.state=-1 ");
		}
		sql.append("and u.faccountid in (select a4.id from accountinfo a4 where a4.fcompanyid="+companyid+") ");
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object[] object : lst) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", object[0].toString());
			map.put("nickname", object[1]==null?"":object[1].toString());
			map.put("name",object[2]==null?"":object[2].toString());
			map.put("mobile",object[3]==null?"":object[3].toString());
			map.put("totalrecharge",object[4]==null?"":object[4].toString());
			map.put("golds", object[5]==null?"":object[5].toString());
			map.put("virtualcapital", object[6]==null?"":object[6].toString());
			map.put("createtime",object[7]==null?"":object[7].toString());
			map.put("state",object[8]==null?"":object[8].toString());
			lstMap.add(map);
		}
		
		return lstMap;
	}

	
	private StringBuffer companysql(String querybyuserinfo,
			String querybyaccountinfo){
		StringBuffer sql=new StringBuffer("");
		//按业务员姓名查询
		sql.append(" where  (u.faccountid in (select a1.id from accountinfo a1 where a1.name LIKE '%"+querybyaccountinfo+"%')");
		//按公司经理姓名查询
		sql.append("or u.faccountid in (select a2.id from accountinfo a2 where a2.fparentid in(select a3.id from accountinfo a3 where a3.name LIKE '%"+querybyaccountinfo+"%')))");
		//按用户的姓名或手机号查询
		sql.append("and (u.mobile like '%"+querybyuserinfo+"%' OR u.name like '%"+querybyuserinfo+"%')");
		return sql;
	}
	
	private StringBuffer generalsql(String querybyuserinfo,
			String querybyaccountinfo){
		StringBuffer sql=new StringBuffer("");
		//按业务员姓名查询
		sql.append(" where  u.faccountid in (select a1.id from accountinfo a1 where a1.name LIKE '%"+querybyaccountinfo+"%')");
		//按用户的姓名或手机号查询
		sql.append("and (u.mobile like '%"+querybyuserinfo+"%' OR u.name like '%"+querybyuserinfo+"%')");
		return sql;
	}
	
	private StringBuffer salesmansql(String querybyuserinfo){
		StringBuffer sql=new StringBuffer("");
		//按用户的姓名或手机号查询
		sql.append("(u.mobile like '%"+querybyuserinfo+"%' OR u.name like '%"+querybyuserinfo+"%')");
		return sql;
	}
	

	@Override
	public int oldusernum(long companyid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where (u.state=0 or u.state=-1) ");
		sql.append("and u.faccountid in (select a4.id from accountinfo a4 where a4.fcompanyid="+companyid+") ");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString()) ;
	}

	@Override
	public int todayusernum(long companyid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where (u.state=0 or u.state=-1) and TO_DAYS(u.createtime)=TO_DAYS(NOW()) ");
		sql.append("and u.faccountid in (select a4.id from accountinfo a4 where a4.fcompanyid="+companyid+") ");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}

	@Override
	public int countgeneralqueryuserinfo(long generalid, String selecttype, String querybyuserinfo,
			String querybyaccountinfo) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u ");
		sql.append(generalsql(querybyuserinfo, querybyaccountinfo));
		if(selecttype.equals("1")){
			sql.append("and u.state=0 ");
		}else{
			sql.append("and u.state=-1 ");
		}
		sql.append("and u.faccountid in (select a2.id from accountinfo a2 where a2.fparentid="+generalid+" )");
		System.out.println(sql.toString());
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> generalqueryuserinfo(long generalid, String selecttype, String querybyuserinfo,
			String querybyaccountinfo, int start, int limit) throws Exception {
		StringBuffer sql=new StringBuffer("select u.id,u.name,u.nickname, u.mobile,u.totalrecharge,u.golds,u.virtualcapital,u.createtime,u.state from userinfo u ");
		sql.append(generalsql(querybyuserinfo, querybyaccountinfo));
		if(selecttype.equals("1")){
			sql.append("and u.state=0 ");
		}else{
			sql.append("and u.state=-1 ");
		}
		sql.append("and u.faccountid in (select a2.id from accountinfo a2 where a2.fparentid="+generalid+" )");
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object[] object : lst) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", object[0].toString());
			map.put("nickname", object[1]==null?"":object[1].toString());
			map.put("name",object[2]==null?"":object[2].toString());
			map.put("mobile",object[3]==null?"":object[3].toString());
			map.put("totalrecharge",object[4]==null?"":object[4].toString());
			map.put("golds", object[5]==null?"":object[5].toString());
			map.put("virtualcapital", object[6]==null?"":object[6].toString());
			map.put("createtime",object[7]==null?"":object[7].toString());
			map.put("state",object[8]==null?"":object[8].toString());
			lstMap.add(map);
		}
		return lstMap;
	}

	@Override
	public int generaloldusernum(long generalid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where (u.state=0 or u.state=-1) ");
		sql.append("and u.faccountid in (select a2.id from accountinfo a2 where a2.fparentid="+generalid+" )");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString()) ;
	}

	@Override
	public int generaltodayusernum(long generalid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where (u.state=0 or u.state=-1) and TO_DAYS(u.createtime)=TO_DAYS(NOW()) ");
		sql.append("and u.faccountid in (select a2.id from accountinfo a2 where a2.fparentid="+generalid+" )");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}

	@Override
	public int countsalesmanqueryuserinfo(long salesmanid, String selecttype, String querybyuserinfo) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where ");
		sql.append(salesmansql(querybyuserinfo));
		if(selecttype.equals("1")){
			sql.append("and u.state=0 ");
		}else{
			sql.append("and u.state=-1 ");
		}
		sql.append("and u.faccountid ="+salesmanid+"");
		System.out.println(sql.toString());
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> salesmanqueryuserinfo(long salesman, String selecttype, String querybyuserinfo,
			int start, int limit) throws Exception {
		StringBuffer sql=new StringBuffer("select u.id,u.name,u.nickname, u.mobile,u.totalrecharge,u.golds,u.virtualcapital,u.createtime,u.state from userinfo u where  ");
		sql.append(salesmansql(querybyuserinfo));
		if(selecttype.equals("1")){
			sql.append("and u.state=0 ");
		}else{
			sql.append("and u.state=-1 ");
		}
		sql.append("and u.faccountid ="+salesman+"");
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object[] object : lst) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", object[0].toString());
			map.put("nickname", object[1]==null?"":object[1].toString());
			map.put("name",object[2]==null?"":object[2].toString());
			map.put("mobile",object[3]==null?"":object[3].toString());
			map.put("totalrecharge",object[4]==null?"":object[4].toString());
			map.put("golds", object[5]==null?"":object[5].toString());
			map.put("virtualcapital", object[6]==null?"":object[6].toString());
			map.put("createtime",object[7]==null?"":object[7].toString());
			map.put("state",object[8]==null?"":object[8].toString());
			lstMap.add(map);
		}
		return lstMap;
	}

	@Override
	public int salesmanoldusernum(long salesmanid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where (u.state=0 or u.state=-1) ");
		sql.append("and u.faccountid ="+salesmanid+"");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString()) ;
	}

	@Override
	public int salesmantodayusernum(long salesmanid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where (u.state=0 or u.state=-1) and TO_DAYS(u.createtime)=TO_DAYS(NOW()) ");
		sql.append("and u.faccountid ="+salesmanid+"");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}
}
