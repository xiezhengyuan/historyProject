package com.hxy.isw.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.BusinessInfo;
import com.hxy.isw.entity.UserInfo;

/**
* @author lcc
* @date 2017年5月4日 下午3:21:40
* @describe
*/
public interface EmployeeService {
	public List<Map<String,Object>> queryemployee(BusinessInfo bi,String name,String mobile,String department,int start,int limit) throws Exception;
	public int countemployee(BusinessInfo bi,String name,String mobile,String department) throws Exception;
	public List<Map<String,Object>> querylower(BusinessInfo bi,String name,String mobile,String proxylevel,String userinfoid,int start,int limit) throws Exception;
	public int countlower(BusinessInfo bi,String name,String mobile,String proxylevel,String userinfoid) throws Exception;
	public JsonArray querydepartment(BusinessInfo bi) throws Exception;
	public void addemployee(UserInfo userinfo)  throws Exception;
	public void modifyemployee(UserInfo userinfo)  throws Exception;
	public String castFile2Lst(InputStream is,BusinessInfo bi)   throws Exception;
}
