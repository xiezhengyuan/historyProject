package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.PostingStyle;

public interface PostingService {
	public JsonArray querypostingstyle() throws Exception;
	public PostingStyle  addpostingstyle(String name)  throws Exception;
	
	public List<Map<String,Object>> queryposting(String name,String fpostingstyleid,String fuser, int start,int limit) throws Exception;
	public int countposting(String name,String fpostingstyleid,String fuser) throws Exception;
	public JsonArray querypostingphotosbyid(Long id)throws Exception;
	public List<Map<String,Object>> querypostingcomments(String postingid, int start,int limit) throws Exception;
	public int countpostingcomments(String postingid) throws Exception;
	public void passposting(String id)  throws Exception;
	public void nopassposting(String id)  throws Exception;
}
