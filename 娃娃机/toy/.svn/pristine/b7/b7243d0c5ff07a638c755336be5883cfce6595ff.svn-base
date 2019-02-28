package com.hxy.isw.service;

import com.hxy.isw.entity.PayInfoByWx;
import com.hxy.isw.entity.PayinfoByAli;

/**
* @author lcc
* @date 2017年6月30日 上午10:58:20
* @describe
*/
public interface AppServicePay {

	public String gears(String userid) throws Exception;
	public String recharge(String userid, String gearsid,String paymentway,String ip) throws Exception;
	
	public boolean notifyurl(PayinfoByAli pali) throws Exception;
	public boolean wxnotify(PayInfoByWx p) throws Exception;
	
}
