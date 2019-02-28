package com.hxy.isw.service;

import java.util.Map;

import com.hxy.isw.entity.UserInfo;

public interface TradingService {
		public Map<String, Object> queryusertrading(Integer start,String keyword,Integer limit)throws Exception;
		public Map<String, Object> queryposition(Integer start,String keyword,Integer limit,String tradingId,String type)throws Exception;
		public Map<String, Object> queryorders(Integer start,String keyword,Integer limit,String tradingId,String type)throws Exception;
		public Map<String, Object> queryforex(Integer start,String keyword,Integer limit,String tradingId)throws Exception;
		public Map<String, Object> queryforex1(Integer start,String keyword,Integer limit,String tradingId)throws Exception;
		public Map<String, Object> queryhistorytrading(Integer start,String keyword,Integer limit,String tradingId,String type)throws Exception;
		public Map<String, Object> queryhistoryforex(Integer start,String keyword,Integer limit,String tradingId)throws Exception;
		public void modifytrading(UserInfo userInfo)throws Exception;
}
