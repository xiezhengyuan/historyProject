package com.hxy.isw.util;

import com.alibaba.cloudapi.sdk.core.annotation.ThreadSafe;
import com.alibaba.cloudapi.sdk.core.model.ApiResponse;

/**
* @author lcc
* @date 2017年7月27日 下午5:58:10
* @describe 阿里云沪深股接口工具
*/

@ThreadSafe
public class AliShares4HSUtil{

	 private AliShares4HS syncClient = null;

	    public AliShares4HSUtil() {
	    	if(syncClient==null)
	    		this.syncClient = AliShares4HS.newBuilder()
	                .appKey("24559874")
	                .appSecret("712932b558de6877c36fe1e8617a4032")
	                .build();
	    }

	    
	    //股票列表查询
	    public ApiResponse stocklist(String market,String page) {
	        ApiResponse response = syncClient.stocklist(market, page);
	        return response;
	    }
	    
	    //大盘股指行情_批量
	    public ApiResponse stockIndex() {
	    	 ApiResponse response = syncClient.stockIndex("sh000001,sz399001,sz399006");
	        return response;
	    }
	    
	    //股票行情批量
	    public ApiResponse batchRealStockinfo(String stocks) {
	        ApiResponse response = syncClient.batchRealStockinfo(stocks, "0");
	        return response;
	    }
	    
	    //股票行情
	    public ApiResponse realStockinfo(String code,String need_k_pic,String needIndex){
	    	 ApiResponse response = syncClient.realStockinfo(code, need_k_pic, needIndex);
		     return response;
	    }
	    
}
