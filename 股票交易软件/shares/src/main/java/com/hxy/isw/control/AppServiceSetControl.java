package com.hxy.isw.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AppServiceSet;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/appServiceSet")
public class AppServiceSetControl {
	@Autowired
	AppServiceSet appServiceSet;
	
	//收益统计图
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		@RequestMapping(method = RequestMethod.POST, value = "/queryYield")
		public void queryYield(HttpServletRequest request, HttpServletResponse response){
			try {
				String userId=request.getParameter("userId");
				if (StringUtils.isEmpty(userId)) {
					throw new NullPointerException();
				}
				String month=request.getParameter("month");
				
				appServiceSet.queryYield(month,userId);
				JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
			
					
		}
		
		//收益统计图
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/queryYieldtwo")
				public void queryYieldtwo(HttpServletRequest request, HttpServletResponse response){
					try {
						//type:0:股票；1：美股；2：外汇；
						String type=request.getParameter("type");
						String userId=request.getParameter("userId");
						if (StringUtils.isEmpty(userId)) {
							throw new NullPointerException();
						}
						String day=request.getParameter("day");//day:1;day:7;全部：-1
						
						appServiceSet.queryYieldtwo(day, type, userId);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
							
				}
				
				//收益统计图
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/queryYieldthird")
				public void queryYieldthird(HttpServletRequest request, HttpServletResponse response){
					try {
						String userId=request.getParameter("userId");
						if (StringUtils.isEmpty(userId)) {
							throw new NullPointerException();
						}
					
						
						appServiceSet.queryYieldthird(userId);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
							
				}
	
	/**
	 * 增加跟单金额 
	 * @param request
	 * @param response
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/addDocumentaryMoney")
	public void addDocumentaryMoney(HttpServletRequest request, HttpServletResponse response){
		try {
			String userId=request.getParameter("userId");
			if (StringUtils.isEmpty(userId)) {
				throw new NullPointerException();
			}
			String money=request.getParameter("money");//跟单金额
			String documentaryId=request.getParameter("documentaryId");//牛人id
			appServiceSet.addDocumentaryMoney(userId, money, documentaryId);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
		}
		
				
	}
	
	/**
	 * 减少跟单金额 
	 * @param request
	 * @param response
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/cutDocumentaryMoney")
	public void cutDocumentaryMoney(HttpServletRequest request, HttpServletResponse response){
		try {
			String userId=request.getParameter("userId");
			if (StringUtils.isEmpty(userId)) {
				throw new NullPointerException();
			}
			String money=request.getParameter("money");//跟单金额
			String documentaryId=request.getParameter("documentaryId");//牛人id
			appServiceSet.cutDocumentaryMoney(userId, money, documentaryId);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
		}
		
				
	}
	
	/**
	 * 新增跟单金额 
	 * @param request
	 * @param response
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		@RequestMapping(method = RequestMethod.POST, value = "/newaddDocumentaryMoney")
		public void newaddDocumentaryMoney(HttpServletRequest request, HttpServletResponse response){
			try {
				String userId=request.getParameter("userId");
				if (StringUtils.isEmpty(userId)) {
					throw new NullPointerException();
				}
				String money=request.getParameter("money");//跟单金额
				String ffollowuserinfoid=request.getParameter("ffollowuserinfoid");//跟单牛人id
				appServiceSet.newaddDocumentaryMoney(userId, money, ffollowuserinfoid);
				JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			
					
		}
	 /**
	  * 取消跟单转为平仓
	  * @param request
	  * @param response
	  */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	 @RequestMapping(method = RequestMethod.POST, value = "/cancelDocumentaryMoney")
		public void cancelDocumentaryMoney(HttpServletRequest request, HttpServletResponse response){
		 try {
			 String userId=request.getParameter("userId");
				if (StringUtils.isEmpty(userId)) {
					throw new NullPointerException();
				}
			
				String documentaryId=request.getParameter("documentaryId");//跟单id，可传可不传
				String exampleapplyId=request.getParameter("exampleapplyId");//牛人id
				appServiceSet.cancelDocumentaryMoney(userId, documentaryId,exampleapplyId);
				JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
		}
	 }
	 
	 /**
	  * 取消跟单转为自有股票
	  * @param request
	  * @param response
	  */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	 @RequestMapping(method = RequestMethod.POST, value = "/cancelDocumentaryMoney1")
		public void cancelDocumentaryMoney1(HttpServletRequest request, HttpServletResponse response){
		 try {
			 String userId=request.getParameter("userId");
				if (StringUtils.isEmpty(userId)) {
					throw new NullPointerException();
				}
			
				String documentaryId=request.getParameter("documentaryId");//跟单id，可传可不传
				String exampleapplyId=request.getParameter("exampleapplyId");//牛人id
				appServiceSet.cancelDocumentaryMoney1(userId, documentaryId,exampleapplyId);
				JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
		}
	 }
	 
	 /**
	  * 查询股票（正在持仓、委托挂单、历史交易）
	  * @param request
	  * @param response
	  */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	 @RequestMapping(method = RequestMethod.POST, value = "/querytrading")
		public void querytrading(HttpServletRequest request, HttpServletResponse response){
		 try {
			 /*type=0:正在持仓;type=1:委托挂单;type=2:历史交易;*/
			 String type =request.getParameter("type");
			 String userId=request.getParameter("userId");
			 if (StringUtils.isEmpty(userId)) {
					throw new NullPointerException();
				}
			 int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			 int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				if (StringUtils.isEmpty(userId)) {
					throw new NullPointerException();
				}
			Map<String, Object> map = appServiceSet.querytrading(userId, type, start, limit);
			JsonUtil.mapToJson(map, response);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	 
	 }
	 
	/**
	 * 查询美股（正在持仓、委托挂单、历史交易）
	 * @param request
	 * @param response
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/queryAmericantrading")
			public void queryAmericantrading(HttpServletRequest request, HttpServletResponse response){
			 try {
				 /*type=0:正在持仓;type=1:委托挂单;type=2:历史交易;*/
				 String type =request.getParameter("type");
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				 int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
					if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				Map<String, Object> map = appServiceSet.queryAmericantrading(userId, type, start, limit);
				JsonUtil.mapToJson(map, response);	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
		 
		 }
		 
		/**
		 * 查询外汇（正在持仓、委托挂单、历史交易）
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/queryforex")
			public void queryforex(HttpServletRequest request, HttpServletResponse response){
			 try {
				 /*type=0:正在持仓;type=1:委托挂单;type=2:历史交易;*/
				 String type =request.getParameter("type");
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				 int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
					if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				Map<String, Object> map = appServiceSet.queryforex(userId, type, start, limit);
				JsonUtil.mapToJson(map, response);	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
		 
		 }
		 /**
		  * 查询平仓信息
		  * @param request
		  * @param response
		  */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/queryCoverMessage")
			public void queryCoverMessage(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String type=request.getParameter("type");//0:股票；1：外汇
				 String sharesId=request.getParameter("sharesId");//想要平仓的股票或者外汇的id
				 Map<String, Object> map =appServiceSet.queryCoverMessage(userId, sharesId, type);
				 JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
			 
		 }
		 
		 /**
		  * 股票，美股平仓
		  * @param request
		  * @param response
		  */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/queryCover")
			public void queryCover(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 String warehouseid=request.getParameter("warehouseid");
				 if (StringUtils.isEmpty(userId))throw new Exception("userId不能为空");
				 if (StringUtils.isEmpty(warehouseid))throw new Exception("warehouseid不能为空");
					
				 String sharesId=request.getParameter("sharesId");
				appServiceSet.queryCover(userId, sharesId,warehouseid);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 /**
		  * 外汇平仓
		  * @param request
		  * @param response
		  */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/queryforexCover")
			public void queryforexCover(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String forexId=request.getParameter("forexId");
				appServiceSet.queryforexCover(userId, forexId);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		/**
		 * 撤销股票委托卖出订单
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/revokeShare")
			public void revokeShare(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String shareId=request.getParameter("shareId");
				appServiceSet.revokeShare(userId, shareId);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**
		 * 撤销股票委托卖出订单详情
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/queryRevokeMessage")
			public void queryRevokeMessage(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String shareId=request.getParameter("shareId");//股票或者外汇的id
				 String type=request.getParameter("type");//0:股票；1：外汇
				Map<String, Object> map = appServiceSet.queryRevokeMessage(userId, shareId, type);
				 JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		/**
		 * 新增股票,美股委托卖出订单
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/addShare")
			public void addShare(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String fshareswarehouseid=request.getParameter("fshareswarehouseid");//股票，美股的id
				 String entrustprice=request.getParameter("entrustprice");//委托价格
				 String entrustnums=request.getParameter("entrustnums");//委托数量
				 appServiceSet.addshares(userId, fshareswarehouseid, entrustprice, entrustnums);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
	
	
		/**
		 * 新增股票,美股委托买入订单
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/addSharebuying")
			public void addSharebuying(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String sharesId=request.getParameter("sharesId");//shares表里的id
				 String entrustprice=request.getParameter("entrustprice");//委托价格
				 String entrustnums=request.getParameter("entrustnums");//委托数量
				appServiceSet.addsharesbuying(userId, sharesId, entrustprice, entrustnums);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**
		 * 新增外汇委托卖出订单
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/addforex")
			public void addforex(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String sharesId=request.getParameter("sharesId");//外汇id
				 String entrustprice=request.getParameter("entrustprice");
				 String entrustnums=request.getParameter("entrustnums");
				appServiceSet.addforex(userId, sharesId, entrustprice, entrustnums);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
			/**
			 * 新增外汇委托买入订单
			 * @param request
			 * @param response
			 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/addforexbuying")
			public void addforexbuying(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String sharesId=request.getParameter("sharesId");//外汇id
				 String entrustprice=request.getParameter("entrustprice");
				 String entrustnums=request.getParameter("entrustnums");
				appServiceSet.addforexbuying(userId, sharesId, entrustprice, entrustnums);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		
		/**
		 * 撤销股票委托买入订单
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/revokeSharebuying")
			public void revokeSharebuying(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String shareId=request.getParameter("shareId");
				appServiceSet.revokeSharebuying(userId, shareId);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**
		 * 撤销外汇委托卖出订单
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/revokeforex")
			public void revokeforex(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String foreignexId=request.getParameter("shareId");
				appServiceSet.revokeForex(userId, foreignexId);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**
		 * 撤销外汇委托买入订单
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/revokeforexbuying")
			public void revokeforexbuying(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String foreignexId=request.getParameter("shareId");
				appServiceSet.revokeforexbuying(userId, foreignexId);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**
		 * 新增股票,外汇止损止盈
		 * 如果没有，则是新增，有就是修改
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/addSharesCheck")
			public void addSharesCheck(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userid=request.getParameter("userid");
				 if (StringUtils.isEmpty(userid)) {
						throw new NullPointerException();
					}
				 String stoploss=request.getParameter("stoploss");//止损点
				 String stopprofit=request.getParameter("stopprofit");//止盈点
				 String shareid=request.getParameter("shareid");
				 String type=request.getParameter("type");
				 String usersharessettingid=request.getParameter("usersharessettingid");
				appServiceSet.addSharesCheck(userid, stoploss, stopprofit, shareid,type,usersharessettingid);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		 /**
		  * 
		  *查询股票外汇的止盈止损信息，没有则返回买入价和卖出
		  * @param request
		  * @param response
		  */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/querySharesCheck")
			public void querySharesCheck(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userid=request.getParameter("userid");
				 if (StringUtils.isEmpty(userid)) {
						throw new NullPointerException();
					}
				String type=request.getParameter("type");
				 String shareid=request.getParameter("shareid");
				Map<String, Object> map =appServiceSet.querySharesCheck(userid, type, shareid);
				 JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/* @RequestMapping(method = RequestMethod.POST, value = "/ModifySharesCheck")
			public void ModifySharesCheck(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userid=request.getParameter("userid");
				 if (StringUtils.isEmpty(userid)) {
						throw new NullPointerException();
					}
				 String type=request.getParameter("type");
				 String stoploss=request.getParameter("stoploss");//止损点
				 String stopprofit=request.getParameter("stopprofit");//止盈点
				 String shareid=request.getParameter("shareid");
				appServiceSet.ModifySharesCheck(userId, stoploss, stopprofit, fforeignid);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
			 
		 }*/
		 
		/**
		 * 修改股票止损止盈
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/modifySharesCheck")
			public void modifySharesCheck(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String stoploss=request.getParameter("stoploss");
				 String stopprofit=request.getParameter("stopprofit");
				 String fforeignid=request.getParameter("fforeignid");
				 String type=request.getParameter("type");
				appServiceSet.modifySharesCheck(userId, stoploss, stopprofit, fforeignid, type);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**
		 * 新增外汇止损止盈
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/addforexCheck")
			public void addforexCheck(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String stoploss=request.getParameter("stoploss");
				 String stopprofit=request.getParameter("stopprofit");
				 String fforeignid=request.getParameter("fforeignid");
				appServiceSet.addforexCheck(userId, stoploss, stopprofit, fforeignid);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**修改外汇止损止盈
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/modifyforexCheck")
			public void modifyforexCheck(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 String stoploss=request.getParameter("stoploss");
				 String stopprofit=request.getParameter("stopprofit");
				 String fforeignid=request.getParameter("fforeignid");
				 String type=request.getParameter("type");
				appServiceSet.modifyforexCheck(userId, stoploss, stopprofit, fforeignid, type);
				 JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
		 
		/**
		 * 查询交易明细
		 * @param request
		 * @param response
		 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		 @RequestMapping(method = RequestMethod.POST, value = "/queryTradingDetail")
			public void queryTradingDetail(HttpServletRequest request, HttpServletResponse response){
			 try {
				 String userId=request.getParameter("userId");
				 if (StringUtils.isEmpty(userId)) {
						throw new NullPointerException();
					}
				 int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				 int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				 String shareId=request.getParameter("shareId");//股票或者外汇的id
				 String type=request.getParameter("type");//0:股票;1：外汇
				
				Map<String, Object> map =appServiceSet.queryTradingDetail(userId, shareId, type,start,limit);
				 JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
			 
		 }
	
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	 	@RequestMapping(method = RequestMethod.POST, value = "/revokeEntrust")
		public void revokeEntrust(HttpServletRequest request, HttpServletResponse response){
			try {
				String userid = request.getParameter("userid");
				String entrustid = request.getParameter("entrustid");
				String type = request.getParameter("type");//hs us wh
				
				String result = appServiceSet.revokeEntrust(userid,entrustid,type);
				JsonUtil.success2client(response, result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				String mess = e.getMessage();
				JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			}
	    }
}

