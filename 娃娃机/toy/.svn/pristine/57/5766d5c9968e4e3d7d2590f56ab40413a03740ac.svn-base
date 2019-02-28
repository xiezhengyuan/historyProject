package com.hxy.isw.service;

import java.util.Map;

public interface AppServiceUser {
	public String getcode(String userid,String mobile,String type)throws Exception;
	public String login(String mobile, String password) throws Exception;
	public String wxlogin(String openid, String nickname,String headimg) throws Exception;
	public String registe(String mobile, String password,String code)throws Exception;
	public String forgotpassword(String mobile, String newpassword,String code)throws Exception;
	public String uploaduserphoto(String userid, String filename) throws Exception;
	public String userinfo(String userid) throws Exception;
	public String	modifyuserinfo(String userid,String nickname,String sex,String birthday,String selfinfo)throws Exception;
	public String getshippingaddress(String userid) throws Exception;
	public String addnewaddress(String userid,String consigneename,String consigneemobile,String province,String city,String area,String address)throws Exception;
	public String setdefault(String userid,String addressid)throws Exception;
	public String deladdress(String userid,String addressid)throws Exception;
	public String consumption(String userid,Integer start,Integer limit)throws Exception;
	public String userlevellist(String userid,Integer start,Integer limit)throws Exception;
	public String myfans(String userid,Integer start,Integer limit)throws Exception;
	public String myfollow(String userid,Integer start,Integer limit)throws Exception;
	public String usersussesstoys(String userid,Integer start,Integer limit)throws Exception;
	public String myuseremember(String userid,Integer start,Integer limit)throws Exception;
	public String nearsuccess(String machineid,int start,int limit)throws Exception;
	public String applyproxy(String userid,String name,String mobile,String province,String city,String area)throws Exception;
	public String sign(String userid) throws Exception;
	public String appeal(String userid,String content,String photos) throws Exception;
	public String leavemessage(String userid,String content,String title) throws Exception;
	public String banner() throws Exception;
	public void dofollow(String myid,String userid) throws Exception;
	public void removefollow(String myid,String userid) throws Exception;
	public Map<String, Object> userstate(String myid,String userid) throws Exception;
	public Map<String, Object> my(String userid) throws Exception;
	public Map<String, Object> usershare(String userid) throws Exception;
	public 	String allege() throws Exception;
	public void userallege(String userid,String allegeid,String substance,String videoinfoid)throws Exception;
	public void invitema(String userid,String shareno)throws Exception;
	public Map<String, Object> testedition(String editionno,String system)throws Exception;
}
