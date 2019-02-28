package com.hxy.isw.service;

import java.util.Map;

import com.google.gson.JsonArray;

public interface PostingsService {
	public Map<String, Object> searchPostings(Integer start, String keyword,Integer limit)throws Exception;
	public void disable(String id)throws Exception;
	public Map<String, Object> searchTopic(Integer start, String keyword,Integer limit,String fpostingstyleid)throws Exception;
	public void deleteTopic(String id)throws Exception;
	public JsonArray queryTopicbyid(String id)throws Exception;
	public Map<String, Object> queryTopicbyid2(String id)throws Exception;
	public void modifypostings(String imgarr,String flag,String postingscontent,String id)throws Exception;
	public Map<String, Object> querybanneruser(Integer start, String keyword,Integer limit)throws Exception;
	public void relievebanner(String id)throws Exception;
	public void addpostings(String imgarr,String flag,String postingscontent,String id,String praise,String rewrad,String share,String title,String fpostingstyleid)throws Exception;
	public Map<String, Object> querycomment(Integer start, String keyword,Integer limit,String id)throws Exception;
	public void deleteComment(String id)throws Exception;
	public Map<String, Object> querycommentbyid(String id)throws Exception;
	public void modifypostingcoment(String postingscontent,String id)throws Exception;
	public JsonArray queryPostingStyle();
	public Map<String, Object> queryStyle(Integer start,Integer limit)throws Exception;
	public void deleteStyle(String id)throws Exception;
	public Map<String, Object> addStyle(String name)throws Exception;
	public Map<String, Object> queryStyleById(String id)throws Exception;
	public void modifypostingStyle(String name,String id)throws Exception;
}
