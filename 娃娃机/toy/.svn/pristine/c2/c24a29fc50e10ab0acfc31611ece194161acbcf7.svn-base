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

import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.ProxyApply;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.AgentapplyService;
import com.hxy.isw.util.DatabaseHelper;

@Service
public class AgentapplyServiceImpl implements AgentapplyService {
    @Autowired
	DatabaseHelper databaseHelper;
    
	@Override
	public int countproxyapply(Employee bi, String name, String mobile) throws Exception {
		StringBuffer hql = new StringBuffer("select count(p.id) from ProxyApply p where  p.state = 0");
		hql = conditionuserinfo(hql, bi,name,mobile);
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> queryproxyapply(Employee bi, String name, String mobile, Integer start,
			Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer("select p from ProxyApply p where  p.state = 0");
		hql = conditionuserinfo(hql,bi, name,mobile);
		hql = hql.append(" order by p.createtime desc");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object obj : lst) {
			ProxyApply p = (ProxyApply) obj;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", p.getId());
			map.put("name", p.getName());
			map.put("mobile", p.getMobile());
			map.put("address",p.getProvince()+p.getCity()+p.getArea());
			map.put("createtime", p.getCreatetime().toString());
		
			lstMap.add(map);
		}
		
		return lstMap;
	}
	private StringBuffer conditionuserinfo(StringBuffer hql,Employee bi,String name,String mobile){
			
		if(name!=null&&name.length()>0)
			hql = hql.append(" and p.name like '%").append(name).append("%'");
		
		if(mobile!=null&&mobile.length()>0)
			hql = hql.append(" and p.mobile like '%").append(mobile).append("%'");
		
		return hql;
	}

	@Override
	public int countagentuser(Employee bi, String name, String mobile) throws Exception {
		StringBuffer hql = new StringBuffer("select count(p.id) from ProxyApply p where  p.state = 1");
		hql = conditionuserinfo(hql, bi,name,mobile);
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	

	@Override
	public List<Map<String, Object>> queryagentuser(Employee bi, String name, String mobile, Integer start,
			Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer("select p from ProxyApply p where  p.state = 1");
		hql = conditionuserinfo(hql,bi, name,mobile);
		hql = hql.append(" order by p.createtime desc");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object obj : lst) {
			ProxyApply p = (ProxyApply) obj;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", p.getId());
			map.put("name", p.getName());
			map.put("mobile", p.getMobile());
			map.put("address",p.getProvince()+p.getCity()+p.getArea());
			map.put("createtime", p.getCreatetime());
		
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	public int countdisagreeuser(Employee bi, String name, String mobile) throws Exception {
		StringBuffer hql = new StringBuffer("select count(p.id) from ProxyApply p where  p.state = -1");
		hql = conditionuserinfo(hql, bi,name,mobile);
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> querydisagreeuser(Employee bi, String name, String mobile, Integer start,
			Integer limit) throws Exception {
		StringBuffer hql = new StringBuffer("select p from ProxyApply p where  p.state = -1");
		hql = conditionuserinfo(hql,bi, name,mobile);
		hql = hql.append(" order by p.createtime desc");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object obj : lst) {
			ProxyApply p = (ProxyApply) obj;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", p.getId());
			map.put("name", p.getName());
			map.put("mobile", p.getMobile());
			map.put("address",p.getProvince()+p.getCity()+p.getArea());
			map.put("createtime", p.getCreatetime());
		
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void disagreeapply(Long fuserinfoid) throws Exception {
		StringBuffer hql = new StringBuffer("select p from ProxyApply p where  p.state = 0 and p.id="+fuserinfoid+" ");
		
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString());
		ProxyApply pro=(ProxyApply) lst.get(0);
		pro.setState(-1);
		pro.setCreatetime(new Date());
		databaseHelper.updateObject(pro);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void agreeapply(String id,String username,String password) throws Exception {
		
		String sql=" select count(id) from employee where username like '"+username+"' ";
		int count=databaseHelper.getSqlCount(sql);
		if(count>0){
			throw new Exception("该登陆账号已存在");
		}
		
		
		ProxyApply pa=(ProxyApply) databaseHelper.getObjectById(ProxyApply.class,Long.parseLong(id));
		
		Employee emp=new Employee();
		emp.setUsername(username);
		emp.setPassword(password);
		emp.setName(pa.getName());
		emp.setMobile(pa.getMobile());
		emp.setState(0);
		emp.setRole(1);
		Date date=new Date();
		emp.setCreatetime(date);
		pa.setCreatetime(date);
		pa.setState(1);
		databaseHelper.persistObject(emp);
		databaseHelper.updateObject(pa);
		
	}

	@Override
	public Map<String, String> queryapplyuserxq(Long fuserinfoid) throws Exception {
		
		Map<String, String> map=new HashMap<String,String>(); 
		//基本信息
		StringBuffer hql1=new StringBuffer("select u from UserInfo u where u.id="+fuserinfoid+"" );  
		List<Object> userlst = databaseHelper.getResultListByHql(hql1.toString());
		UserInfo u=(UserInfo) userlst.get(0);
		
		//获得礼物
		StringBuffer hql2=new StringBuffer("select count(g.id) from Giftbox g where g.fuserinfoid = "+fuserinfoid+"");
		List<Object> giftlst = databaseHelper.getResultListByHql(hql2.toString());
		String giftnum= giftlst==null? "0":giftlst.get(0)+"";
		
		//总充值
		String money="0";//预留
		
		
		
		map.put("username", u.getUsername());
		map.put("nickname", u.getNickname());
		map.put("sex", u.getSex());
		map.put("mobile", u.getMobile());
		map.put("createtime", u.getCreatetime().toString());
		map.put("level", u.getLevel()+"");
		map.put("balance", u.getBalance()+"");
		map.put("giftnum", giftnum);
		map.put("money", money);
		
		return map;
	}

	@Override
	public Map<String, Object> updateagent(String id) throws Exception {
		String sql=" select * from employee where  ";
		sql+="  createtime =( select createtime from proxyapply where id="+id+")  ";
		List<Object[]> lst= databaseHelper.getResultListBySql(sql);
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("id", lst.get(0)[0]);
		map.put("username", lst.get(0)[1]);
		map.put("password", lst.get(0)[2]);
		map.put("name", lst.get(0)[3]);
		map.put("mobile", lst.get(0)[4]);

		String sql1=" select count(id) from machineinfo where empid ="+lst.get(0)[0]+"  and state !=-1 ";
		map.put("mechonumber", databaseHelper.getSqlCount(sql1));	
		

				
		String sql2=" select IFNULL(SUM(golds),0) from consumption where type=-1 and fmachineid in  ";
			   sql2+=" ( select id from machineinfo where empid="+lst.get(0)[0]+")  ";
	    map.put("miaobinumber", databaseHelper.getSqlCount(sql1));
	    map.put("op", "success");
			
		return map;
	}

	@Override
	public void changeinfo(String id, String username, String password) throws Exception {
		Employee emp=(Employee) databaseHelper.getObjectById(Employee.class, Long.parseLong(id));
		emp.setUsername(username);
		emp.setPassword(password);
		databaseHelper.updateObject(emp);
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Map<String, Object> deleteagent(String id) throws Exception {
		//代理表
		String sql=" update proxyapply set state= -1 where id="+id+" ";
	 	
		databaseHelper.executeNativeSql(sql);
		//后台管理账号
		String hql= " select e from Employee e where e.createtime =( select p.createtime from ProxyApply p where p.id="+id+" ) ";
		Employee e=(Employee) databaseHelper.getResultListByHql(hql).get(0); 
		e.setState(-1);
		databaseHelper.updateObject(e);
		
		 
		//娃娃机
		String sql2=" update machineinfo set state = -1 where empid="+e.getId()+"   ";
		int machinenumber=databaseHelper.executeNativeSql(sql2);
		
		//玩具
		String sql3="  update toysinfo set state = -1 where empid= "+e.getId()+" ";
		int toynumber=databaseHelper.executeNativeSql(sql3);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("machinenumber", machinenumber);
		map.put("toynumber", toynumber);
		return map;
	}
}
