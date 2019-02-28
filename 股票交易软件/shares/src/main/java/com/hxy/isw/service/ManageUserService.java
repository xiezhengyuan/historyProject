package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.AccountInfo;


public interface ManageUserService {
  
   public int ptcountuserinfo(AccountInfo acc,String querybyuserinfo, String company, String jl,String ywy ,String selecttype)throws Exception;
   public List<Map<String,Object>> ptqueryuserinfo(AccountInfo acc, String querybyuserinfo, String company, String jl,String ywy ,String selecttype,int start, int limit)throws Exception;
   public int ptoldusernum()throws Exception;
   public int pttodayusernum()throws Exception;
   public Map<String,Object> queryuserbyid(long userid )throws Exception;
   public int queryinviteusersnum(long userid,String nameorphone )throws Exception;
   public int querygolddetilnumbyuserid(long userid,String starttime,String endtime )throws Exception;
   public int querymoneydetilnumbyuserid(long userid,String starttime,String endtime )throws Exception;
   public List<Map<String,Object>> queryinviteusers( long userid,String nameorphone,int start, int limit)throws Exception;
   public List<Map<String,Object>> querygolddetil(long userid, String starttime,String endtime,int start, int limit)throws Exception;
   public List<Map<String,Object>> querymoneydetil(long userid, String starttime,String endtime,int start, int limit)throws Exception;
   public void delectuser(long userid)throws Exception;
   public void updatagolds(long userid,double newgolds,String isjia)throws Exception;
}
