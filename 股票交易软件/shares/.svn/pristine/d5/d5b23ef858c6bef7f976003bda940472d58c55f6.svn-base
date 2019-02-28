package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.AccountInfo;

public interface StatisticService {
	public JsonArray querywalletstatistic(AccountInfo emp,String starttime,String type,String companyid) throws Exception;
	public JsonArray queryuserstatistic(AccountInfo emp,String starttime,String type,String companyid) throws Exception;
	public JsonArray queryuser(AccountInfo emp) throws Exception;
	public JsonArray queryincome(AccountInfo emp) throws Exception;
	public JsonArray querycash(AccountInfo emp) throws Exception;
	
	
	public List<Map<String, Object>> querycompanywalletstatistic(AccountInfo acc,String starttime,String type)throws Exception;
	public List<Map<String, Object>> queryusersstatistic(AccountInfo acc,String starttime,String type)throws Exception;
     
	public Map<String, Object>  queryuserandmoney(AccountInfo acc)throws Exception;
	public Map<String, Object>  querymyadduser(AccountInfo acc)throws Exception;
   
}
