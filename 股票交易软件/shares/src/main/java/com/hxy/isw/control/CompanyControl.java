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
import com.hxy.isw.service.CompanyService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/company")
public class CompanyControl {

	@Autowired
	CompanyService companyService;
	
	@RequestMapping(method=RequestMethod.POST,value="/querycompany")
	public void querycompany(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = companyService.countcompany(emp,name,mobile);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = companyService.querycompany(emp,name,mobile,start, limit);
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/forbiddencompany")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void forbiddencompany(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id = request.getParameter("companyid");
			Integer state = Integer.parseInt(request.getParameter("state"));
			companyService.forbiddencompany(id,state);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}

	@RequestMapping(method = RequestMethod.POST, value = "/queryuserbycompany")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void queryuserbycompany(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id = request.getParameter("fcompanyid");
			AccountInfo accountInfo = companyService.queryuserbycompany(id);
			
			JsonUtil.objToJson(accountInfo, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/modifycompany")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modifycompany(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String companyid = request.getParameter("companyid");
			String company = request.getParameter("company");
			String mobile = request.getParameter("mobile");
			String name = request.getParameter("name");
			String proportion = request.getParameter("proportion");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			companyService.modifycompany(emp,companyid,company,mobile,name,proportion,username,password);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addcompany")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addcompany(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String companyid = request.getParameter("companyid");
			String company = request.getParameter("company");
			String mobile = request.getParameter("mobile");
			String name = request.getParameter("name");
			String proportion = request.getParameter("proportion");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			companyService.addcompany(emp,company,mobile,name,proportion,username,password);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/querymember")
	public void querymember(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String companyid = request.getParameter("companyid");
			String memberlevel = request.getParameter("memberlevel");
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = companyService.countmember(companyid,memberlevel,name,mobile);
			int total = ConstantUtil.pages(records, limit);
			int totalrole4 = companyService.querycompanyrole4(companyid);
			int totalrole5= companyService.querycompanyrole5(companyid);
			
			if(records > 0 ){
				
				lstMap = companyService.querymember(emp,companyid,memberlevel,name,mobile,start, limit);
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.addProperty("total",total);
			obj.addProperty("records",records);
			obj.add("rows", arr);
			obj.addProperty("totalrole4", totalrole4);
			obj.addProperty("totalrole5", totalrole5);
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/forbiddenmember")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void forbiddenmember(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id = request.getParameter("accountinfoid");
			Integer state = Integer.parseInt(request.getParameter("state"));
			companyService.forbiddenmember(id,state);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/querysalesmanbymanager")
	public void querysalesmanbymanager(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String accountinfoid = request.getParameter("accountinfoid");
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = companyService.countsalesman(accountinfoid,name,mobile);
			int total = ConstantUtil.pages(records, limit);
			
			if(records > 0 ){
				
				lstMap = companyService.querysalesmanbymanager(emp,accountinfoid,name,mobile,start, limit);
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
	
	@RequestMapping(method=RequestMethod.POST,value="/queryuserbysalesman")
	public void queryuserbysalesman(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String accountinfoid = request.getParameter("accountinfoid");
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = companyService.countuserbysalesman(accountinfoid,name,mobile);
			int total = ConstantUtil.pages(records, limit);
			
			if(records > 0 ){
				
				lstMap = companyService.queryuserbysalesman(emp,accountinfoid,name,mobile,start, limit);
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/addmanager")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addmanager(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String manager = request.getParameter("manager");
			String mobile = request.getParameter("mobile");
			String proportion = request.getParameter("proportion");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			companyService.addmanager(emp,manager,mobile,proportion,username,password);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addsaleman")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addsaleman(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String saleman = request.getParameter("saleman");
			String mobile = request.getParameter("mobile");
			String extensionurl = request.getParameter("extensionurl");
			String proportion = request.getParameter("proportion");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			companyService.addsaleman(emp,saleman,mobile,extensionurl,proportion,username,password);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/modifymanager")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modifymanager(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String companyid = request.getParameter("id");
			String manager = request.getParameter("manager");
			String mobile = request.getParameter("mobile");
			String proportion = request.getParameter("proportion");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			companyService.modifymanager(emp,companyid,manager,mobile,proportion,username,password);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/modifysaleman")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modifysaleman(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String accountinfoid = request.getParameter("accountinfoid");
			String saleman = request.getParameter("saleman");
			String mobile = request.getParameter("mobile");
			String proportion = request.getParameter("proportion");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			companyService.modifysaleman(emp,accountinfoid,saleman,mobile,proportion,username,password);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
}
