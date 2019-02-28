package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.MachineInfo;

public interface MachineService {

	/**
	 * @Description: 获取娃娃机列表
	 * @author: lixq
	 * @date 2017年6月29日 下午2:58:22
	 */
	Map<String, Object> findMachineList(Employee em, String agentname, String type, Integer start, Integer limit);

	void addmachine(String machineno,String empid) throws Exception;

	Map<String, Object> findAlltoys(String machineinfoid)throws Exception;

	void bindToys(Employee em, MachineInfo machineInfo) throws Exception;

	Map<String, Object> findToysPrice(Employee em, String id)throws Exception;
	
	List<Map<String, Object>> selectagent()throws Exception;
}
