package com.hxy.isw.control;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.StatisticService;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/statistic")
public class StatisticControl {

	@Autowired
	StatisticService statisticService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/querywalletstatistic")
	public void querywalletstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String starttime = request.getParameter("starttime");
			String type = request.getParameter("type");
			String companyid = request.getParameter("companyid");
			  JsonArray jsonArray = statisticService.querywalletstatistic(emp,starttime,type,companyid);
			 
			JsonUtil.listToJson(jsonArray, jsonArray.size(), response);
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/queryuserstatistic")
	public void queryuserstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String starttime = request.getParameter("starttime");
			String type = request.getParameter("type");
			String companyid = request.getParameter("companyid");
			  JsonArray jsonArray = statisticService.queryuserstatistic(emp,starttime,type,companyid);
			 
			JsonUtil.listToJson(jsonArray, jsonArray.size(), response);
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/queryuser")
	public void queryuser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			 JsonArray jsonArray = statisticService.queryuser(emp);
			 
			JsonUtil.listToJson(jsonArray, jsonArray.size(), response);
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/queryincome")
	public void queryincome(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			 JsonArray jsonArray = statisticService.queryincome(emp);
			 
			JsonUtil.listToJson(jsonArray, jsonArray.size(), response);
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/querycash")
	public void querycash(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			 JsonArray jsonArray = statisticService.querycash(emp);
			 
			JsonUtil.listToJson(jsonArray, jsonArray.size(), response);
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 彭亮
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/querycompanywalletstatistic")
	public void querycompanywalletstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
	
			
			AccountInfo acc = (AccountInfo)session.getAttribute("loginEmp");
			
			String starttime = request.getParameter("starttime");
			String type = request.getParameter("type");
			try {
				List<Map<String, Object>> lstmap= statisticService.querycompanywalletstatistic(acc,starttime,type);
				String json = new Gson().toJson(lstmap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",lstmap.size());
				obj.addProperty("role",acc.getRole());
				obj.add("rows", arr);
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println(obj.toString());
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/queryuserandmoney")
	public void queryuserandmoney(HttpServletRequest request,HttpServletResponse response,HttpSession session){
	
			
			AccountInfo acc = (AccountInfo)session.getAttribute("loginEmp");
			try {
				Map<String, Object> map= statisticService.queryuserandmoney(acc);
				JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/queryusersstatistic")
	public void queryusersstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
	
			
			AccountInfo acc = (AccountInfo)session.getAttribute("loginEmp");
			
			String starttime = request.getParameter("starttime");
			String type = request.getParameter("type");
			try {
				List<Map<String, Object>> lstmap= statisticService.queryusersstatistic(acc,starttime,type);
				String json = new Gson().toJson(lstmap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",lstmap.size());
				obj.addProperty("role",acc.getRole());
				obj.add("rows", arr);
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println(obj.toString());
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/querymyadduser")
	public void querymyadduser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
	
			
			AccountInfo acc = (AccountInfo)session.getAttribute("loginEmp");
			try {
				Map<String, Object> map= statisticService.querymyadduser(acc);
				JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
