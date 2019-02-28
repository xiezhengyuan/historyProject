package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.AccountInfo;

public interface SalesmanChangeService {


	public int countsalesman(String companyid,String jlid)throws Exception;
	
	public int countchangedetli(String querybysalesmaninfo, String id)throws Exception;
	
	public List<Map<String, Object>> lstmap(String companyid,String jlid, int start, int limit)throws Exception;
    
	public void confirmchange(long doitid,String salesmans,String injlid,String incompanyid)throws Exception;
	
	public int countchangerem(String starttime,String endtime,AccountInfo acc)throws Exception;
	
	public List<Map<String, Object>> querychangerem(String starttime,String endtime,AccountInfo acc, int start, int limit)throws Exception;

	public List<Map<String, Object>> querychangedetli(String querybysalesmaninfo, String id, int start, int limit)throws Exception;

	public Map<String, Object> querytop(String id)throws Exception;
}
