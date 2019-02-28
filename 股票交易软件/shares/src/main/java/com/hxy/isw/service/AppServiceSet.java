package com.hxy.isw.service;

import java.text.ParseException;
import java.util.Map;

import com.hxy.isw.entity.UserInfo;

public interface AppServiceSet {
	public void addDocumentaryMoney(String userId,String money,String documentaryId)throws Exception;
	public Map<String, Object> queryTradingDetail(String userId,String shareId,String type,Integer start,Integer limit)throws Exception;
	public Map<String, Object> queryYield(String month,String userId)throws Exception;
	public Map<String, Object> queryYieldthird(String userId) throws Exception;
	public Map<String, Object> queryYieldtwo(String day,String type,String userId)throws Exception;
	public void cutDocumentaryMoney(String userId,String money,String documentaryId)throws Exception;
	public void newaddDocumentaryMoney(String userId,String money,String ffollowuserinfoid)throws Exception;
	public void cancelDocumentaryMoney(String userId,String documentaryId,String exampleapplyId)throws Exception;
	public void cancelDocumentaryMoney1(String userId,String documentaryId,String exampleapplyId)throws Exception;
	public Map<String, Object> querytrading(String userId,String type,Integer start,Integer limit)throws Exception;
	public Map<String, Object> queryAmericantrading(String userId,String type,Integer start,Integer limit)throws Exception;
	public Map<String, Object> queryforex(String userId,String type,Integer start,Integer limit)throws Exception;
	public Map<String, Object> queryCoverMessage(String userId,String sharesId,String type)throws Exception;
	public void queryCover(String userId,String sharesId,String warehouseid)throws Exception;
	public void queryforexCover(String userId,String forexId)throws Exception;
	public Map<String, Object> queryRevokeMessage(String userId,String shareId,String type)throws Exception;
	public void revokeShare(String userId,String shareId)throws Exception;
	public void revokeSharebuying(String userId,String shareId)throws Exception;
	public void revokeForex(String userId,String foreignexId)throws Exception;
	public void revokeforexbuying(String userId,String foreignexId)throws Exception;
	public void addSharesCheck(String userid,String stoploss,String stopprofit,String shareid,String type,String usersharessettingid)throws Exception;
	public void modifySharesCheck(String userId,String stoploss,String stopprofit,String fforeignid,String type)throws Exception;
	public void addforexCheck(String userId,String stoploss,String stopprofit,String fforeignid)throws Exception;
	public void modifyforexCheck(String userId,String stoploss,String stopprofit,String fforeignid,String type)throws Exception;
	public void addshares(String userId,String fshareswarehouseid,String entrustprice,String entrustnums)throws Exception;
	public void addsharesbuying(String userId,String sharesId,String entrustprice,String entrustnums)throws Exception;
	public void addforex(String userId,String fforeignexchangeid,String entrustprice,String entrustnums)throws Exception;
	public void addforexbuying(String userId,String sharesId,String entrustprice,String entrustnums)throws Exception;
	public Map<String, Object> querySharesCheck(String userid,String type,String shareid)throws Exception;
	
	public void ModifySharesCheck(String userid,String type,String stoploss,String stopprofit,String shareid)throws Exception;
	
	public String revokeEntrust(String userid,String entrustid,String type) throws Exception;
}
