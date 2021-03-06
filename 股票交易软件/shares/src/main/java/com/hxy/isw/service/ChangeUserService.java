package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

public interface ChangeUserService {
    public List<Map<String,Object>> querycompany(String companyname)throws Exception;
    public List<Map<String,Object>> queryjlbycompanyid(long companyid)throws Exception;
    public List<Map<String,Object>> queryywybyjlid(long jlid)throws Exception;
    public List<Map<String ,Object>> querychange(String starttime,String endtime,int start, int limit)throws Exception;
    public List<Map<String ,Object>> querychangedetail(long id,String querybyuserinfo,int start, int limit)throws Exception;
    public Map<String, Object> querysome(long id)throws Exception;
    
    public int countqueryuserbycompany(long companyid,long jlid,long ywyid)throws Exception;
    public int countquerychange(String starttime,String endtime )throws Exception;
    public int countquerychangedetail(long id,String querybyuserinfo )throws Exception;
    public List<Map<String,Object>> queryuserbycompany( long companyid,long jlid,long ywyid,int start, int limit)throws Exception;
    public void confirmtochange(String users,long ywyid,long fdoitid,long incompanyid)throws Exception;
}
