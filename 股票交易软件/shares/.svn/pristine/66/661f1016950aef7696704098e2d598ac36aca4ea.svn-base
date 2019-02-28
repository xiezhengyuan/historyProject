package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.AccountInfo;

public interface ManagerService {
	public int countquerAll(String type)throws Exception;
	public List<Map<String, Object>> queryAll(Integer start,Integer limit,String type)throws Exception;
	public Map<String, Object> queryAll2(Integer start,Integer limit,String type,String keyword)throws Exception;
	public void modifyState(String id)throws Exception;
	public JsonArray queryPhoto(String id)throws Exception;
	public void modifyPhoto(String imgarr,String flag,String id)throws Exception;
	public Map<String, Object> queryRecharge(Integer role,AccountInfo acc)throws Exception;
	public JsonArray queryStatistics(String date,AccountInfo acc) throws Exception;

	public Map<String, Object> queryRech(Integer start,Integer limit,String gstarttime,String gendtime,String querybyuserinfo,AccountInfo acc)throws Exception;
}
