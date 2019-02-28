package com.hxy.isw.service;

import java.util.Map;

import com.google.gson.JsonArray;

import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.ToysType;

import sun.security.util.PropertyExpander.ExpandException;

public interface DeviceService {
	Map<String, Object> findMachineList(Employee em, String machineno, String ftoysid, Integer start, Integer limit)throws Exception;
	void bindToys(Employee em, MachineInfo machineInfo) throws Exception;
	void addmachine(Employee em,String addmachineid) throws Exception;
	Map<String, Object> findAlltoys(Employee em)throws Exception;
	Map<String, Object> findAlltoysType(Employee em)throws Exception;
	Map<String, Object> toysInfoList(Employee em, int start, int limit,String name,String ftoystypeid)throws Exception;
	Map<String, Object> toysTypeList(Employee em, int start, int limit, String name)throws Exception;
	void addToys(ToysInfo toysInfo,String imgarr,Employee em);
	void addToysType(Employee em, ToysType tt) throws Exception;
	public Map<String, Object> searchDeliveryapply(Integer start, String state,Integer limit,Employee em)throws Exception;
	public void sendtouser(String id,String content)throws Exception;
	public void deleteapp(String id)throws Exception;
	public void addexpressageinfo(String id,String expressageno, String expressageid)throws Exception;
	public String queryexpressagedetail(String id)throws Exception;
	public JsonArray queryexpressage()throws Exception;
	String outportinmoneylog(Employee em,String starttime,String endtime)throws Exception;
	public Map<String, Object> querymiaobi(Employee em,String starttime,String endtime,Integer start,Integer limit);
	public JsonArray queryStatistics(String date,Employee em) throws Exception;
}
