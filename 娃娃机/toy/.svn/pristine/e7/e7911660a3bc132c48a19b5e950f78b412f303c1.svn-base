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

import com.hxy.isw.service.UserAppealService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;


@Controller
@RequestMapping(value="/UserAppeal")
public class UserAppealControl {
	
	@Autowired
	UserAppealService userAppealService;
	
	@RequestMapping(method=RequestMethod.POST, value="/querysystemappeal")
	public void querysystemappeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
			int records = userAppealService.countsystemappeal();
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = userAppealService.systemappeal(start, limit);
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

	@RequestMapping(method=RequestMethod.POST, value="/addappeal")
	public void addappeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String content=request.getParameter("content");
			userAppealService.addappeal(content);
			
			JsonUtil.success2page(response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/deleteappeal")
	public void deleteappeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			userAppealService.deleteappeal(id);
			
			JsonUtil.success2page(response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateappeal")
	public void updateappeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			String type=request.getParameter("type");
			String newcontent =request.getParameter("newcontent")==null?"":request.getParameter("newcontent");
			Map<String, Object> map=userAppealService.deleteappeal(id,type,newcontent);
			if(type.equals("kan")){
				JsonUtil.mapToJson(map, response);
			}else{
				JsonUtil.success2page(response);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/queryuserappeal")
	public void querynewappeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			String username=request.getParameter("username");
			String moblie=request.getParameter("moblie");
			System.out.println(moblie);
			String appealtype=request.getParameter("appealtype");
			String neworold=request.getParameter("neworold");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = userAppealService.countuserappeal(username,moblie,appealtype,neworold);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = userAppealService.queryuserappeal (username,moblie,appealtype,neworold,start, limit);
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
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="/deleteuserappeal")
	public void deleteuserappeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			String type=request.getParameter("type");
			userAppealService.deleteuserappeal(id,type);
			
			JsonUtil.success2page(response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/appealtetail")
	public void appealtetail(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String appealid=request.getParameter("appealid");
			
			Map<String, Object> map=userAppealService.appealtetail(appealid);
				JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/sendcontent")
	public void sendcontent(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {

			String id=request.getParameter("id");
			String content=request.getParameter("content");
			userAppealService.sendcontent(id,content);
			
			JsonUtil.success2page(response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
}
