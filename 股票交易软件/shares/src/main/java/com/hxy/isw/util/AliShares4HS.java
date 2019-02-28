package com.hxy.isw.util;

import com.alibaba.cloudapi.sdk.core.BaseApiClient;
import com.alibaba.cloudapi.sdk.core.BaseApiClientBuilder;
import com.alibaba.cloudapi.sdk.core.annotation.NotThreadSafe;
import com.alibaba.cloudapi.sdk.core.annotation.ThreadSafe;
import com.alibaba.cloudapi.sdk.core.enums.Method;
import com.alibaba.cloudapi.sdk.core.enums.ParamPosition;
import com.alibaba.cloudapi.sdk.core.enums.Scheme;
import com.alibaba.cloudapi.sdk.core.model.ApiRequest;
import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.cloudapi.sdk.core.model.BuilderParams;

/**
* @author lcc
* @date 2017年7月27日 下午5:58:10
* @describe 阿里云沪深股API
*/

@ThreadSafe
public class AliShares4HS extends BaseApiClient{

	 public final static String GROUP_HOST = "ali-stock.showapi.com";
	
	private AliShares4HS(BuilderParams builderParams) {
		super(builderParams);
		// TODO Auto-generated constructor stub
	}

	 @NotThreadSafe
	    public static class Builder extends BaseApiClientBuilder<AliShares4HS.Builder, AliShares4HS>{

	        @Override
	        protected AliShares4HS build(BuilderParams params) {
	            return new AliShares4HS(params);
	        }
	    }

	    public static Builder newBuilder(){
	        return new AliShares4HS.Builder();
	    }

	    public static AliShares4HS getInstance(){
	        return getApiClassInstance(AliShares4HS.class);
	    }

	    //大盘历史查询
	    public ApiResponse indexDayHis(String code, String month) {
	        String _apiPath = "/indexDayHis";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, false);
	        _apiRequest.addParam("month", month, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //股票行情
	    public ApiResponse realStockinfo(String code, String need_k_pic, String needIndex) {
	        String _apiPath = "/real-stockinfo";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
	        _apiRequest.addParam("need_k_pic", need_k_pic, ParamPosition.QUERY, false);
	        _apiRequest.addParam("needIndex", needIndex, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //大盘股指行情_批量
	    public ApiResponse stockIndex(String stocks) {
	        String _apiPath = "/stockIndex";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("stocks", stocks, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //股票行情_批量
	    public ApiResponse batchRealStockinfo(String stocks, String needIndex) {
	        String _apiPath = "/batch-real-stockinfo";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("stocks", stocks, ParamPosition.QUERY, true);
	        _apiRequest.addParam("needIndex", needIndex, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //沪深及港股历史行情
	    public ApiResponse szShStockHistory(String begin, String end, String code) {
	        String _apiPath = "/sz-sh-stock-history";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("begin", begin, ParamPosition.QUERY, true);
	        _apiRequest.addParam("end", end, ParamPosition.QUERY, true);
	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //股票板块列表
	    public ApiResponse stockBlockList() {
	        String _apiPath = "/stock-block-list";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);


	        return syncInvoke(_apiRequest);
	    }
	    
	    //股票实时分时线数据
	    public ApiResponse timeline(String code, String day) {
	        String _apiPath = "/timeline";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
	        _apiRequest.addParam("day", day, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //股票K线数据
	    public ApiResponse realtimeK(String code, String time, String beginDay, String type) {
	        String _apiPath = "/realtime-k";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
	        _apiRequest.addParam("time", time, ParamPosition.QUERY, false);
	        _apiRequest.addParam("beginDay", beginDay, ParamPosition.QUERY, false);
	        _apiRequest.addParam("type", type, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //大盘股指列表查询
	    public ApiResponse stockindexsearch(String market, String page) {
	        String _apiPath = "/stockindexsearch";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("market", market, ParamPosition.QUERY, false);
	        _apiRequest.addParam("page", page, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    
	    //大盘股指K线数据
	    public ApiResponse indexKline(String code, String time, String beginDay) {
	        String _apiPath = "/index-kline";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
	        _apiRequest.addParam("time", time, ParamPosition.QUERY, false);
	        _apiRequest.addParam("beginDay", beginDay, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //股票列表查询
	    public ApiResponse stocklist(String market, String page) {
	        String _apiPath = "/stocklist";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("market", market, ParamPosition.QUERY, false);
	        _apiRequest.addParam("page", page, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    
	    //大盘股指分时线
	    public ApiResponse indexTimeline(String code, String day) {
	        String _apiPath = "/index-timeline";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
	        _apiRequest.addParam("day", day, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //沪深股票最新50条逐笔交易
	    public ApiResponse everytrade(String code) {
	        String _apiPath = "/everytrade";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //根据名称或编码查询股票信息
	    public ApiResponse nameToStockinfo(String code, String name, String pinyin) {
	        String _apiPath = "/name-to-stockinfo";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, false);
	        _apiRequest.addParam("name", name, ParamPosition.QUERY, false);
	        _apiRequest.addParam("pinyin", pinyin, ParamPosition.QUERY, false);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //当日除权停复牌上市股票
	    public ApiResponse stopStartDivide(String date) {
	        String _apiPath = "/stop-start-divide";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("date", date, ParamPosition.QUERY, true);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //查询沪深板块中的股票列表
	    public ApiResponse stockInBlock(String typeId, String page) {
	        String _apiPath = "/stock-in-block";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("typeId", typeId, ParamPosition.QUERY, true);
	        _apiRequest.addParam("page", page, ParamPosition.QUERY, true);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //港股板块列表
	    public ApiResponse hkBlockList() {
	        String _apiPath = "/hk-block-list";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);


	        return syncInvoke(_apiRequest);
	    }
	    
	    //查询港股板块中的股票列表
	    public ApiResponse hkInBlock(String typeId, String page) {
	        String _apiPath = "/hk-in-block";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("typeId", typeId, ParamPosition.QUERY, true);
	        _apiRequest.addParam("page", page, ParamPosition.QUERY, true);

	        return syncInvoke(_apiRequest);
	    }
	    
	    //沪深股票内外盘数据
	    public ApiResponse inOutData(String code) {
	        String _apiPath = "/in-out-data";

	        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

	        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);

	        return syncInvoke(_apiRequest);
	    }
	
}
