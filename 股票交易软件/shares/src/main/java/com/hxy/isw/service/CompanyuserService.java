package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

public interface CompanyuserService {
 
	public int comcountuserinfo(long companyid,String selecttype,String querybyuserinfo, String querybyaccountinfo)throws Exception;
	public int countgeneralqueryuserinfo(long generalid,String selecttype,String querybyuserinfo, String querybyaccountinfo)throws Exception;
	public int countsalesmanqueryuserinfo(long salesmanid,String selecttype,String querybyuserinfo)throws Exception;
	public int todayusernum(long companyid)throws Exception;
	public int oldusernum(long companyid)throws Exception;
	public int generaloldusernum(long generalid)throws Exception;
	public int generaltodayusernum(long generalid)throws Exception;
	public int salesmantodayusernum(long generalid)throws Exception;
	public int salesmanoldusernum(long generalid)throws Exception;
	public List<Map<String,Object>> comqueryuserinfo(long companyid,String selecttype, String querybyuserinfo, String querybyaccountinfo,int start, int limit)throws Exception;
	public List<Map<String,Object>> generalqueryuserinfo(long generalid,String selecttype, String querybyuserinfo, String querybyaccountinfo,int start, int limit)throws Exception;
	public List<Map<String,Object>> salesmanqueryuserinfo(long salesmanid,String selecttype, String querybyuserinfo,int start, int limit)throws Exception;
}
