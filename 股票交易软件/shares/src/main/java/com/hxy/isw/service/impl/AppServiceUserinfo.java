package com.hxy.isw.service.impl;

import java.util.List;
import java.util.Map;


public interface AppServiceUserinfo {
	public Map<String, String> querymyinfo (long userid)throws Exception;
	public Map<String, String> towithdraw (long userid)throws Exception;
	public Map<String, String> toexchange(long userid)throws Exception;
	public Map<String, String> myagent(long userid)throws Exception;
	public double querymygolds (long userid)throws Exception;
	public double querymaxtotalgolds (long userid)throws Exception;
	public double totalingolds (long userid,String type,String time,String starttime,String endtime)throws Exception;
	public double totaloutgolds (long userid,String type,String time,String starttime,String endtime)throws Exception;
	
	 public int countmygoldsdetial(long userid)throws Exception; 
	 public int countmygoldsdetialanddetial(long userid,String type,String time,String starttime,String endtime,String inorout)throws Exception; 
	 public int countexchangerecord(long userid)throws Exception; 
	 public int countsystemmessage(long userid)throws Exception; 
	 public int countmyusers(long userid)throws Exception; 
	 public List<Map<String,Object>> mygoldsdetial(long userid ,int start, int limit)throws Exception; 
	 public List<Map<String,Object>> mygoldsdetialanddetial(long userid,String type,String time,String starttime,String endtime,int start, int limit,String inorout)throws Exception; 
	 public List<Map<String,Object>> exchangerecord(long userid,int start, int limit)throws Exception; 
	 public List<Map<String,Object>> systemmessage(long userid,int start, int limit)throws Exception; 
	 public List<Map<String,Object>> myusers(long userid,int start, int limit)throws Exception; 
     public void withdraw(long userid,double withdrawnumber)throws Exception;
     public void exchange(long userid,double exchangegolds)throws Exception;
     public void updatamyinfo(long userid,String updatatext,String type)throws Exception;
     public void updatamypassword(long userid,String password,String newpassword1,String newpassword2)throws Exception;
     public void applyhero(long userid,	String name,String mobile,String email,String bank,String cardno,String paparno,String tag)throws Exception;
     public void pcupdatemyinfo(long userid,String nickname,String name,String mobile,String email,String intro)throws Exception;
     public void updatemybank(long userid,String bankaccount,String bank,String cardno,String paparno )throws Exception;		
 			
}
