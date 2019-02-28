package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface EditionService {

	public int countedition(String type)throws Exception;
	public List<Map<String, Object>> edition(String type,int  start, int limit)throws Exception;
    
	public void usethisedition(String id,String type)throws Exception;
	
	public void updateedition(String editionid,String type,String editionno,String description)throws Exception;
	
	public Map<String, Object> updateapp(HttpServletRequest request)throws Exception;
	public Map<String, Object> queryloadurl()throws Exception;
}
