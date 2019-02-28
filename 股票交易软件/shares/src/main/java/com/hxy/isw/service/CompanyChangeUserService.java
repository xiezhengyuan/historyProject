package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.util.JsonUtil;

public interface CompanyChangeUserService {
     public List<Map<String,Object>> queryjl(long companyid)throws Exception;
     public List<Map<String,Object>> queryywy(long jlid)throws Exception;
     
     public int countqueryuser(long companyid,long salesid,long gengalid,String accname,String type)throws Exception;
     public List<Map<String, Object>> queryuser(long companyid,long salesid,long gengalid,String accname,String type,int start, int limit)throws Exception;
     
     public void confirmtochange(String users,long ywyid,long fdoitid,long incompanyid);

     public int countquerychange(long companyid,String starttime,String endtime )throws Exception;
     public int countquerychangejl(long jlid,String starttime,String endtime )throws Exception;
     public List<Map<String ,Object>> querychange(long companyid,String starttime,String endtime,int start, int limit)throws Exception;
     public List<Map<String ,Object>> querychangejl(long jlid,String starttime,String endtime,int start, int limit)throws Exception;
}
