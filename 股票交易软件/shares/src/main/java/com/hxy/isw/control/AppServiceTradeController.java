package com.hxy.isw.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AppServiceTradeService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/appServiceTrade")
public class AppServiceTradeController {

	@Autowired
	private AppServiceTradeService appServiceTradeService;

	/**
	 * 自选股列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/optionalSharesList", method = RequestMethod.POST)
	public void optionalSharesList(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));

			Map<String, Object> map = appServiceTradeService.optionalSharesList(userid,start,limit);

			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"异常\"}");
		}
	}

	/**
	 * 自选股详情
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(value = "/optionalSharesDetail", method = RequestMethod.POST)
	public void optionalSharesDetail(HttpServletRequest request, HttpServletResponse response) {
		try {
			// TODO 做过了
			
			String userid = request.getParameter("userid");
			String code = request.getParameter("code");// 股票代码

		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"异常\"}");
		}
	}*/

	/**
	 * 新增自选股
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addOptionalShares", method = RequestMethod.POST)
	public void addOptionalShares(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String code = request.getParameter("code");// 股票代码

			appServiceTradeService.addOptionalShares(userid, code);
			JsonUtil.success2page(response, "{\"msg\":\"success\",\"info\":\"添加成功\"}");

		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"异常\"}");
		}
	}

	/**
	 * 删除自选股
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delOptionalShares", method = RequestMethod.POST)
	public void delOptionalShares(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String code = request.getParameter("code");// 股票代码

			appServiceTradeService.delOptionalShares(userid, code);
			JsonUtil.success2page(response, "{\"msg\":\"success\",\"info\":\"删除成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"异常\"}");
		}
	}

	/**
	 * 股票买入（限价委托/市价委托）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/buyShares", method = RequestMethod.POST)
	public void buyShares(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String entrustType = request.getParameter("entrustType");// 1限价委托 2市价委托
			String entrustPrice = request.getParameter("entrustPrice");// 限价委托金额
			String count = request.getParameter("count");// 买入股数
			String sharesid = request.getParameter("sharesid");// 股票表ID
			String positions = request.getParameter("positions");// (1/4仓=>0.25)(1/3仓=>0.33)(半仓=>0.5)(全仓=>1)
			String type = request.getParameter("type");// 0股票1美股
			String isplan = request.getParameter("isplan");// 0非计划 1计划

			appServiceTradeService.buyShares(userid, entrustType, entrustPrice, count, sharesid, positions, type,isplan);
			JsonUtil.success2page(response, "{\"msg\":\"success\",\"info\":\"操作成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 股票卖出（限价委托/市价委托）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sellShares", method = RequestMethod.POST)
	public void sellShares(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String entrustType = request.getParameter("entrustType");// 1限价委托2市价委托
			String entrustPrice = request.getParameter("entrustPrice");// 限价委托金额
			String count = request.getParameter("count");// 卖出股数
			String sharesid = request.getParameter("sharesid");// 股票表ID
			String positions = request.getParameter("positions");// (1/4仓=>0.25)(1/3仓=>0.33)(半仓=>0.5)(全仓=>1)
			String type = request.getParameter("type");// 0股票1美股
			String isplan = request.getParameter("isplan");// 0非计划 1计划
			String warehouseid = request.getParameter("warehouseid");// 持仓id

			appServiceTradeService.sellShares(userid, entrustType, entrustPrice, count, sharesid, positions, type,isplan,warehouseid);
			JsonUtil.success2page(response, "{\"msg\":\"success\",\"info\":\"操作成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 外汇买入（限价委托/市价委托）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/buyForeign", method = RequestMethod.POST)
	public void buyForeign(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String entrustType = request.getParameter("entrustType");// 1限价委托 2市价委托
			String entrustPrice = request.getParameter("entrustPrice");// 限价委托金额
			String count = request.getParameter("count");// 买入股数
			String sharesid = request.getParameter("sharesid");// 股票表ID
			String positions = request.getParameter("positions");// (1/4仓=>0.25)(1/3仓=>0.33)(半仓=>0.5)(全仓=>1)
			String isplan = request.getParameter("isplan");// 0非计划 1计划
			String buytype = request.getParameter("buytype");// 1买涨 -1买跌

			appServiceTradeService.buyForeign(userid, entrustType, entrustPrice, count, sharesid, positions,isplan,buytype);
			JsonUtil.success2page(response, "{\"msg\":\"success\",\"info\":\"操作成功\"}");
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 外汇卖出（限价委托/市价委托）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sellForeign", method = RequestMethod.POST)
	public void sellForeign(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String entrustType = request.getParameter("entrustType");// 1限价委托2市价委托
			String entrustPrice = request.getParameter("entrustPrice");// 限价委托金额
			String count = request.getParameter("count");// 卖出股数
			String sharesid = request.getParameter("sharesid");// 股票表ID
			String positions = request.getParameter("positions");// (1/4仓=>0.25)(1/3仓=>0.33)(半仓=>0.5)(全仓=>1)
			String isplan = request.getParameter("isplan");// 0非计划 1计划
			String warehouseid = request.getParameter("warehouseid");// 持仓id
			appServiceTradeService.sellForeign(userid, entrustType, entrustPrice, count, sharesid, positions,isplan,warehouseid);
			JsonUtil.success2page(response, "{\"msg\":\"success\",\"info\":\"操作成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 行情列表
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(value = "/marketList", method = RequestMethod.POST)
	public void marketList(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			// TODO 做过了
			

		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"异常\"}");
		}
	}*/
}
