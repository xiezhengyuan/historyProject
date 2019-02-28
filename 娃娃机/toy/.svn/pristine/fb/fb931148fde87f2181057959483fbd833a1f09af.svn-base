package com.hxy.isw.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.BusinessInfo;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.UserInfo;

/**
* @author lcc
* @date 2017年5月4日 下午3:30:00
* @describe
*/

public interface UserService{
	
	public List<Map<String, Object>>queryuseinfo(Employee bi,String name,String mobile,Integer start,Integer limit)throws Exception;
	public int countuserinfo(Employee bi,String name,String mobile)throws Exception;
	public int queryaddtoday(Employee bi)throws Exception;
	public void modifyuserinfo(UserInfo userinfo)  throws Exception;
	public List<Map<String,Object>> querylower(Employee bi,String name,String mobile,String proxylevel,String userinfoid,int start,int limit) throws Exception;
	public int countlower(Employee bi,String name,String mobile,String proxylevel,String userinfoid) throws Exception;
	public void adduserinfo(UserInfo userinfo)  throws Exception;
	
	public List<Map<String,Object>> queryrecord(Employee bi,String name,String userinfoid,Integer start,Integer limit);
	public int countrecord(Employee bi,String name,String userinfoid) throws Exception;
	
	
}