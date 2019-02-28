package com.hxy.isw.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.Notice;
import com.hxy.isw.service.NoticService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/notic")
public class NoticControl {
		@Autowired
		NoticService noticService;
	
		//查找历史发布公告
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		@RequestMapping(method = RequestMethod.POST, value = "/queryhnotice")
		public void queryhnotice(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String keyword =request.getParameter("keyword");
				AccountInfo acc = (AccountInfo)session.getAttribute("loginEmp");
				Map<String,Object> map = noticService.queryhnotice(start, keyword, limit, acc);
				JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
			
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/addhnotice")
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		public void addhnotice(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String target =request.getParameter("target");
				String array =request.getParameter("array");
			
				System.err.println(array);
				String noticename =request.getParameter("noticename");
				String noticecontent =request.getParameter("noticecontent");
				
				 noticService.addhnotice(noticecontent, target, noticename,array);
				JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
			
		}
		//新增定时公告
		@RequestMapping(method = RequestMethod.POST, value = "/addtimnotice")
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		public void addtimnotice(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String target =request.getParameter("target");
				String array =request.getParameter("array");
				String sendtime=request.getParameter("sendtime");
				
				String noticename =request.getParameter("noticename");
				String noticecontent =request.getParameter("noticecontent");
				
				 noticService.addtimnotice(noticecontent, target, noticename,array,sendtime);
				JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
			
		}
		//查找定时消息
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/querywaitnotic")
				public void querywaitnotic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
						int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
						String keyword =request.getParameter("keyword");
						System.out.println(keyword);
						Map<String,Object> map = noticService.querywaitnotic(start, keyword, limit);
						JsonUtil.mapToJson(map, response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
				}
				//查找定时消息根据id
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/querywaitnoticbyid")
				public void querywaitnoticbyid(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						String id =request.getParameter("id");
						
						Map<String,Object> map = noticService.querywaitnoticbyid(id);
						JsonUtil.mapToJson(map, response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
				}
				//删除定时帖子
				@RequestMapping(method = RequestMethod.POST, value = "/deletewaitnotic")
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				public void deletewaitnotic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						String id =request.getParameter("id");
						
						
						 noticService.deletewaitnotic(id);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
				}
				//修改定时公告
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/savewaitnotice")
				public void savewaitnotice(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						String id =request.getParameter("id");
						String noticename =request.getParameter("noticename");
						String noticecontent =request.getParameter("noticecontent");
						
						noticService.savewaitnotice(id, noticename, noticecontent);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
				}
				
				//查找指定公司名称
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/querycompony")
				public void querycompony(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						
						

						JsonArray arr = noticService.querycompony();
						
						JsonUtil.listToJson(arr, arr.size(), response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
				}
				
				//根据id查找公告详情
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/queryNoticeById")
				public void queryNoticeById(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						String id =request.getParameter("id");
						
						
					Map<String, Object> map=noticService.queryNoticeById(id);
						JsonUtil.mapToJson(map, response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
				}
}
