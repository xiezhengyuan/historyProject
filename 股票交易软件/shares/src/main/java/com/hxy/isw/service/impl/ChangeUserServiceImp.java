package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.Company;
import com.hxy.isw.entity.UserChange;
import com.hxy.isw.entity.UserChangeLog;
import com.hxy.isw.entity.UserEmployeeRelation;
import com.hxy.isw.service.ChangeUserService;
import com.hxy.isw.util.DatabaseHelper;

@Service
public class ChangeUserServiceImp implements ChangeUserService{

	@Autowired
	DatabaseHelper  databaseHelper;
	@Override
	public List<Map<String, Object>> querycompany(String companyname) throws Exception {
		StringBuffer Hql=new StringBuffer("select c from Company c ");
		Hql.append("where c.company like '%"+companyname+"%' and c.state !=-2");
		List<Map<String, Object>>  lstmap=new ArrayList<Map<String,Object>>();
		List<Object> lst = databaseHelper.getResultListByHql(Hql.toString());
		for (Object o : lst) {
			Company c=(Company) o; 
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", c.getId().toString());
			map.put("company", c.getCompany().toString());
			lstmap.add(map);
		}
		return lstmap;
	}
	@Override
	public int countqueryuserbycompany(long companyid ,long jlid,long ywyid) throws Exception {
		StringBuffer sql=new StringBuffer("SELECT count(ua.id) from  ");
		sql.append(addquerysql(companyid, jlid, ywyid));
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst==null?0:Integer.parseInt(lst.get(0).toString());
	}
	@Override
	public List<Map<String, Object>> queryuserbycompany(long companyid,long jlid,long ywyid, int start, int limit) throws Exception {
		StringBuffer sql=new StringBuffer("SELECT ua.id,ua.nickname,ua.mobile,ua.ywyname,a2.`name`,ua.faccountid from ");
		sql.append(addquerysql(companyid, jlid, ywyid));
		List<Object[]> lst=databaseHelper.getResultListBySql(sql.toString(), start, limit);
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
	
	public StringBuffer addquerysql(long companyid,long jlid,long ywyid){
		
		StringBuffer querysql=new StringBuffer("");
		if(companyid==0l){
			querysql.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid, a.`name` as ywyname ,u.faccountid  from userinfo u LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 ) ua ");
			querysql.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id");
			return querysql;
		}
		if(jlid==0l){
			querysql.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid, a.`name` as ywyname ,u.faccountid  from userinfo u LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 and u.faccountid in (SELECT a1.id from accountinfo a1 where a1.fcompanyid="+companyid+")) ua ");
			querysql.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id");
			return querysql;
		}
		
		if(ywyid==0l){
			querysql.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid,a.`name` as ywyname ,u.faccountid from userinfo u LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 and u.faccountid in (SELECT a1.id from accountinfo a1 where a1.fparentid="+jlid+")) ua ");
			querysql.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id");
			return querysql;
		}
		else{
			querysql.append("(SELECT u.id,u.nickname,u.mobile,a.fparentid,a.`name` as ywyname ,u.faccountid from userinfo u LEFT JOIN accountinfo a on u.faccountid=a.id where u.state != -2 and u.faccountid ="+ywyid+") ua ");
			querysql.append("LEFT JOIN accountinfo a2 ON ua.fparentid=a2.id");
			return querysql;
		}
		
		
	}
	
	
	@Override
	public List<Map<String, Object>> queryjlbycompanyid(long companyid) throws Exception {
		StringBuffer Hql=new StringBuffer("SELECT  a  from AccountInfo a where  a.role=4 and a.state=0 ");
		Hql.append("and  a.fcompanyid ="+companyid+"");
		List<Map<String, Object>>  lstmap=new ArrayList<Map<String,Object>>();
		List<Object> lst = databaseHelper.getResultListByHql(Hql.toString());
		for (Object o : lst) {
			AccountInfo a=(AccountInfo) o; 
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", a.getId().toString());
			map.put("name", a.getName().toString());
			lstmap.add(map);
		}
		return lstmap;
	}
	@Override
	public List<Map<String, Object>> queryywybyjlid(long jlid) throws Exception {
		StringBuffer Hql=new StringBuffer("select a  from AccountInfo a where  a.role=5 and a.state=0 ");
		Hql.append("AND a.fparentid="+jlid+" ");
		List<Map<String, Object>>  lstmap=new ArrayList<Map<String,Object>>();
		List<Object> lst = databaseHelper.getResultListByHql(Hql.toString());
		for (Object o : lst) {
			AccountInfo a=(AccountInfo) o; 
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", a.getId().toString());
			map.put("name", a.getName().toString());
			lstmap.add(map);
		}
		return lstmap;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void confirmtochange(String users, long ywyid, long fdoitid,long incompanyid) throws Exception {

		Date date=new Date();
		UserChange uc=new UserChange();
		uc.setInfcompanyid(incompanyid);
		uc.setFaccountid(ywyid);
		uc.setFdoitid(fdoitid);
		uc.setCreatetime(date);
		uc.setState(0);
		databaseHelper.persistObject(uc);
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
			 databaseHelper.persistObject(ucl);		 
		}
		userids=userids.substring(0, userids.length()-1);
		System.out.println(userids);
		String sql1="UPDATE userinfo  u set u.faccountid="+ywyid+" where u.id in ("+userids+")";
		 databaseHelper.executeNativeSql(sql1);
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
	public List<Map<String, Object>> querychange(String starttime, String endtime, int start, int limit)
			throws Exception {
		StringBuffer hql= new StringBuffer("select  u from UserChange u where ");
		hql.append(appHql(starttime, endtime));
		hql.append("u.state=0 order by u.createtime desc");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object o : lst) {
			UserChange u=(UserChange) o;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id",u.getId()+"");
			
			String sql1="select count(id) from userchangelog where userchangeid="+u.getId()+"";
			map.put("usernum", databaseHelper.getResultListBySql(sql1).get(0).toString());
			
			String sql2="SELECT company from company where id="+u.getInfcompanyid()+"";
			map.put("incompany", databaseHelper.getResultListBySql(sql2).get(0).toString());
			
			String sql3="SELECT name from accountinfo where id="+u.getFaccountid()+"";
			map.put("insalesman", databaseHelper.getResultListBySql(sql3).get(0).toString());
			
			String sql4="SELECT name from accountinfo where id="+u.getFdoitid()+"";
			map.put("doitman", databaseHelper.getResultListBySql(sql4).get(0).toString());

			map.put("createtime",u.getCreatetime().toString());
			lstMap.add(map);
		}
		return lstMap;
		
	}
	@Override
	public int countquerychange(String starttime, String endtime) throws Exception {
		StringBuffer hql= new StringBuffer("select  count(u.id) from UserChange u where ");
		hql.append(appHql(starttime, endtime));
		hql.append("u.state=0 order by u.createtime desc");
		List<Object> list= databaseHelper.getResultListByHql(hql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}
	@Override
	public List<Map<String, Object>> querychangedetail(long id, String querybyuserinfo, int start, int limit)
			throws Exception {
		StringBuffer sql=new StringBuffer("select uu.id, u.nickname,u.mobile,c.company,ai.name FROM ");
		sql.append("(select ucl.id, ucl.fuserid,ucl.outfcompanyid,ucl.outfaccountid from userchangelog ucl ");
		sql.append("where ucl.userchangeid="+id+" and ucl.fuserid in (SELECT ui.id from userinfo  ui where ui.nickname like '%"+querybyuserinfo+"%'  or ui.mobile like '%"+querybyuserinfo+"%' or ui.name like '%"+querybyuserinfo+"%')) uu ");
		sql.append("LEFT JOIN userinfo u on uu.fuserid=u.id ");
		sql.append("left join company c on uu.outfcompanyid=c.id ");
		sql.append("left join accountinfo ai on  uu.outfaccountid=ai.id ");
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for(Object[] o : lst){
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("id", o[0].toString());
			map.put("nickname", o[1].toString());
			map.put("mobile", o[2].toString());
			map.put("outcompany", o[3].toString());
			map.put("outsalesman", o[4].toString());
			lstMap.add(map);
		}
		return lstMap;
	}
	@Override
	public int countquerychangedetail(long id, String querybyuserinfo) throws Exception {
        String sql="select count(ucl.id) from userchangelog ucl where ucl.userchangeid="+id+" and ucl.fuserid in (SELECT ui.id from userinfo  ui where ui.nickname like '%"+querybyuserinfo+"%'  or ui.mobile like '%"+querybyuserinfo+"%' or ui.name like '%"+querybyuserinfo+"%' )"; 
        List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}
	@Override
	public Map<String, Object> querysome(long id) throws Exception {
		UserChange uc=(UserChange) databaseHelper.getObjectById(UserChange.class, id);
		String sql1="select c.company  from company c where c.id="+uc.getInfcompanyid()+"";
		String sql2="select a.name  from accountinfo a where a.id="+uc.getFaccountid()+"";
		String sql3="select a.name  from accountinfo a where a.id="+uc.getFdoitid()+"";
		List<Object> lst1 = databaseHelper.getResultListBySql(sql1.toString());
		List<Object> lst2 = databaseHelper.getResultListBySql(sql2.toString());
		List<Object> lst3 = databaseHelper.getResultListBySql(sql3.toString());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("incompany", lst1.get(0).toString());
		map.put("insalesman", lst2.get(0).toString());
		map.put("doitman", lst3.get(0).toString());
		return map;
	}
	
}
