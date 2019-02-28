package com.hxy.isw.service;

import java.util.Map;

import org.hibernate.loader.collection.OneToManyJoinWalker;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.AccountInfo;

public interface NoticService {
	public Map<String, Object> queryhnotice(Integer start, String keyword,Integer limit,AccountInfo acc) throws Exception;
	public void addhnotice(String noticecontent,String target,String noticename,String array);
	public void addtimnotice(String noticecontent,String target,String noticename,String array,String sendtime) throws Exception;
	public Map<String, Object> querywaitnotic(Integer start, String keyword,Integer limit);
	public void deletewaitnotic(String id);
	public Map<String, Object> querywaitnoticbyid(String id);
	public void savewaitnotice(String id,String noticename,String noticecontent);
	public JsonArray querycompony();
	public Map<String, Object> queryNoticeById(String id)throws Exception;
}
