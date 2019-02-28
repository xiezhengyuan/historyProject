package com.hxy.isw.control;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.service.AgentapplyService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Controller
@RequestMapping("/agentinfo")
public class AgentControl {
	
	@Autowired
	AgentapplyService  AgentapplyService;
	
	
	/**
	 * 查看代理申请
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method=RequestMethod.POST, value="/querynewagent")
	public void querynewagent(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			Employee bi = (Employee)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			//int newadd = userService.queryaddtoday(bi);
			int records = AgentapplyService.countproxyapply(bi, name, mobile);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = AgentapplyService.queryproxyapply(bi, name, mobile, start, limit);
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			//obj.addProperty("newadd", newadd);
			obj.addProperty("total",total);
			obj.addProperty("records",records);
			obj.add("rows", arr);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
			
			
			/*JsonUtil.listToJson(lstMap, records, total, response);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    
	/**
	 * 查看代理人
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method=RequestMethod.POST,value="/queryagentuser")
	public void queryagentuser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			Employee bi = (Employee)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			//int newadd = userService.queryaddtoday(bi);
			int records = AgentapplyService.countagentuser(bi,name,mobile);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = AgentapplyService.queryagentuser(bi,name,mobile,start, limit);
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			//obj.addProperty("newadd", newadd);
			obj.addProperty("total",total);
			obj.addProperty("records",records);
			obj.add("rows", arr);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
			
			
			/*JsonUtil.listToJson(lstMap, records, total, response);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	//驳回申请
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method=RequestMethod.POST,value="/disagreeapply")
	public void disagreeapply(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String fuserinfoid=request.getParameter("fuserinfoid");
			
			AgentapplyService.disagreeapply(Long.parseLong(fuserinfoid));
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	//同意申请
	@RequestMapping(method=RequestMethod.POST,value="/agreeapply")
	public void agreeapply(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			AgentapplyService.agreeapply(id,username,password);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/updateagent")
	public void updateagent(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			Map<String, Object> map=AgentapplyService.updateagent(id);
			String json = new Gson().toJson(map);
			JsonUtil.success2client(response, json);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/changeinfo")
	public void changeinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			AgentapplyService.changeinfo(id,username,password);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
		}
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/deleteagent")
	public void deleteagent(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			
			Map<String, Object> map=AgentapplyService.deleteagent(id);
			map.put("op", "success");
			
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
		}
	}
}
