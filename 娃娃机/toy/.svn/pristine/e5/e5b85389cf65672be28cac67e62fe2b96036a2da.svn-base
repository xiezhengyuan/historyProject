package com.hxy.isw.control;

import java.io.PrintWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
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
import com.hxy.isw.entity.BusinessInfo;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.UserService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

/**
* @author lcc
* @date 2017年5月4日 下午3:29:15
* @describe 用户管理
*/

@Controller
@RequestMapping("/userinfo")
public class UserControl {

	@Autowired
	UserService userService;
	
	@RequestMapping(method=RequestMethod.POST,value="/queryuserinfo")
	public void queryuserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			Employee bi = (Employee)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			//int newadd = userService.queryaddtoday(bi);
			int records = userService.countuserinfo(bi,name,mobile);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = userService.queryuseinfo(bi,name,mobile,start, limit);
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

	@RequestMapping(method = RequestMethod.POST, value = "/modifyuserinfo")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modifyuserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session,UserInfo  userinfo){
		try {
			Employee bi = (Employee)session.getAttribute("loginEmp");
			//userinfo.setFbusinessid(bi.getId());
			
			userService.modifyuserinfo(userinfo);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/querylower")
	public void querylower(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			Employee bi = (Employee)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String proxylevel = request.getParameter("proxylevel");
			String userinfoid = request.getParameter("userinfoid");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = userService.countlower(bi,name,mobile,proxylevel,userinfoid);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = userService.querylower(bi,name,mobile,proxylevel,userinfoid,start, limit);
			}
			
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/adduserinfo")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void adduserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session,UserInfo  userinfo){
		try {
			Employee bi = (Employee)session.getAttribute("loginEmp");
			//userinfo.setFbusinessid(bi.getId());
			userinfo.setNickname("hello world");
			userService.adduserinfo(userinfo);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/queryrecord")
	public void queryrecord(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			Employee bi = (Employee)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String userinfoid = request.getParameter("userinfoid");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = userService.countrecord(bi,name,userinfoid);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = userService.queryrecord(bi,name,userinfoid,start, limit);
			}
			
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
