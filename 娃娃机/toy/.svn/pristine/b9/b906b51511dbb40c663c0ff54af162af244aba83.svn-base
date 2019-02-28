package com.hxy.isw.control;

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

import com.google.gson.JsonArray;
import com.hxy.isw.entity.PostingStyle;
import com.hxy.isw.service.PostingService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/posting")
public class PostingControl {
      @Autowired
      PostingService postingService;
      
      @RequestMapping(method = RequestMethod.POST, value = "/querypostingstyle")
  	public void querysupplier(HttpServletRequest request,HttpServletResponse response,HttpSession session){
  		try {
  			/*int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
  			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));*/
  			
  			//BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
  			
  			JsonArray arr = postingService.querypostingstyle();
  			
  			JsonUtil.listToJson(arr, arr.size(), response);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
	
      @RequestMapping(method = RequestMethod.POST, value = "/addpostingstyle")
  	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
  	public void addpostingstyle(HttpServletRequest request,HttpServletResponse response,HttpSession session){
  		try {
  			String stylename = request.getParameter("stylename");
  			
  			  PostingStyle postingStyle = postingService.addpostingstyle(stylename);
  			
  			JsonUtil.objToJson(postingStyle,response);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			String mess = e.getMessage();
  			e.printStackTrace();
  			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
  		}
  		
  	}

      @RequestMapping(method = RequestMethod.POST, value = "/queryposting")
  	public void queryposting(HttpServletRequest request,HttpServletResponse response,HttpSession session){
  		try {
  			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
  			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
  			
  			//BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
  			
  			String name = request.getParameter("name");
  			String fpostingstyleid = request.getParameter("fpostingstyleid");
  			String fuser = request.getParameter("fuser");
  			
  			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
  			int records = postingService.countposting(name,fpostingstyleid,fuser);
  			int total = ConstantUtil.pages(records, limit);
  			if(records > 0 ){
  				
  				lstMap = postingService.queryposting(name,fpostingstyleid,fuser,start, limit);
  			}
  			
  			JsonUtil.listToJson(lstMap, records, total, response);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
      
      @RequestMapping(method = RequestMethod.POST, value = "/querypostingphotosbyid")
  	public void querypostingphotosbyid(HttpServletRequest request,HttpServletResponse response,HttpSession session){
  		try {
  				Long id = Long.parseLong(request.getParameter("postingid"));
  				JsonArray arr = postingService.querypostingphotosbyid(id);
  				JsonUtil.listToJson(arr, arr.size(), response);
  			}
  			
  			
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
      
      @RequestMapping(method = RequestMethod.POST, value = "/querypostingcomments")
    	public void querypostingcomments(HttpServletRequest request,HttpServletResponse response,HttpSession session){
    		try {
    			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
    			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
    			
    			String postingid = request.getParameter("postingid");
    			
    			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
    			int records = postingService.countpostingcomments(postingid);
    			int total = ConstantUtil.pages(records, limit);
    			if(records > 0 ){
    				
    				lstMap = postingService.querypostingcomments(postingid,start, limit);
    			}
    			
    			JsonUtil.listToJson(lstMap, records, total, response);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
      
      @RequestMapping(method = RequestMethod.POST, value = "/passposting")
  	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
  	public void passposting(HttpServletRequest request,HttpServletResponse response,HttpSession session){
  		try {
  			String id = request.getParameter("postingid");
  			postingService.passposting(id);
  			
  			JsonUtil.success2page(response);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			String mess = e.getMessage();
  			e.printStackTrace();
  			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
  		}
  	}
      
      @RequestMapping(method = RequestMethod.POST, value = "/nopassposting")
  	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
  	public void nopassposting(HttpServletRequest request,HttpServletResponse response,HttpSession session){
  		try {
  			String id = request.getParameter("postingid");
  			postingService.nopassposting(id);
  			
  			JsonUtil.success2page(response);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			String mess = e.getMessage();
  			e.printStackTrace();
  			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
  		}
  	}
      
}
