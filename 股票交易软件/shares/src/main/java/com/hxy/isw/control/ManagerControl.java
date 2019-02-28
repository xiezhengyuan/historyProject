package com.hxy.isw.control;

import java.util.HashMap;
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
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.ManagerService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/managercontrol")
public class ManagerControl {
	@Autowired
	ManagerService managerService;
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/querAll")
	public void queryuser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String type = request.getParameter("type");
			String keyword=request.getParameter("keyword");
			/*int records = managerService.countquerAll(type);
			int total = ConstantUtil.pages(records, limit);
			if(records>0){
				List<Map<String, Object>> lstmap=managerService.queryAll(start, limit, type);
				JsonUtil.listToJson(lstmap, records, total, response);


			}*/
			
			Map<String, Object> map = managerService.queryAll2(start, limit, type,keyword);
			
			
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/modifyState")
	public void modifyState(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id =request.getParameter("id");
			
			managerService.modifyState(id);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/queryPhoto")
	public void queryPhoto(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id =request.getParameter("id");
			JsonArray arr = managerService.queryPhoto(id);
			JsonUtil.listToJson(arr, arr.size(), response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/modifyPhoto")
	public void modifyPhoto(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id =request.getParameter("id");
			String imgarr =request.getParameter("imgarr");
			String flag =request.getParameter("flag");
			managerService.modifyPhoto(imgarr, flag, id);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	
	
	/**
	 * 查询累计充值
	 * @param request
	 * @param response
	 * @param session
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/queryRecharge")
	public void queryRecharge(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			AccountInfo acc=(AccountInfo)session.getAttribute("loginEmp");
			int role=acc.getRole();
			Map<String, Object> map =managerService.queryRecharge(role, acc);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	
	/**
	 * 查询充值明细
	 * @param request
	 * @param response
	 * @param session
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/queryRech")
	public void queryRech(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			AccountInfo acc=(AccountInfo)session.getAttribute("loginEmp");
			
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String gstarttime=request.getParameter("gstarttime");
			String gendtime=request.getParameter("gendtime");
			String querybyuserinfo=request.getParameter("querybyuserinfo");
			Map<String, Object> map =managerService.queryRech(start, limit, gstarttime, gendtime, querybyuserinfo, acc);
			
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	
	/**
	 * 充值统计图
	 * @param request
	 * @param response
	 * @param session
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/queryStatistics")
	public void queryincome(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			
			String date=request.getParameter("dates");
			AccountInfo acc=(AccountInfo)session.getAttribute("loginEmp");
			 JsonArray jsonArray = managerService.queryStatistics(date, acc);
			 
			JsonUtil.listToJson(jsonArray, jsonArray.size(), response);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
}
