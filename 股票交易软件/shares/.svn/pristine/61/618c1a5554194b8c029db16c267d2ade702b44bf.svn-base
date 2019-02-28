package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxy.isw.entity.UserChange;
import com.hxy.isw.entity.UserChangeLog;
import com.hxy.isw.entity.UserEmployeeRelation;
import com.hxy.isw.service.CompanyChangeUserService;
import com.hxy.isw.util.DatabaseHelper;

@Service
public class CompanyUserChangeServiceImp implements CompanyChangeUserService{
	
	@Autowired
	DatabaseHelper databasehelper;

	@Override
	public List<Map<String, Object>> queryjl(long companyid) throws Exception {
		String sql="select id, name from accountinfo where fcompanyid ="+companyid+" and role=4 and state=0";
		List<Object[]> lst=databasehelper.getResultListBySql(sql);
		List<Map<String, Object>> lstmap =new ArrayList<Map<String,Object>>();
		for(Object[] o:lst){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("gengenlid", o[0].toString());
			map.put("gengenlname", o[1].toString());
			lstmap.add(map);
		}
		return lstmap;
	}

	@Override
	public List<Map<String, Object>> queryywy(long jlid) throws Exception {
		String sql="select id, name from accountinfo where fparentid ="+jlid+" and state=0";
		List<Object[]> lst=databasehelper.getResultListBySql(sql);
		List<Map<String, Object>> lstmap =new ArrayList<Map<String,Object>>();
		for(Object[] o:lst){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("salesid", o[0].toString());
			map.put("salesname", o[1].toString());
			lstmap.add(map);
		}
		return lstmap;
	}

	@Override
	public int countqueryuser(long companyid, long salesid,long gengalid, String accname, String type) throws Exception {
		StringBuffer sql=new StringBuffer("select count(ua.id) from ");
		sql.append(sqlapp(companyid, salesid, gengalid, accname, type));
		List<Object> lst = databasehelper.getResultListBySql(sql.toString());
		return lst==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> queryuser(long companyid, long salesid,long gengalid, String accname, String type, int start,
			int limit) throws Exception {
		StringBuffer sql=new StringBuffer("SELECT ua.id,ua.nickname,ua.mobile,ua.ywyname,a2.`name`,ua.faccountid from  ");
		sql.append(sqlapp(companyid, salesid, gengalid, accname, type));
		List<Object[]> lst=databasehelper.getResultListBySql(sql.toString(), start, limit);
		List<Map<String, Object>> lstmap=new ArrayList<Map<String,Object>>();
		for (Object[] o : lst) {
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("id",o[0].toString() );
			map.put("nickname",o[1].toString());
			map.put("mobile",o[2].toString());
			map.put("ywyname",o[3].toString());
			map.put("jlname",o[4].toString());
			map.put("faccountid",o[5].toString());
			map.put("companyid",companyid+"");
			lstmap.add(map);
		}
		return lstmap;
	}
	 private StringBuffer sqlapp(long companyid, long salesid,long gengalid, String accname, String type){
		 StringBuffer sqlapp=new StringBuffer("");
		 if(type.equals("1")){
			 sqlapp.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid,a.`name` as ywyname,u.faccountid from userinfo u  ");
			 sqlapp.append("LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 and u.faccountid="+salesid+") ua ");
			 sqlapp.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id ");
		 }else if(type.equals("2")){
			 sqlapp.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid,a.`name` as ywyname,u.faccountid from userinfo u  ");
			 sqlapp.append("LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 and u.faccountid in (SELECT id from accountinfo where fparentid ="+gengalid+" )) ua ");
			 sqlapp.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id "); 
		 }else if(type.equals("3")){
			 sqlapp.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid,a.`name` as ywyname,u.faccountid from userinfo u  ");
			 sqlapp.append("LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 and ");
			 sqlapp.append("(u.faccountid in (select id from accountinfo where name like '%"+accname+"%' and fcompanyid="+companyid+") ");
			 sqlapp.append("or u.faccountid in (select id from accountinfo where fparentid in (select id from accountinfo where name like '%"+accname+"%' and fcompanyid="+companyid+")))");
			 sqlapp.append(") ua ");
			 sqlapp.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id ");
		 }else{
			 sqlapp.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid,a.`name` as ywyname,u.faccountid from userinfo u  ");
			 sqlapp.append("LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 and ");
			 sqlapp.append("(u.faccountid in (select id from accountinfo where name like '%"+accname+"%' and fcompanyid="+companyid+")) ");
			 sqlapp.append(") ua ");
			 sqlapp.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id ");
		 }
		 return sqlapp;
	 }

	@Override
	public void confirmtochange(String users, long ywyid, long fdoitid, long incompanyid) {
		Date date=new Date();
		UserChange uc=new UserChange();
		uc.setInfcompanyid(incompanyid);
		uc.setFaccountid(ywyid);
		uc.setFdoitid(fdoitid);
		uc.setCreatetime(date);
		uc.setState(0);
		databasehelper.persistObject(uc);
		String[] usersarr= users.split(",");
		String userids="";
		for (int i = 0; i < usersarr.length; i++) {
			String[] user=usersarr[i].split("-");
			 userids+=user[0]+",";
			 UserChangeLog ucl=new UserChangeLog();
			 ucl.setUserchangeid(uc.getId());
			 ucl.setFuserid(Long.parseLong(user[0]));
			 ucl.setOutfcompanyid(Long.parseLong(user[1]));
			 ucl.setOutfaccountid(Long.parseLong(user[2]));
			 ucl.setInfcompanyid(incompanyid);
			 ucl.setInfaccountid(ywyid);
			 ucl.setFdoitid(fdoitid);
			 ucl.setCreatetime(date);
			 ucl.setState(0);
			 //加入记录表
			 databasehelper.persistObject(ucl);		 
		}
		userids=userids.substring(0, userids.length()-1);
		System.out.println(userids);
		String sql1="UPDATE userinfo  u set u.faccountid="+ywyid+" where u.id in ("+userids+")";
		 databasehelper.executeNativeSql(sql1);
		
	}

	private StringBuffer appHql(String starttime,String endtime){
    	StringBuffer apphql = new StringBuffer("");
    	if(starttime==""&&endtime==""){
    		
    	}else if(starttime==""&&endtime!=""){ 
    		apphql.append(" u.createtime<='"+endtime+"' and ");
    		
    	}else if(starttime!=""&&endtime==""){
    		apphql.append(" u.createtime>='"+starttime+"' and  ");
    	}else{
    		apphql.append(" u.createtime>='"+starttime+"' and u.createtime<='"+endtime+"' and ");
    	}
    	return apphql;
    }
	@Override
	public List<Map<String, Object>> querychange(long companyid,String starttime, String endtime, int start, int limit)
			throws Exception {
		StringBuffer hql= new StringBuffer("select  u from UserChange u where ");
		hql.append(appHql(starttime, endtime));
		hql.append("u.state=0 and u.fdoitid in (select a.id from  AccountInfo a where a.fcompanyid="+companyid+" ) order by u.createtime desc");
		List<Object> lst = databasehelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object o : lst) {
			UserChange u=(UserChange) o;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id",u.getId()+"");
			
			String sql1="select count(id) from userchangelog where userchangeid="+u.getId()+"";
			map.put("usernum", databasehelper.getResultListBySql(sql1).get(0).toString());
			
			String sql3="SELECT name from accountinfo where id="+u.getFaccountid()+"";
			map.put("insalesman", databasehelper.getResultListBySql(sql3).get(0).toString());
			
			String sql4="SELECT name from accountinfo where id="+u.getFdoitid()+"";
			map.put("doitman", databasehelper.getResultListBySql(sql4).get(0).toString());

			map.put("createtime",u.getCreatetime().toString());
			lstMap.add(map);
		}
		return lstMap;
		
	}
	@Override
	public int countquerychange(long companyid,String starttime, String endtime) throws Exception {
		StringBuffer hql= new StringBuffer("select  count(u.id) from UserChange u where ");
		hql.append(appHql(starttime, endtime));
		hql.append("u.state=0 and u.fdoitid in (select a.id from  AccountInfo a where a.fcompanyid="+companyid+" ) order by u.createtime desc");
		List<Object> list= databasehelper.getResultListByHql(hql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}

	@Override
	public int countquerychangejl(long jlid, String starttime, String endtime) throws Exception {
		StringBuffer hql= new StringBuffer("select  count(u.id) from UserChange u where ");
		hql.append(appHql(starttime, endtime));
		hql.append("u.state=0 and u.fdoitid="+jlid+" order by u.createtime desc");
		List<Object> list= databasehelper.getResultListByHql(hql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> querychangejl(long jlid, String starttime, String endtime, int start, int limit)
			throws Exception {
		StringBuffer hql= new StringBuffer("select  u from UserChange u where ");
		hql.append(appHql(starttime, endtime));
		hql.append("u.state=0 and u.fdoitid="+jlid+"  order by u.createtime desc");
		List<Object> lst = databasehelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object o : lst) {
			UserChange u=(UserChange) o;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id",u.getId()+"");
			
			String sql1="select count(id) from userchangelog where userchangeid="+u.getId()+"";
			map.put("usernum", databasehelper.getResultListBySql(sql1).get(0).toString());
			
			String sql3="SELECT name from accountinfo where id="+u.getFaccountid()+"";
			map.put("insalesman", databasehelper.getResultListBySql(sql3).get(0).toString());
			
			String sql5="select name from accountinfo where id=(select outfaccountid  from userchangelog  where  userchangeid ="+u.getId()+" LIMIT 1)";
			map.put("outsalesman", databasehelper.getResultListBySql(sql5).get(0).toString());
			
			String sql4="SELECT name from accountinfo where id="+u.getFdoitid()+"";
			map.put("doitman", databasehelper.getResultListBySql(sql4).get(0).toString());

			map.put("createtime",u.getCreatetime().toString());
			lstMap.add(map);
		}
		return lstMap;
	}
}
