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
import com.hxy.isw.entity.Setting;
import com.hxy.isw.service.SetService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/setting")
public class SetControl {

	@Autowired
	SetService setService;

	@RequestMapping(method=RequestMethod.POST,value="/modifyset")
	public void modifyset(HttpServletRequest request,HttpServletResponse response,HttpSession session,Setting setting)throws Exception{
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			setService.modifyset(emp, setting);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/addreward")
	public void addreward(HttpServletRequest request,HttpServletResponse response,HttpSession session,Setting setting)throws Exception{
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String position = request.getParameter("position");
			
			setService.addreward(position);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/queryreward")
	public void queryreward(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = setService.countreward();
			int total = ConstantUtil.pages(records, limit);
				lstMap = setService.queryreward(start, limit);
			
			
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
	
	@RequestMapping(method=RequestMethod.POST,value="/delreward")
	public void delreward(HttpServletRequest request,HttpServletResponse response,HttpSession session,Setting setting)throws Exception{
		try {
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			String rewardid = request.getParameter("rewardid");
			
			setService.delreward(rewardid);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
}
