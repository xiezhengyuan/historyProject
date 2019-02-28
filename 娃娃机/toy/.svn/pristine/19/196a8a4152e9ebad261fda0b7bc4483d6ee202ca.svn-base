package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.ToysType;

public interface ToyService {
	
	Map<String, Object> toysInfoList(Employee em, int start, int limit,String typeagentname,String type);

	Map<String, Object> toysTypeList(Employee em, int start, int limit, String name);

	void addToysType(Employee em, ToysType tt) throws Exception;

	void delToysType(Employee em, String id)throws Exception;

	Map<String, Object> findToysTypeById(Employee em, String id)throws Exception;
	
	Map<String, Object> findAlltoysType(Employee em)throws Exception;
	
	void addToys(ToysInfo toysInfo,String imgarr)throws Exception;
	 void updatebannerimgs(String imgs)throws Exception;
	 void deletetoys(String id)throws Exception;
	
	JsonArray findToysPhotos(Employee em,String toysid)throws Exception;
	
	List<Map<String, Object>> querybannerimg()throws Exception;

}
