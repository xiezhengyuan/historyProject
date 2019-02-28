package com.hxy.isw.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AppServiceSocial;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/appServiceSocial")
public class AppServiceSocialControl {

	@Autowired
	AppServiceSocial appServiceSocial;
	
	/**
	 * 动态列表
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getpostingslist")
	public void getpostingslist(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String fpostingstyleid = request.getParameter("fpostingstyleid");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String type = request.getParameter("type");
			String exampleid = request.getParameter("exampleid");
			
			
			String json = appServiceSocial.getpostingslist(userid,fpostingstyleid,exampleid,start,limit,type);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}

	/**
	 * 动态详情
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/postingsdetail")
	public void postingsdetail(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			String json = appServiceSocial.postingsdetail(userid,postingid);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 评论列表
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/postingscomments")
	public void postingscomments(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			String json = appServiceSocial.postingscomments(userid,postingid,start,limit);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 点赞
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/praise")
	public void praise(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			String json = appServiceSocial.praise(userid,postingid);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *评论
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/comment")
	public void comment(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			String comment = request.getParameter("comment");
			String commentid = request.getParameter("commentid");
			String json = appServiceSocial.comment(userid,postingid,comment,commentid);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *打赏
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/reward")
	public void reward(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			String position = request.getParameter("position");
			String json = appServiceSocial.reward(userid,postingid,position);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *分享
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/shareposting")
	public void shareposting(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			String json = appServiceSocial.shareposting(userid,postingid);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *发微博
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sendpost")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void sendpost(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String fpostingstyleid = request.getParameter("fpostingstyleid");
			String postingscontent = request.getParameter("postingscontent");
			String photos = request.getParameter("photos");
			String json = appServiceSocial.sendpost(userid,fpostingstyleid,postingscontent,photos);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *打赏金额档位
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getrewardposition")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void getrewardposition(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String json = appServiceSocial.getrewardposition(userid);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 动态分类列表
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getpostingstyle")
	public void getpostingstyle(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
						
			String json = appServiceSocial.getpostingstyle();
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 发私信   write by lcc
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sendmsg")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void sendmsg(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");//发送人
			String freceiverid = request.getParameter("freceiverid");//接收人
			String content = request.getParameter("content");
			String json = appServiceSocial.sendmsg(userid,freceiverid,content);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
}
