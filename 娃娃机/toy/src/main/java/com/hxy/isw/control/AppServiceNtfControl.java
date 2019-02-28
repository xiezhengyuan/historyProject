package com.hxy.isw.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AppServiceNtf;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

import net.sourceforge.jeval.function.string.StringFunctions;

@Controller
@RequestMapping("/appServiceNtf")
public class AppServiceNtfControl {
	
	@Autowired
	AppServiceNtf AppServiceNtf;
	//消息列表
	@RequestMapping(method = RequestMethod.POST,value="/notifyinfo")
	public void notifyinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT: Integer.parseInt(request.getParameter("rows"));
			String userid = request.getParameter("userid");
			if(StringUtils.isEmpty(userid)){
				throw new Exception("用户不存在");
			}
			Map<String, Object> map= AppServiceNtf.queryNotifyinfo(userid, limit, start);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/nearnotifyinfo")
	public void nearnotifyinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT: Integer.parseInt(request.getParameter("rows"));
			String userid = request.getParameter("userid");
			if(StringUtils.isEmpty(userid)){
				throw new Exception("用户不存在");
			}
			Map<String, Object> map= AppServiceNtf.nearnotifyinfo(userid, limit, start);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
		
	}
	
	
	//消息详情(私信)
	@RequestMapping(method = RequestMethod.POST,value="/message")
	public void message(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT: Integer.parseInt(request.getParameter("rows"));
			String userid = request.getParameter("userid");
			String notifyid = request.getParameter("notifyid");
			if(StringUtils.isEmpty(userid)){
				throw new Exception("用户不存在");
			}
			Map<String, Object> map = AppServiceNtf.queryMessage(userid, limit, start,notifyid);
			
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	//发私信
	@RequestMapping(method = RequestMethod.POST,value="/sendmessage")
	public void sendmessage(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String userid= request.getParameter("userid"); //发送人id
			if(StringUtils.isEmpty(userid)){
				throw new Exception("用户不存在");
			}
			String receiveid=request.getParameter("receiveid");//接收者id
			String content=request.getParameter("content");
			Map<String, Object> map = AppServiceNtf.querySendmessage(userid, receiveid, content);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
}
