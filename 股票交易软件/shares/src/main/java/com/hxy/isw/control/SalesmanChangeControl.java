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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.SalesmanChangeService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping(value="/salesmanchange")
public class SalesmanChangeControl {
	
	@Autowired
	SalesmanChangeService salesmanchangeService;

	
	@RequestMapping(method=RequestMethod.POST,value="/querysalesman")
	public void querysalesman(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			String companyid=request.getParameter("companyid")==null?acc.getFcompanyid().toString():request.getParameter("companyid");
			String jlid=request.getParameter("jlid")==null?"":request.getParameter("jlid");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			
			int records = salesmanchangeService.countsalesman(companyid, jlid);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = salesmanchangeService.lstmap(companyid, jlid, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(method=RequestMethod.POST,value="/confirmchange")
	public void confirmchange(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String salesmans=request.getParameter("salesmans");
			String injlid=request.getParameter("injlid");
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			long doitid=acc.getId();
			String incompanyid=request.getParameter("incompanyid")==null?acc.getFcompanyid().toString():request.getParameter("incompanyid");
			salesmanchangeService.confirmchange(doitid, salesmans, injlid, incompanyid);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/querychangerem")
	public void querychangerem(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
			String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			AccountInfo acc=(AccountInfo) session.getAttribute("loginEmp");
			int records = salesmanchangeService.countchangerem(starttime, endtime, acc);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = salesmanchangeService.querychangerem(starttime, endtime, acc, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/querytop")
	public void querytop(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String changeid= request.getParameter("id");
			Map<String, Object> map=salesmanchangeService.querytop(changeid);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/querychangedetli")
	public void querychangedetli(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String querybysalesmaninfo=request.getParameter("querybysalesmaninfo");
			String id=request.getParameter("id");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = salesmanchangeService.countchangedetli(querybysalesmaninfo, id);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = salesmanchangeService.querychangedetli(querybysalesmaninfo, id, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
