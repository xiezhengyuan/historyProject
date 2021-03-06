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
import com.hxy.isw.service.ChangeUserService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/ChangeUser")
public class ChangeUserControl {
	
	@Autowired
	ChangeUserService changeUserService;

	@RequestMapping(method=RequestMethod.POST,value="/querycompany")
	public void querycompany(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 String companyname=request.getParameter("companyname")==null?"":request.getParameter("companyname");
		 try {
			List<Map<String,Object>>lstMap=changeUserService.querycompany(companyname);
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
	
	@RequestMapping(method=RequestMethod.POST,value="/queryjl")
	public void queryjl(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 String companyid=request.getParameter("companyid");
		 try {
			List<Map<String,Object>>lstMap=changeUserService.queryjlbycompanyid(Long.parseLong(companyid));
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
		 String jlid=request.getParameter("jlid");
		 try {
			List<Map<String,Object>>lstMap=changeUserService.queryywybyjlid(Long.parseLong(jlid));
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
	
	@RequestMapping(method = RequestMethod.POST, value="/queryuserbycon")
	public void ptqueryuserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String companyid=request.getParameter("companyid");
			String jlid=request.getParameter("jlid");
			String ywyid=request.getParameter("ywyid");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			
			int records = changeUserService.countqueryuserbycompany(Long.parseLong(companyid),Long.parseLong(jlid),Long.parseLong(ywyid));
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = changeUserService.queryuserbycompany(Long.parseLong(companyid),Long.parseLong(jlid),Long.parseLong(ywyid), start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/confirmtochange")
	public void confirmtochange(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String users=request.getParameter("users");
			long ywyid=Long.parseLong(request.getParameter("ywyid")) ;
			long incompanyid=Long.parseLong(request.getParameter("incompanyid"));
			AccountInfo acc=(AccountInfo)session.getAttribute("loginEmp");
			long fdoitid=acc.getId();
			changeUserService.confirmtochange(users,ywyid,fdoitid,incompanyid);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
		
	}
	
	
	
	//转移记录
	@RequestMapping(method = RequestMethod.POST, value="/querychange")
	public void querychange(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
			String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
            List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = changeUserService.countquerychange(starttime,endtime);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = changeUserService.querychange(starttime,endtime, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//转移详情
	@RequestMapping(method = RequestMethod.POST, value="/querychangedetail")
	public void querychangedetail(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String querybyuserinfo=request.getParameter("querybyuserinfo")==null?"":request.getParameter("querybyuserinfo");
			String id=request.getParameter("id")==null?"":request.getParameter("id");
            List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = changeUserService.countquerychangedetail(Long.parseLong(id),querybyuserinfo);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = changeUserService.querychangedetail(Long.parseLong(id),querybyuserinfo, start, limit);
			}	
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(method = RequestMethod.POST,value="/querysome")
	public void querysome(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			Map<String, Object> map =changeUserService.querysome(Long.parseLong(id));
			JsonUtil.objToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
