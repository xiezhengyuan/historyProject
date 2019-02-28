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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.CompanyuserService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping(value="/companyuser")
public class CompanyUsercontrol {
	
	@Autowired
	CompanyuserService companyuserService;

	//公司
	@RequestMapping(method = RequestMethod.POST, value="/comqueryuserinfo")
	public void comqueryuserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String selecttype = request.getParameter("selecttype");
			String querybyuserinfo = request.getParameter("querybyuserinfo")==null?"":request.getParameter("querybyuserinfo");
			String querybyaccountinfo = request.getParameter("querybyaccountinfo")==null?"":request.getParameter("querybyaccountinfo");
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long companyid=acc.getFcompanyid();
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			
			int records = companyuserService.comcountuserinfo(companyid,selecttype,querybyuserinfo, querybyaccountinfo);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = companyuserService.comqueryuserinfo(companyid,selecttype,querybyuserinfo, querybyaccountinfo, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//公司
	@RequestMapping(method = RequestMethod.POST, value="/comqueryquerynewlod")
	public void ptquerynewlod(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long companyid=acc.getFcompanyid();
			int olduser=companyuserService.oldusernum(companyid);
			int todayuser=companyuserService.todayusernum(companyid);
			JsonObject obj = new JsonObject();
			obj.addProperty("olduser",olduser);
			obj.addProperty("todayuser",todayuser);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//经理
	@RequestMapping(method = RequestMethod.POST, value="/generalqueryuserinfo")
	public void generalqueryuserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String selecttype = request.getParameter("selecttype");
			String querybyuserinfo = request.getParameter("querybyuserinfo")==null?"":request.getParameter("querybyuserinfo");
			String querybyaccountinfo = request.getParameter("querybyaccountinfo")==null?"":request.getParameter("querybyaccountinfo");
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long generalid=acc.getId();
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			
			int records = companyuserService.countgeneralqueryuserinfo(generalid,selecttype,querybyuserinfo, querybyaccountinfo);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = companyuserService.generalqueryuserinfo(generalid,selecttype,querybyuserinfo, querybyaccountinfo, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//经理
	@RequestMapping(method = RequestMethod.POST, value="/generalqueryquerynewlod")
	public void generalqueryquerynewlod(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long generalid=acc.getId();
			int olduser=companyuserService.generaloldusernum(generalid);
			int todayuser=companyuserService.generaltodayusernum(generalid);
			JsonObject obj = new JsonObject();
			obj.addProperty("olduser",olduser);
			obj.addProperty("todayuser",todayuser);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//业务员
	@RequestMapping(method = RequestMethod.POST, value="/salesmanqueryuserinfo")
	public void salesmanqueryuserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String selecttype = request.getParameter("selecttype");
			String querybyuserinfo = request.getParameter("querybyuserinfo")==null?"":request.getParameter("querybyuserinfo");
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long salesmanid=acc.getId();
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			
			int records = companyuserService.countsalesmanqueryuserinfo(salesmanid,selecttype,querybyuserinfo);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = companyuserService.salesmanqueryuserinfo(salesmanid,selecttype,querybyuserinfo, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//业务员
	@RequestMapping(method = RequestMethod.POST, value="/salesmanqueryquerynewlod")
	public void salesmanqueryquerynewlod(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long salesmanid=acc.getId();
			int olduser=companyuserService.salesmanoldusernum(salesmanid);
			int todayuser=companyuserService.salesmantodayusernum(salesmanid);
			JsonObject obj = new JsonObject();
			obj.addProperty("olduser",olduser);
			obj.addProperty("todayuser",todayuser);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
