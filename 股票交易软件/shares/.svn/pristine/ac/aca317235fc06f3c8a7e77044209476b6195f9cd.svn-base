package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.GoldsDetail;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.ManageUserService;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;


@Repository
public class ManageUserServiceImpl implements ManageUserService{


	@Autowired
	DatabaseHelper databaseHelper;
	
	@Override
	public int ptcountuserinfo(AccountInfo acc,String querybyuserinfo,String company, String jl,String ywy,String selecttype) throws Exception {
		StringBuffer sql=new StringBuffer("select  count(u.id) from userinfo u  ");
		sql.append("join accountinfo a1 on u.faccountid = a1.id  join accountinfo a2 on a1.fparentid =a2.id  join  company c on a1.fcompanyid = c.id where ");
		if(selecttype.equals("1")){
			sql.append(" u.state=0 ");
		}else{
			sql.append(" u.state=-1 ");
		}
		sql.append(ptsql(acc, company, jl, ywy));
		sql.append("and  (u.name like '%"+querybyuserinfo+"%' or u.mobile like '%"+querybyuserinfo+"%') order by u.createtime desc");
		System.out.println(sql.toString());
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst==null?0:Integer.parseInt(lst.get(0).toString());
	}
	
	
	//平台查用户的hql
	private StringBuffer ptsql(AccountInfo acc,String company, String jl,String ywy){
		int role = acc.getRole();
		StringBuffer sql = new StringBuffer();
		if (role == 0 || role == 1) {

		} else if (role == 2 || role == 3) {
			company = acc.getFcompanyid().toString();
		} else if (role == 4) {
			jl = acc.getId().toString();
		} else {
			ywy = acc.getId().toString();
		}
		if(company.equals("0")){
			sql.append("");
			return sql;
		}
		if(jl.equals("0")){
			sql.append("and  c.id="+company+" ");
			return sql;
		}
		if(ywy.equals("0")){
			sql.append("and  a2.id="+jl+" ");
			return sql;
		}
		else{
			sql.append("and  a1.id="+ywy+" ");
			return sql;
		}
		
	}


	@Override
	public List<Map<String, Object>> ptqueryuserinfo(AccountInfo acc,String querybyuserinfo, String company, String jl,String ywy,String selecttype, int start,
			int limit) throws Exception {
		StringBuffer sql=new StringBuffer("select u.id,u.name 'a',u.nickname, u.mobile,u.totalrecharge,u.golds,u.virtualcapital,u.createtime,u.state ,a1.name 'b',a2.name 'c',c.company  from userinfo u ");
		sql.append("join accountinfo a1 on u.faccountid = a1.id  join accountinfo a2 on a1.fparentid =a2.id  join  company c on a1.fcompanyid = c.id where ");
	
		if(selecttype.equals("1")){
			sql.append(" u.state=0 ");
		}else{
			sql.append(" u.state=-1 ");
		}
		sql.append(ptsql(acc, company, jl, ywy));
		sql.append("and  (u.name like '%"+querybyuserinfo+"%' or u.mobile like '%"+querybyuserinfo+"%') order by u.createtime desc");
		System.out.println(sql.toString());
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object[] object : lst) {  
			String s = "";
			int i=1;
			for (Object object2 : object) {
				s+= (i+":"+(object2==null?"null":object2.toString())+"\t");
				i++;
			}
			System.out.println(s);
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
			map.put("salesman",object[9]==null?"":object[9].toString());
			map.put("gengerl",object[10]==null?"":object[10].toString());
			map.put("company",object[11]==null?"":object[11].toString());

			lstMap.add(map);
		}
		
		return lstMap;
	}


	public int ptoldusernum() throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where u.state=0 or u.state=-1");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString()) ;
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int pttodayusernum() throws Exception {
		StringBuffer sql=new StringBuffer("select count(u.id) from userinfo u where (u.state=0 or u.state=-1) and TO_DAYS(u.createtime)=TO_DAYS(NOW())");
		List<Object> list= databaseHelper.getResultListBySql(sql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}


	@Override
	public Map<String, Object> queryuserbyid(long userid) throws Exception {
		StringBuffer hql=new StringBuffer("select u from UserInfo u where u.id="+userid+"") ;
		//推广业务员
		StringBuffer sql1=new StringBuffer("select a.name from accountinfo a where a.id in (select u.faccountid from userinfo u where u.id="+userid+")");
		//推广经理
		StringBuffer sql2=new StringBuffer("select a.name from  accountinfo a where a.id in(select a1.fparentid from accountinfo a1 where a1.id in (select u.faccountid from userinfo u where u.id="+userid+"))");
		//推广公司
		StringBuffer sql3=new StringBuffer("select c.company from  company c where c.id in(select a.fcompanyid from accountinfo a where a.id in (select u.faccountid from userinfo u where u.id="+userid+"))");
		
		Map<String, Object> userinfo =new HashMap<String, Object>();
		
		
		UserInfo user= (UserInfo) databaseHelper.getResultListByHql(hql.toString()).get(0);
		
		List<Object> lst1 = databaseHelper.getResultListBySql(sql1.toString());
		List<Object> lst2 = databaseHelper.getResultListBySql(sql2.toString());
		List<Object> lst3 = databaseHelper.getResultListBySql(sql3.toString());
		
		userinfo.put("wxid", user.getWxid()==null?"":user.getWxid().toString());
		userinfo.put("nickname",user.getNickname()==null?"":user.getNickname().toString() );
		userinfo.put("mobile", user.getMobile()==null?"":user.getMobile().toString());
		userinfo.put("golds", user.getGolds().toString());
		userinfo.put("totalrecharge", user.getTotalrecharge().toString());
		userinfo.put("virtualcapital", user.getVirtualcapital().toString());
		userinfo.put("email", user.getEmail()==null?"":user.getEmail().toString());
		userinfo.put("address", user.getAddress()==null?"":user.getAddress().toString());
		userinfo.put("bank", user.getBank()==null?"":user.getBank().toString());
		userinfo.put("bankaccount", user.getBankaccount()==null?"":user.getBankaccount().toString());
		userinfo.put("cardno", user.getCardno()==null?"":user.getCardno().toString());
		userinfo.put("Paparno",user.getPaparno()==null?"": user.getPaparno().toString());
		userinfo.put("createtime", user.getCreatetime().toString());
		userinfo.put("empname",lst1.get(0).toString());
	    userinfo.put("bossname",lst2.get(0).toString());
	    userinfo.put("companyname",lst3.get(0).toString());
		return userinfo;
	}


	@Override
	public int queryinviteusersnum(long userid, String nameorphone) throws Exception {
		StringBuffer sql=new StringBuffer("SELECT COUNT(u.id) from userinfo u where u.finvateid="+userid+" and (u.name like'%"+nameorphone+"%' or u.mobile like '%"+nameorphone+"%' )");
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return Integer.parseInt(lst.get(0).toString());
		
	}


	@Override
	public List<Map<String, Object>> queryinviteusers(long userid, String nameorphone, int start, int limit)
			throws Exception {
		StringBuffer hql=new StringBuffer("select u from UserInfo u where u.finvateid="+userid+" and (u.name like'%"+nameorphone+"%' or u.mobile like '%"+nameorphone+"%' ) and (u.state =0 or u.state=-1)");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object object : lst) {
			UserInfo u= (UserInfo) object;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", u.getId().toString());
			map.put("name", u.getName().toString());
			map.put("mobile", u.getMobile().toString());
			map.put("totalrecharge", u.getTotalrecharge().toString());
			map.put("golds", u.getGolds().toString());
			map.put("virtualcapital", u.getVirtualcapital().toString());
			map.put("createtime", u.getCreatetime().toString());
			lstMap.add(map);
		}
		return lstMap;
	}


	@Override
	public int querygolddetilnumbyuserid(long userid, String starttime, String endtime) throws Exception {
		StringBuffer hql=new StringBuffer("select count(g.id) from GoldsDetail g where g.fuserinfoid="+userid+"");
		hql.append(appHql(starttime, endtime));
		hql.append("order by g.createtime desc ");
		List<Object> list= databaseHelper.getResultListByHql(hql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}


	@Override
	public List<Map<String, Object>> querygolddetil(long userid, String starttime, String endtime, int start, int limit)
			throws Exception {
		StringBuffer hql=new StringBuffer("select g from GoldsDetail g where  g.fuserinfoid="+userid+"");
		hql.append(appHql(starttime, endtime));
		hql.append("order by g.createtime desc ");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object o : lst) {
			GoldsDetail g=(GoldsDetail) o;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", g.getId().toString());
			map.put("type", getgoldstype(g.getType()).toString());
			map.put("golds", g.getGolds()>0?"+"+g.getGolds():g.getGolds().toString());
			map.put("createtime", g.getCreatetime().toString());
			lstMap.add(map);
		}
		return lstMap;
	}
	
	private String getgoldstype(int type){ 
		if(type==-7){
		  return "金币兑换虚拟资金 ";
		}else if(type==-6){
			return "管理员扣除";
		}
		else if(type==-5){
			return "打赏别人";
		}
		else if(type==-4){
			return "观摩计划";
		}
		else if(type==-3){
			return "抢购计划";
		}
		else if(type==-2){
			return "提现";
		}
		else if(type==-1){
			return "订阅";
		}else if(type==1){
			return "充值 ";
		}else if(type==2){
			return "被人打赏";
		}else if(type==3){
			return "邀请好友";
		}else if(type==4){
			return "管理员赠送";
		}else{
			return "其他";
		}

	}
	
	private String getcapitaltype(int type){
		if(type==-5){
		  return "跟单金额";
		}else if(type==-4){
			return "委托冻结资金";
		}else if(type==-3){
			return "美股购买";
		}else if(type==-2){
			return "外汇购买";
		}else if(type==-1){
			return "股票购买 ";
		}else if(type==1){
			return "股票卖出";
		}else if(type==2){
			return "外汇卖出";
		}else if(type==3){
			return "美股卖出";
		}else if(type==4){
			return "冻结返还";
		}else{
			return "其他";
		}
	}
    private StringBuffer appHql(String starttime,String endtime){
    	StringBuffer apphql = new StringBuffer("");
    	if(starttime==""&&endtime==""){
    		
    	}else if(starttime==""&&endtime!=""){
    		apphql.append("and g.createtime<='"+endtime+"'");
    		
    	}else if(starttime!=""&&endtime==""){
    		apphql.append("and g.createtime>='"+starttime+"'");
    	}else{
    		apphql.append("and g.createtime>='"+starttime+"' and g.createtime<='"+endtime+"' ");
    	}
    	return apphql;
    }


	@Override
	public int querymoneydetilnumbyuserid(long userid, String starttime, String endtime) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("select count(g.id) from CapitalDetail g where g.fuserinfoid="+userid+"");
		hql.append(appHql(starttime, endtime));
		hql.append("order by g.createtime desc ");
		List<Object> list= databaseHelper.getResultListByHql(hql.toString());
		return list==null?0:Integer.parseInt(list.get(0).toString());
	}


	@Override
	public List<Map<String, Object>> querymoneydetil(long userid, String starttime, String endtime, int start,
			int limit) throws Exception {
		StringBuffer hql=new StringBuffer("select g from CapitalDetail g where  g.fuserinfoid="+userid+"");
		hql.append(appHql(starttime, endtime));
		hql.append("order by g.createtime desc ");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object o : lst) {
			CapitalDetail g=(CapitalDetail) o;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", g.getId().toString());
			map.put("type", getcapitaltype(g.getType()));
			map.put("capital", g.getCapital()>0?"+"+g.getCapital():g.getCapital().toString());
			map.put("createtime", g.getCreatetime().toString());
			lstMap.add(map);
		}
		return lstMap;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delectuser(long userid) throws Exception {
		StringBuffer sql=new StringBuffer("update userinfo u set u.state=-2 where u.id= "+userid+"");
		int folg=databaseHelper.executeNativeSql(sql.toString());
		if(folg==0)throw new Exception("未改变");
		System.out.println("folg:"+folg);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updatagolds(long userid ,double newgolds , String isjia) throws Exception {
		StringBuffer sql=new StringBuffer("");
		GoldsDetail g=new GoldsDetail();
		if(isjia.equals("yes")){
			sql.append("update userinfo u set u.golds=u.golds+"+newgolds+" where u.id= "+userid+"");
			g.setCreatetime(new Date());
			g.setFuserinfoid(userid);
			g.setGolds(newgolds);
			g.setState(0);
			g.setType(4);
		}else{
			StringBuffer sqlgolds=new StringBuffer("select u.golds from userinfo u where u.id="+userid+"");
			List<Object> list= databaseHelper.getResultListBySql(sqlgolds.toString());
			if(Double.parseDouble(list.get(0).toString())<newgolds){
				sql.append("update userinfo u set u.golds=0 where u.id= "+userid+"");
				g.setCreatetime(new Date());
				g.setFuserinfoid(userid);
				g.setGolds(-Double.parseDouble(list.get(0).toString()));
				g.setState(0);
				g.setType(-6);
			}else{
				sql.append("update userinfo u set u.golds=u.golds-"+newgolds+" where u.id= "+userid+"");
				g.setCreatetime(new Date());
				g.setFuserinfoid(userid);
				g.setGolds(-newgolds);
				g.setState(0);
				g.setType(-6);
			}
		}
		databaseHelper.persistObject(g);
		int folg=databaseHelper.executeNativeSql(sql.toString());
		
		if(folg==0)throw new Exception("未改变");
		
		
	}
	
}