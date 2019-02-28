package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.Employee;

/**
 * 
 * @author peng
 * 
 *
 */
public interface AgentapplyService {
	public int countproxyapply(Employee bi,String name,String mobile)throws Exception;
	public int countagentuser(Employee bi,String name,String mobile)throws Exception;
	public int countdisagreeuser(Employee bi,String name,String mobile)throws Exception;
	public void disagreeapply(Long fuserinfoid  )throws Exception;
	public void agreeapply(String id,String username,String password  )throws Exception;
	public void changeinfo(String id,String username,String password  )throws Exception;
	public Map<String, Object> deleteagent(String id)throws Exception;
	public Map<String, String> queryapplyuserxq(Long fuserinfoid  )throws Exception;
	public Map<String, Object> updateagent(String id)throws Exception;
	public List<Map<String, Object>>queryproxyapply(Employee bi,String name,String mobile,Integer start,Integer limit)throws Exception;
	public List<Map<String, Object>>queryagentuser(Employee bi,String name,String mobile,Integer start,Integer limit)throws Exception;
	public List<Map<String, Object>>querydisagreeuser(Employee bi,String name,String mobile,Integer start,Integer limit)throws Exception;
}
