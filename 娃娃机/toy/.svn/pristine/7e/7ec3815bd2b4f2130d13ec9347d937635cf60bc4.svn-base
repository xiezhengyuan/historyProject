package com.hxy.isw.control;

import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.hxy.isw.service.ModelConfigService;
import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Controller
@RequestMapping("/model")
public class ModelConfigControl {
	
	@Autowired
	ModelConfigService modelConfigService;
	
	 /*发送数据缓冲区*/  
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);  
	
	 @RequestMapping(method = RequestMethod.POST, value = "/queryuser")
		public void queryuser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String name = request.getParameter("name");
				JsonArray arr = null;
				int records = modelConfigService.countuser(name);
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					arr = modelConfigService.queryuser(name,start, limit);
				}
				
				JsonUtil.listToJson(arr, records, total, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	/* 
	 @RequestMapping(method = RequestMethod.POST, value = "/queryemp")
		public void queryemp(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				
				JsonArray arr = null;
				int records = modelConfigService.countqueryemp();
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					arr = modelConfigService.queryemp(start, limit);
					
				}
				JsonUtil.listToJson(arr, records, total, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	*/
	 
	 	@RequestMapping(method = RequestMethod.POST, value = "/mysocket")
		public void mysocket(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
			String userid = request.getParameter("userid");
			String info =request.getParameter("info");
			Long key = Long.parseLong(userid);
			sBuffer.clear();  
	        sBuffer.put(info.getBytes());  
	        sBuffer.flip(); 
	        Sys.out("NIOSServer.clientsLMap.size..."+NIOSServer.clientsLMap.size());
	        if(NIOSServer.clientsLMap.get(key)==null){
	        	JsonUtil.success2page(response, "no client");
	        	return;
	        }
			NIOSServer.clientsLMap.get(key).write(sBuffer);
			JsonUtil.success2page(response, info);
		}
	 	
	 	
	 	@RequestMapping(method = RequestMethod.POST, value = "/testfromvue")
		public void testfromvue(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				List<Map<String,Object>> alllstMap = new ArrayList<Map<String,Object>>();
				for (int i = 0; i < 100; i++) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("num", i+1);
					map.put("author", "author");
					map.put("contents", "contents");
					map.put("remark", "remark");
					alllstMap.add(map);
				}
				int start = request.getParameter("active")==null?1:Integer.parseInt(request.getParameter("active"));
				int limit = request.getParameter("len")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("len"));
				
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				for (int i = (start-1)*limit; i < start*limit; i++) {
					
					lstMap.add(alllstMap.get(i));
				}
				int pageTotal = ConstantUtil.pages(alllstMap.size(), limit);
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",alllstMap.size());
				obj.addProperty("page_num",pageTotal);
				obj.add("data", arr);
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println(obj.toString());
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
