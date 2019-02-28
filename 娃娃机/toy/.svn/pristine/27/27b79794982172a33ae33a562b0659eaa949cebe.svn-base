package com.hxy.isw.control;

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

import com.google.gson.JsonArray;
import com.hxy.isw.service.EditionService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/edition")
public class EditionContorl {

	@Autowired
	EditionService editionService ;
	
	@RequestMapping(method = RequestMethod.POST, value = "/queryedition")
	public void edition(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			List<Map<String, Object>> lstmap =new ArrayList<Map<String,Object>>();
			String type=request.getParameter("type");
			int records = editionService.countedition(type);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstmap = editionService.edition(type,start, limit);
			}
			
			JsonUtil.listToJson(lstmap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/usethisedition")
	public void usethisedition(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id=request.getParameter("id");
			String type=request.getParameter("type");
			editionService.usethisedition(id,type);
			
			JsonUtil.success2page(response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateapp")
	public void updateapp(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			
			Map<String, Object> map =editionService.updateapp(request);
			
			JsonUtil.mapToJson(map, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/queryloadurl")
	public void queryloadurl(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			
			Map<String, Object> map =editionService.queryloadurl();
			
			JsonUtil.mapToJson(map, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateedition")
	public void updateedition(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String editionid=request.getParameter("editionid");
		    String type=request.getParameter("type");
		    String editionno=request.getParameter("editionno");
		    String description=request.getParameter("description");
			
			editionService.updateedition(editionid,type,editionno,description);
			
			JsonUtil.success2page(response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();	
		}
		
	}
	
}
