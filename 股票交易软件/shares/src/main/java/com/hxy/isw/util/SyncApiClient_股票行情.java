package com.hxy.isw.util;

/*
* Copyright 2017 Alibaba Group
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

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

@ThreadSafe
public final class SyncApiClient_股票行情 extends BaseApiClient {
    public final static String GROUP_HOST = "ali-stock.showapi.com";

    private SyncApiClient_股票行情(BuilderParams builderParams) {
        super(builderParams);
    }

    @NotThreadSafe
    public static class Builder extends BaseApiClientBuilder<SyncApiClient_股票行情.Builder, SyncApiClient_股票行情>{

        @Override
        protected SyncApiClient_股票行情 build(BuilderParams params) {
            return new SyncApiClient_股票行情(params);
        }
    }

    public static Builder newBuilder(){
        return new SyncApiClient_股票行情.Builder();
    }

    public static SyncApiClient_股票行情 getInstance(){
        return getApiClassInstance(SyncApiClient_股票行情.class);
    }

    public ApiResponse 大盘历史查询(String code, String month) {
        String _apiPath = "/indexDayHis";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, false);
        _apiRequest.addParam("month", month, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 股票行情(String code, String need_k_pic, String needIndex) {
        String _apiPath = "/real-stockinfo";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
        _apiRequest.addParam("need_k_pic", need_k_pic, ParamPosition.QUERY, false);
        _apiRequest.addParam("needIndex", needIndex, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 大盘股指行情_批量(String stocks) {
        String _apiPath = "/stockIndex";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("stocks", stocks, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 股票行情_批量(String stocks, String needIndex) {
        String _apiPath = "/batch-real-stockinfo";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("stocks", stocks, ParamPosition.QUERY, true);
        _apiRequest.addParam("needIndex", needIndex, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 沪深及港股历史行情(String begin, String end, String code) {
        String _apiPath = "/sz-sh-stock-history";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("begin", begin, ParamPosition.QUERY, true);
        _apiRequest.addParam("end", end, ParamPosition.QUERY, true);
        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 股票板块列表() {
        String _apiPath = "/stock-block-list";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);


        return syncInvoke(_apiRequest);
    }
    public ApiResponse 股票实时分时线数据(String code, String day) {
        String _apiPath = "/timeline";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
        _apiRequest.addParam("day", day, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 股票K线数据(String code, String time, String beginDay, String type) {
        String _apiPath = "/realtime-k";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
        _apiRequest.addParam("time", time, ParamPosition.QUERY, false);
        _apiRequest.addParam("beginDay", beginDay, ParamPosition.QUERY, false);
        _apiRequest.addParam("type", type, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 大盘股指列表查询(String market, String page) {
        String _apiPath = "/stockindexsearch";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("market", market, ParamPosition.QUERY, false);
        _apiRequest.addParam("page", page, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 大盘股指K线数据(String code, String time, String beginDay) {
        String _apiPath = "/index-kline";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
        _apiRequest.addParam("time", time, ParamPosition.QUERY, false);
        _apiRequest.addParam("beginDay", beginDay, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 股票列表查询(String market, String page) {
        String _apiPath = "/stocklist";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("market", market, ParamPosition.QUERY, false);
        _apiRequest.addParam("page", page, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 大盘股指分时线(String code, String day) {
        String _apiPath = "/index-timeline";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);
        _apiRequest.addParam("day", day, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 沪深股票最新50条逐笔交易(String code) {
        String _apiPath = "/everytrade";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 根据名称或编码查询股票信息(String code, String name, String pinyin) {
        String _apiPath = "/name-to-stockinfo";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, false);
        _apiRequest.addParam("name", name, ParamPosition.QUERY, false);
        _apiRequest.addParam("pinyin", pinyin, ParamPosition.QUERY, false);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 当日除权停复牌上市股票(String date) {
        String _apiPath = "/stop-start-divide";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("date", date, ParamPosition.QUERY, true);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 查询沪深板块中的股票列表(String typeId, String page) {
        String _apiPath = "/stock-in-block";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("typeId", typeId, ParamPosition.QUERY, true);
        _apiRequest.addParam("page", page, ParamPosition.QUERY, true);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 港股板块列表() {
        String _apiPath = "/hk-block-list";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);


        return syncInvoke(_apiRequest);
    }
    public ApiResponse 查询港股板块中的股票列表(String typeId, String page) {
        String _apiPath = "/hk-in-block";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("typeId", typeId, ParamPosition.QUERY, true);
        _apiRequest.addParam("page", page, ParamPosition.QUERY, true);

        return syncInvoke(_apiRequest);
    }
    public ApiResponse 沪深股票内外盘数据(String code) {
        String _apiPath = "/in-out-data";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.GET, GROUP_HOST, _apiPath);

        _apiRequest.addParam("code", code, ParamPosition.QUERY, true);

        return syncInvoke(_apiRequest);
    }
}