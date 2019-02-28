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

import com.hxy.isw.service.AppserviceaboutService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping(value="/appServiceabout")
public class AppServiceAboutControl {

	
	@Autowired
	AppserviceaboutService appserviceaboutService;
	
	/**
	 * 我的关注详情
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/myfollow")
	public void  myfollow(HttpServletRequest request,HttpServletResponse response){
		 
		 try {
			    String userid=request.getParameter("userid");
			    String nowuserid=request.getParameter("nowuserid")==null?"null":request.getParameter("nowuserid");
			    int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				int records = appserviceaboutService.countmyfollow(Long.parseLong(userid));
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					lstMap = appserviceaboutService.myfollowinfo(Long.parseLong(userid),nowuserid, start, limit);
				}	
				JsonUtil.listToJson(lstMap, records, total, response);
		     } catch (Exception e) {
			        e.getStackTrace();
		     }
	}
	
	/**
	 * 我的fans详情
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/myfans")
	public void  myfans(HttpServletRequest request,HttpServletResponse response){
		 String userid=request.getParameter("userid");
		 String nowuserid=request.getParameter("nowuserid")==null?"null":request.getParameter("nowuserid");
		 try {
			    int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				int records = appserviceaboutService.countmyfans(Long.parseLong(userid));
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					lstMap = appserviceaboutService.myfansinfo(Long.parseLong(userid),nowuserid, start, limit);
				}	
				JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.getStackTrace();
		}
		 
	}
	
	/**
	 * 我评论的
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/mycomment")
	public void  mycomment(HttpServletRequest request,HttpServletResponse response){
		
		 try {  
			    String userid=request.getParameter("userid");
			    int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				int records = appserviceaboutService.countmycomment(Long.parseLong(userid));
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					lstMap = appserviceaboutService.mycommentinfo(Long.parseLong(userid), start, limit);
				}	
				JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	/**
	 * 评论我的
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/commentme")
	public void  commentme(HttpServletRequest request,HttpServletResponse response){
		
		 try {  
			    String userid=request.getParameter("userid");
			    int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				int records = appserviceaboutService.countcommentme(Long.parseLong(userid));
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					lstMap = appserviceaboutService.commentmeinfo(Long.parseLong(userid), start, limit);
				}	
				JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.getStackTrace();
		}
		 
	}
	
	
	/**
	 * 删除我评论的
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deletemycomment")
	public void deletemycomment(HttpServletRequest request,HttpServletResponse response){
		String id= request.getParameter("id");
		try {
			appserviceaboutService.deletemycomment(Long.parseLong(id));
			JsonUtil.success2page(response);
		} catch (Exception e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 删除我消息列表的
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deletemymessage")
	public void deletemymessage(HttpServletRequest request,HttpServletResponse response){
		String fnotifyid= request.getParameter("fnotifyid");
		String userid= request.getParameter("userid");
		try {
			appserviceaboutService.deletemymessage(Long.parseLong(fnotifyid),Long.parseLong(userid));
			JsonUtil.success2page(response);
		} catch (Exception e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 删除我的系统列表
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deletemysystemmessage")
	public void deletemysystemmessage(HttpServletRequest request,HttpServletResponse response){
		String fnotifyid= request.getParameter("fnotifyid");
		try {
			appserviceaboutService.deletemysystemmessage(Long.parseLong(fnotifyid));
			JsonUtil.success2page(response);
		} catch (Exception e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 私信列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/myprivateletter")
	public void  myprivateletter(HttpServletRequest request,HttpServletResponse response){
		
		 try {  
			    String userid=request.getParameter("userid");
			    int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				int records = appserviceaboutService.countmyprivateletter(Long.parseLong(userid));
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					lstMap = appserviceaboutService.myprivateletterinfo(Long.parseLong(userid), start, limit);
				}	
				JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.getStackTrace();
		}
		 
	}
	
	/**
	 * 私信详情
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/myprivateletterdetil")
	public void  myprivateletterdetil(HttpServletRequest request,HttpServletResponse response){
		
		 try {  
			    String userid=request.getParameter("userid");
			    String fnotifyid=request.getParameter("fnotifyid");
			    int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				int records = appserviceaboutService.countmyprivateletterdetil(Long.parseLong(userid),Long.parseLong(fnotifyid));
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					lstMap = appserviceaboutService.myprivateletterdetilinfo(Long.parseLong(userid),Long.parseLong(fnotifyid), start, limit);
				}	
				JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.getStackTrace();
		}
		 
	}
	
	/**
	 * 系统消息列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/mynews")
	public void  mynews(HttpServletRequest request,HttpServletResponse response){
		
		 try {  
			    String userid=request.getParameter("userid");
			    int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				int records = appserviceaboutService.countmynews(Long.parseLong(userid));
				int total = ConstantUtil.pages(records, limit);
				if(records > 0 ){
					
					lstMap = appserviceaboutService.mynewsinfo(Long.parseLong(userid), start, limit);
				}	
				JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			e.getStackTrace();
		}
		 
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/sharetuijian")
	public void  sharetuijian(HttpServletRequest request,HttpServletResponse response){
		String userid=request.getParameter("userid");
		 try { 
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();

				lstMap = appserviceaboutService.sharetuijian(userid);
				JsonUtil.listToJson(lstMap, 6, response);
		} catch (Exception e) {
			e.getStackTrace();
		}
		 
	}
	
	
}
