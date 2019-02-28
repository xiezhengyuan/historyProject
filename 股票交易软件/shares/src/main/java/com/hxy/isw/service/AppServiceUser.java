package com.hxy.isw.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hxy.isw.entity.PayInfoByWX;

/**
* @author lcc
* @date 2017年7月20日 下午3:13:13
* @describe
*/
public interface AppServiceUser {
	public String getcode(String type,String mobile) throws Exception;
	public String login(String mobile, String password) throws Exception;
	public String registe(String mobile, String password,String code,String faccountid,String finvateid,String openid,String nickname,String headimgurl,HttpServletRequest request)throws Exception;
	public String forgotpassword(String mobile, String newpassword,String code)throws Exception;
	public String sevendayprofit(String userid)throws Exception;
	public String myprofit(String userid)throws Exception;
	public String recharge(String userid,String amount,int paymentway,String ip)throws Exception;
	public String rechargeOfPC(String userid,String amount,String paymentway)throws Exception;
	public boolean wxnotify(PayInfoByWX p) throws Exception;
	public void uploaduserphoto(String userid, String filename) throws Exception;
	public String getbank() throws Exception;
	public boolean rechargeOfPCNotify(Map<String, String> map) throws Exception;
}
