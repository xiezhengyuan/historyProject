package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.AccountInfo;

public interface MoneyService {
	public List<Map<String, Object>> querycash(AccountInfo emp,String name,String mobile,String state,Integer start,Integer limit)throws Exception;
	public int countcash(AccountInfo emp,String name,String mobile,String state)throws Exception;
	public List<Map<String, Object>> querycharge(AccountInfo emp,String name,String mobile,String starttime,String endtime,Integer start,Integer limit)throws Exception;
	public int countcharge(AccountInfo emp,String name,String mobile,String starttime,String endtime)throws Exception;
	public void updatestate(String id,String state) throws Exception;
	public String outportcashlog(AccountInfo emp,String name,String mobile,String state)throws Exception;
	public JsonArray querycashstatistic(AccountInfo emp,String starttime) throws Exception;
}
