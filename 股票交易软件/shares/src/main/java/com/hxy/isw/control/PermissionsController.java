package com.hxy.isw.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.PermissionsService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping(value = "/permissions")
public class PermissionsController {

	@Autowired
	private PermissionsService permissionsService;

	/**
	 * 查询管理员列表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "findPermissionsInfo", method = RequestMethod.POST)
	public void findPermissionsInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));

			Map<String, Object> permissionsInfo = permissionsService.findPermissionsInfo(ai, start, limit);

			permissionsInfo.put("op", "fail");
			permissionsInfo.put("msg", "");

			JsonUtil.mapToJson(permissionsInfo, response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}
	
	/**
	 * 封禁
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "ban", method = RequestMethod.POST)
	public void ban(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			
			permissionsService.ban(ai, id);
			JsonUtil.success2page(response);
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}
	
	/**
	 * 解禁
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "unban", method = RequestMethod.POST)
	public void unban(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			
			permissionsService.unban(ai, id);
			JsonUtil.success2page(response);
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}

	/**
	 * 新增管理（运维）
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "addPermissions", method = RequestMethod.POST)
	public void addPermissions(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String mobile = request.getParameter("mobile");
			String name = request.getParameter("name");
			String menu = request.getParameter("menu");

			Map<String, Object> map = permissionsService.addPermissions(ai, id, username, password, mobile, name, menu);
			JsonUtil.mapToJson(map, response);
		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}

	/**
	 * 查询菜单
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "queryMenu", method = RequestMethod.POST)
	public void queryMenu(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			Map<String, Object> map = permissionsService.queryMenu(ai, id);

			JsonUtil.mapToJson(map, response);
		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}
}
