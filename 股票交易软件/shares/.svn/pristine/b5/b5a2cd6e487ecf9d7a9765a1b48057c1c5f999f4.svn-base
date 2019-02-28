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
import com.hxy.isw.entity.SalesmanChange;
import com.hxy.isw.entity.SalesmanChangeLog;
import com.hxy.isw.service.SalesmanChangeService;
import com.hxy.isw.util.DatabaseHelper;

@Service
public class SalesmanChangeServiceImp implements SalesmanChangeService{
	
	@Autowired
	DatabaseHelper databasehelper;

	@Override
	public int countsalesman(String companyid, String jlid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(a1.id) from accountinfo a1 join company  c  on a1.fcompanyid = c.id  ");
		sql.append("join accountinfo a2 on a1.fparentid =a2.id  where a1.role= 5 ");
		sql.append(addsql(companyid, jlid));
		System.out.println(sql.toString());
		Integer count = databasehelper.getSqlCount(sql.toString());
		return count;
	}

	@Override
	public List<Map<String, Object>> lstmap(String companyid, String jlid, int start, int limit) throws Exception {
		StringBuffer sql=new StringBuffer("select a1.id  salesmanid ,a1.name salesmanname, a1.mobile, a2.id jlid, a2.name jlname,c.id companyid ,c.company from accountinfo a1 join company  c  on a1.fcompanyid = c.id  ");
		sql.append("join accountinfo a2 on a1.fparentid =a2.id  where a1.role= 5 ");
		sql.append(addsql(companyid, jlid));
		List<Object[]> lst=databasehelper.getResultListBySql(sql.toString(), start, limit);
		List<Map<String, Object>> lstmap =new ArrayList<Map<String,Object>>();
		for (Object[] o : lst) {
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("salesmanid", o[0].toString());
			map.put("salesmanname", o[1].toString());
			map.put("salesmanmobile", o[2].toString());
			map.put("jlid", o[3].toString());
			map.put("jlname", o[4].toString());
			map.put("companyid", o[5].toString());
			map.put("company", o[6].toString());
			lstmap.add(map);
		}
		
		return lstmap;
	}

	public StringBuffer addsql(String companyid, String jlid){
		StringBuffer sql=new StringBuffer("");
		if(companyid.equals("0")){
			sql.append("");
			return sql;
		}
		if(jlid.equals("0")){
			sql.append("and c.id= "+companyid+" ");
			return sql;
		}
		else{
			sql.append("and a2.id= "+jlid+" ");
			return sql;
		}	
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void confirmchange(long doitid, String salesmans, String injlid, String incompanyid) throws Exception {
		Date date=new Date();
		SalesmanChange sc=new SalesmanChange();
		sc.setFaccountid(Long.parseLong(injlid));
		sc.setInfcompanyid(Long.parseLong(incompanyid));
		sc.setCreatetime(date);
		sc.setFdoitid(doitid);sc.setState(0);
		databasehelper.persistObject(sc);
		String salesmanids="";
		String []salesmanarr=salesmans.split(",");
		for (int i = 0; i < salesmanarr.length; i++) {
			if(i==salesmanarr.length-1){
				salesmanids+=salesmanarr[i].split("-")[0];
			}else{
				salesmanids+=salesmanarr[i].split("-")[0]+",";
			}
			SalesmanChangeLog scl=new SalesmanChangeLog();
			scl.setSalesmanchangeid(sc.getId());
			scl.setFsalesmanid(Long.parseLong(salesmanarr[i].split("-")[0]));
			scl.setOutfaccountid(Long.parseLong(salesmanarr[i].split("-")[1]));
			scl.setOutfcompanyid(Long.parseLong(salesmanarr[i].split("-")[2]));
			scl.setInfaccountid(Long.parseLong(injlid));
			scl.setInfcompanyid(Long.parseLong(incompanyid));
			scl.setFdoitid(doitid);
			scl.setCreatetime(date);
			scl.setState(0);
			databasehelper.persistObject(scl);
		}
		
		String sql = "UPDATE accountinfo set fparentid = "+injlid+" ,fcompanyid = "+incompanyid+" where id in ("+salesmanids+") ";
		databasehelper.executeNativeSql(sql);
	}

	@Override
	public int countchangerem(String starttime, String endtime, AccountInfo acc) throws Exception {
		StringBuffer sql=new StringBuffer("select count(s.id) from salesmanchange s join accountinfo a on s.fdoitid =a.id where ");
		sql.append(appHql(starttime, endtime));
		if(acc.getRole()==0||acc.getRole()==1){
			sql.append(" (a.role=1 or a.role=0)  ");
		}else{
			sql.append(" a.fcompanyid = (select fcompanyid from accountinfo where id= "+acc.getId()+" ) ");
		}
		
		System.out.println(sql.toString());
		int count=databasehelper.getSqlCount(sql.toString());
		return count;
	}

	@Override
	public List<Map<String, Object>> querychangerem(String starttime, String endtime, AccountInfo acc, int start,
			int limit) throws Exception {
		StringBuffer sql=new StringBuffer("select s.* from salesmanchange s join accountinfo a on s.fdoitid =a.id where");
		sql.append(appHql(starttime, endtime));
		if(acc.getRole()==0||acc.getRole()==1){
			sql.append(" (a.role=1 or a.role=0)  ");
		}else{
			sql.append(" a.fcompanyid = (select fcompanyid from accountinfo where id= "+acc.getId()+" ) ");
		}
		List<Object[]> lst=databasehelper.getResultListBySql(sql.toString(), start, limit);
		List<Map<String, Object>> lstmap =new ArrayList<Map<String,Object>>();
		for (Object[] o : lst) {
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("id", o[0].toString());
			String sql1= "select count(id) from salesmanchangelog where salesmanchangeid ="+o[0].toString()+" ";
			map.put("number", databasehelper.getSqlCount(sql1));
			String sql2="select company from company where id="+o[1].toString()+" ";
			map.put("incompany", databasehelper.getResultListBySql(sql2).get(0).toString());
			String sql3="select name from accountinfo where id="+o[2].toString()+" ";
			map.put("inaccount", databasehelper.getResultListBySql(sql3).get(0).toString());
			String sql4="select name from accountinfo where id="+o[3].toString()+" ";
			map.put("doitman", databasehelper.getResultListBySql(sql4).get(0).toString());
			map.put("createtime", o[4].toString());
			lstmap.add(map);
			
		}
		
		
		return lstmap;
	}
	
	
	private StringBuffer appHql(String starttime,String endtime){
    	StringBuffer apphql = new StringBuffer("");
    	if(starttime==""&&endtime==""){
    		
    	}else if(starttime==""&&endtime!=""){ 
    		apphql.append(" s.createtime<='"+endtime+"' and ");
    		
    	}else if(starttime!=""&&endtime==""){
    		apphql.append(" s.createtime>='"+starttime+"' and  ");
    	}else{
    		apphql.append(" s.createtime>='"+starttime+"' and s.createtime<='"+endtime+"' and ");
    	}
    	return apphql;
    }

	@Override
	public Map<String, Object> querytop(String id) throws Exception {
		Map<String, Object> map =new HashMap<String, Object>();
		SalesmanChange s=(SalesmanChange) databasehelper.getObjectById(SalesmanChange.class, Long.parseLong(id));
		String sql2="select company from company where id="+s.getInfcompanyid()+" ";
		map.put("incompany", databasehelper.getResultListBySql(sql2).get(0).toString());
		String sql3="select name from accountinfo where id="+s.getFaccountid()+" ";
		map.put("inaccount", databasehelper.getResultListBySql(sql3).get(0).toString());
		String sql4="select name from accountinfo where id="+s.getFdoitid()+" ";
		map.put("doitman", databasehelper.getResultListBySql(sql4).get(0).toString());
		return map;
	}

	@Override
	public int countchangedetli(String querybysalesmaninfo, String id) throws Exception {
		StringBuffer sql=new StringBuffer("select count(s.id) from  ");
		sql.append("salesmanchangelog s join accountinfo a1 on  s.fsalesmanid = a1.id  ");
		sql.append("where s.salesmanchangeid ="+id+"  and (a1.`name` like '%"+querybysalesmaninfo+"%' or a1.mobile like '%"+querybysalesmaninfo+"%') ");
	    int count=databasehelper.getSqlCount(sql.toString());
		return count;
	}

	@Override
	public List<Map<String, Object>> querychangedetli(String querybysalesmaninfo, String id, int start, int limit)
			throws Exception {
		StringBuffer sql =new StringBuffer("select s.id,a1.`name` as 'salesmanname' , a1.mobile , a2.`name` as 'outfaccountname' , c.company from  ");
        sql.append("salesmanchangelog s join accountinfo a1 on  s.fsalesmanid = a1.id  ");
        sql.append("join accountinfo a2 on s.outfaccountid =a2.id  ");
        sql.append("join  company  c on s.outfcompanyid = c.id  ");
        sql.append("where s.salesmanchangeid ="+id+"  and (a1.`name` like '%"+querybysalesmaninfo+"%' or a1.mobile like '%"+querybysalesmaninfo+"%') ");
		List<Object[]> lst=databasehelper.getResultListBySql(sql.toString(), start, limit);
		List<Map<String, Object>> lstmap=new ArrayList<Map<String,Object>>();
		for (Object[] o : lst) {
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("id", o[0].toString());
			map.put("name", o[1].toString());
			map.put("moblie", o[2].toString());
			map.put("outjl", o[3].toString());
			map.put("outcompany", o[4].toString());
			lstmap.add(map);
		}
		
		return lstmap;
	}

}
