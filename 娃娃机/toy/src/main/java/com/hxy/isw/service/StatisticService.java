package com.hxy.isw.service;

public interface StatisticService {
	public String queryuserstatistic(String starttime,String endtime) throws Exception;
	public String querytoystatistic(String starttime,String endtime) throws Exception;
	public String querypointstatistic(String starttime,String endtime) throws Exception;
	public String queryfruitstatistic(String starttime,String endtime) throws Exception;
}
