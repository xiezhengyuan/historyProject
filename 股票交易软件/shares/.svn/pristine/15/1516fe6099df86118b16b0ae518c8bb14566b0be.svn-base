package com.hxy.isw.control;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.ChangeUserService;
import com.hxy.isw.service.CompanyChangeUserService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/companychangeUser")
public class CompanyChangeUserControl {
	
	@Autowired
	CompanyChangeUserService companychangeuserservice;
 
	
	//公司管理员
	@RequestMapping(method=RequestMethod.POST,value="/queryjl")
	public void queryjl(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long companyid=acc.getFcompanyid();
			List<Map<String,Object>>lstMap=companychangeuserservice.queryjl(companyid);
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.add("rows", arr);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/queryywy")
	public void queryywy(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String jlid=request.getParameter("jlid");
			List<Map<String,Object>>lstMap=companychangeuserservice.queryywy(Long.parseLong(jlid));
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.add("rows", arr);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/queryuser")
	public void queryuser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long companyid=acc.getFcompanyid();
		    String salesid=request.getParameter("salesid")==null?"0":request.getParameter("salesid");
			String accname=request.getParameter("accname")==null?"0":request.getParameter("accname");
			String gengalid=request.getParameter("gengalid")==null?"0":request.getParameter("gengalid");
			String type=request.getParameter("type");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = companychangeuserservice.countqueryuser(companyid,Long.parseLong(salesid),Long.parseLong(gengalid),accname,type);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = companychangeuserservice.queryuser(companyid,Long.parseLong(salesid),Long.parseLong(gengalid),accname,type, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@RequestMapping(method = RequestMethod.POST, value="/confirmtochange")
	public void confirmtochange(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String users=request.getParameter("users");
			long ywyid=Long.parseLong(request.getParameter("ywyid"));
			AccountInfo acc=(AccountInfo)session.getAttribute("loginEmp");
			long fdoitid=acc.getId();
			long incompanyid=acc.getFcompanyid();
			companychangeuserservice.confirmtochange(users,ywyid,fdoitid,incompanyid);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/querychange")
	public void querychange(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
			String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long companyid=acc.getFcompanyid();
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = companychangeuserservice.countquerychange(companyid,starttime,endtime);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = companychangeuserservice.querychange(companyid,starttime,endtime, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//经理
	@RequestMapping(method=RequestMethod.POST,value="/queryywyonly")
	public void queryywyonly(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long jlid=acc.getId();
			List<Map<String,Object>>lstMap=companychangeuserservice.queryywy(jlid);
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.add("rows", arr);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/querychangejl")
	public void querychangejl(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
			String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long jiid=acc.getId();
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = companychangeuserservice.countquerychangejl(jiid,starttime,endtime);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = companychangeuserservice.querychangejl(jiid,starttime,endtime, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
