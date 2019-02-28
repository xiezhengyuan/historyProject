package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hxy.isw.service.ModelConfigService;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;

@Repository
public class ModelConfigServiceImpl implements ModelConfigService {

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public JsonArray queryuser(String name,int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		String hql = "select u from UserInfo u where u.state = 0";
		hql = conditionuser(hql, name);
		hql += " order by u.time desc";
		List<Object> lst = databaseHelper.getResultListByHql(hql,start,limit);
		
		JsonArray arr = JsonUtil.castLst2Arr4SingleTime(lst);
		return arr;
	}
	
	

	@Override
	public int countuser(String name) throws Exception {
		// TODO Auto-generated method stub
		String hql = "select count(u.id) from UserInfo u where u.state = 0";
		hql = conditionuser(hql, name);
		List lst = databaseHelper.getResultListByHql(hql);
		return Integer.parseInt(lst.get(0).toString());
	}

	private String conditionuser(String hql,String name){
		if(name!=null&&name.length()>0)
		hql += " and u.name like '%"+name+"%'";
		
		return hql;
	}
	
	@Override
	public JsonArray queryemp(int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		String hql = "select e from Employee e where e.state = 0";
		
		List<Object> lst = databaseHelper.getResultListByHql(hql,start,limit);
		
		JsonArray arr = JsonUtil.castLst2Arr4SingleTime(lst);
		return arr;
	}

	@Override
	public int countqueryemp() throws Exception {
		// TODO Auto-generated method stub
		String hql = "select count(e.id) from Employee e where e.state = 0";
		List lst = databaseHelper.getResultListByHql(hql);
		return Integer.parseInt(lst.get(0).toString());
	}

	

}
