package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

public interface UserAppealService {

	public int countsystemappeal()throws Exception;
	public List<Map<String,Object>> systemappeal(int start, int limit)throws Exception;
	
	public int countuserappeal(String username,String moblie,String appealtype,String neworold)throws Exception;
	public List<Map<String,Object>> queryuserappeal(String username,String moblie,String appealtype,String neworold,int start,int limit)throws Exception;
	
	public void addappeal(String content)throws Exception;
	public void deleteappeal(String id)throws Exception;
	public void deleteuserappeal(String id,String type)throws Exception;
	public void sendcontent(String id,String content)throws Exception;
	
	public Map<String,Object> deleteappeal(String id,String type,String newcontent)throws Exception;
	public Map<String,Object> appealtetail(String appealid)throws Exception;
}
