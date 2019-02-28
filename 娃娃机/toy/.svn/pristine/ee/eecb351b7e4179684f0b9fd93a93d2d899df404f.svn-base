package com.hxy.isw.service;

import java.util.Map;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.ExpressageInfo;


public interface DeliveryapplyService {
	public Map<String, Object> searchDeliveryapply(String empid,String keyword,String toyname,int start,int limit)throws Exception;
	public JsonArray queryexpressage()throws Exception;
	public void addexpressageinfo(String id,String expressageno, String expressagecompany)throws Exception;
	public void sendtouser(String id,String content)throws Exception;
	public void deleteapp(String id,String type)throws Exception;
	Map<String, Object> queryexpressageinfo(String empid,String keyword,String toyname,int start,int limit);
	public String queryexpressagedetail(String id)throws Exception;
	
	
}
