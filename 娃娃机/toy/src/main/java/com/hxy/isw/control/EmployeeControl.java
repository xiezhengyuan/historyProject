package com.hxy.isw.control;

import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.BusinessInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.EmployeeService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

/**
* @author lcc
* @date 2017年5月4日 下午3:26:22
* @describe 员工管理
*/



@Controller
@RequestMapping("/employee")
public class EmployeeControl {
	
	@Autowired
	EmployeeService employeeService;
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/queryemployee")
	public void queryemployee(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String department = request.getParameter("department");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = employeeService.countemployee(bi,name,mobile,department);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = employeeService.queryemployee(bi,name,mobile,department,start, limit);
			}
			
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/querylower")
	public void querylower(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String proxylevel = request.getParameter("proxylevel");
			String userinfoid = request.getParameter("userinfoid");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = employeeService.countlower(bi,name,mobile,proxylevel,userinfoid);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = employeeService.querylower(bi,name,mobile,proxylevel,userinfoid,start, limit);
			}
			
			JsonUtil.listToJson(lstMap, records, total, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/querydepartment")
	public void querydepartment(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
			
			JsonArray arr = employeeService.querydepartment(bi);
			
			JsonUtil.listToJson(arr, arr.size(), response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addemployee")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addemployee(HttpServletRequest request,HttpServletResponse response,HttpSession session,UserInfo  userinfo){
		try {
			BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
			//userinfo.setFbusinessid(bi.getId());
			
			employeeService.addemployee(userinfo);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/modifyemployee")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modifyemployee(HttpServletRequest request,HttpServletResponse response,HttpSession session,UserInfo  userinfo){
		try {
			BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
			//userinfo.setFbusinessid(bi.getId());
			
			employeeService.modifyemployee(userinfo);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
		
	}
	
	/**
	 * 导入员工信息
	 * @param request
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/importemployeebyexcel")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void importemployeebyexcel(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		try {
			BusinessInfo bi = (BusinessInfo)session.getAttribute("loginEmp");
			
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			InputStream is = req.getFile("filePath").getInputStream();
			String filename = req.getFile("filePath").getOriginalFilename();
			
			
			String result = "";
			String filesuffix = filename.substring(filename.indexOf("."));
			 if(!filesuffix.toLowerCase().equals(".xls")){
				 result = "{\"op\":\"fail\",\"msg\":\"请上传.xls格式的EXCEL文件\"}";
				 JsonUtil.success2page(response, result);
				 return;
			 }
			
			result = employeeService.castFile2Lst(is, bi);
			
			
			JsonUtil.success2page(response, result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
	
	
}
