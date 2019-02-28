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
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.MoneyService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Controller
@RequestMapping("/money")
public class MoneyControl {

	@Autowired
	MoneyService moneyService;
	
	@RequestMapping(method=RequestMethod.POST,value="/querycash")
	public void querycompany(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String state = request.getParameter("state");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = moneyService.countcash(emp,name,mobile,state);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = moneyService.querycash(emp,name,mobile,state,start, limit);
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
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
	
	@RequestMapping(method=RequestMethod.POST,value="/queryrecharge")
	public void queryrecharge(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = moneyService.countcharge(emp,name,mobile,starttime,endtime);
			int total = ConstantUtil.pages(records, limit);
			
				
				lstMap = moneyService.querycharge(emp,name,mobile,starttime,endtime,start, limit);
			
			Map<String, Object> totalrecharge = lstMap.get(lstMap.size()-1);
			lstMap.remove(totalrecharge);
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.addProperty("total",total);
			obj.addProperty("records",records);
			obj.addProperty("totalrecharge",totalrecharge.get("totalrecharge").toString());
			
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

	@RequestMapping(method = RequestMethod.POST,value="/updatecashinfo")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updatecashinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id =request.getParameter("cashinfoid");
			String state =request.getParameter("state");
			
			moneyService.updatestate(id, state);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/outportcashlog")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void outportcashlog(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String state = request.getParameter("state");
			
			String filename = moneyService.outportcashlog(emp,name, mobile,state);
			
			JsonUtil.success2page(response,"{\"op\":\"success\",\"excel\":\""+filename+"\"}");
		} catch (Exception e) {
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/querycashstatistic")
	public void querycashstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String starttime = request.getParameter("starttime");
			  JsonArray jsonArray = moneyService.querycashstatistic(emp,starttime);
			 
			JsonUtil.listToJson(jsonArray, jsonArray.size(), response);
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
