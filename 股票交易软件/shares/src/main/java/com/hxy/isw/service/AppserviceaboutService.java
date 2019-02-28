package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

public interface AppserviceaboutService {
   public int countmyfans(long userid)throws Exception; 
   public int countmyfollow(long userid)throws Exception; 
   public int countcommentme(long userid)throws Exception; 
   public int countmycomment(long userid)throws Exception; 
   public int countmynews(long userid)throws Exception; 
   public int countmyprivateletter(long userid)throws Exception; 
   public int countmyprivateletterdetil(long userid,long fnotifyid)throws Exception; 
   public List<Map<String,Object>> myfansinfo(long userid ,String nowuserid,int start, int limit)throws Exception; 
   public List<Map<String,Object>> myfollowinfo(long userid ,String nowuserid,int start, int limit)throws Exception; 
   public List<Map<String,Object>> commentmeinfo(long userid ,int start, int limit)throws Exception; 
   public List<Map<String,Object>> mycommentinfo(long userid ,int start, int limit)throws Exception; 
   public List<Map<String,Object>> myprivateletterinfo(long userid ,int start, int limit)throws Exception; 
   public List<Map<String,Object>> mynewsinfo(long userid ,int start, int limit)throws Exception; 
   public List<Map<String,Object>> myprivateletterdetilinfo(long userid ,long fnotifyid,int start, int limit)throws Exception; 
   public List<Map<String,Object>> sharetuijian(String usreid)throws Exception; 
   public void deletemycomment(long id)throws Exception; 
   public void deletemymessage(long fnotifyid,long userid)throws Exception; 
   public void deletemysystemmessage(long fnotifyid)throws Exception; 
}
