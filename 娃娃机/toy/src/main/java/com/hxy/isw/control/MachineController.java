package com.hxy.isw.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.service.MachineService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

/**
 * @Description: 娃娃机
 * @author: lixq
 * @date 2017年6月29日 下午2:08:05
 */
@Controller
@RequestMapping("/machine")
public class MachineController {

	@Autowired
	private MachineService machineService;

	@RequestMapping(method = RequestMethod.POST, value = "/machineList")
	public void machineList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String agentname = request.getParameter("agentname");
			String type = request.getParameter("type");

			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			Employee em = (Employee) session.getAttribute("loginEmp");

			Map<String, Object> map = machineService.findMachineList(em, agentname, type, start, limit);

			JsonUtil.listToJson((List<Map<String, Object>>) map.get("list"), (Integer) map.get("pages"), response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addmachine")
	public void addmachine(HttpServletRequest request, HttpServletResponse response, HttpSession session
			) {
		try {
			String machineno = request.getParameter("machineno");
			String empid = request.getParameter("empid");
			machineService.addmachine(machineno,empid);
			JsonUtil.success2page(response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bindToys")
	public void bindToys(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			MachineInfo machineInfo) {
		try {
			Employee em = (Employee) session.getAttribute("loginEmp");

			machineService.bindToys(em, machineInfo);

			JsonUtil.success2page(response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/findAllToys")
	public void findAlltoys(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			
			String machineinfoid=request.getParameter("machineinfoid");

			Map<String, Object> map = machineService.findAlltoys(machineinfoid);

			JsonUtil.mapToJson(map, response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/findToysPrice")
	public void findToysPrice(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		try {
			Employee em = (Employee) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			Map<String, Object> map = machineService.findToysPrice(em, id);
			JsonUtil.mapToJson(map, response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/selectagent")
	public void selectagent(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		try {
			
			List<Map<String, Object>> listmap = machineService.selectagent();
			JsonUtil.listToJson(listmap, listmap.size(), response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		}

	}

}
