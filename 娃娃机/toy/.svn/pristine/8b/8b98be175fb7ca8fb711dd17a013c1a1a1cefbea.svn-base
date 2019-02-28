package com.hxy.isw.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AppServiceToys {
	public Map<String, Object> queryGiftbox(String userid, Integer state, Integer start, Integer limit);
	public String queryexpressage(String giftboxid) throws Exception;
	public Map<String, Object> searchToystype(Integer start, Integer limit);
	public Map<String, Object> searchToysdetail(String mechinesid);
	public Map<String, Object> searchApplydelivery(String giftboxid,String userid) throws Exception;
	public String play(String userid,String machineid,String operation,String direction,String visualangle) throws Exception;
	public String uploadvideo(HttpServletRequest request) throws Exception;
}
