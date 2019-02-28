package com.hxy.isw.control;

import java.util.Date;
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
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.ToysType;
import com.hxy.isw.service.ToyService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/toy")
public class ToyController {

	@Autowired
	private ToyService toyService;

	@RequestMapping(value = "/toysinfoList", method = RequestMethod.POST)
	public void toysinfoList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String agentname = request.getParameter("agentname");
			String type = request.getParameter("type");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			Employee em = (Employee) session.getAttribute("loginEmp");

			Map<String, Object> map = toyService.toysInfoList(em, start, limit, agentname, type);
			JsonUtil.listToJson((List<Map<String, Object>>) map.get("rows"), (Integer) map.get("pages"), response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}

	@RequestMapping(value = "/addToys", method = RequestMethod.POST)
	public void addToys(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ToysInfo toysInfo) {
		try {
			Employee em = (Employee) session.getAttribute("loginEmp");
			String photo = request.getParameter("thumbnail");
			String ffileinfoid = request.getParameter("ffileinfoid");
			toysInfo.setPhoto(photo);// 缩略图地址
			toysInfo.setFfileinfoid(Long.parseLong(ffileinfoid));// 缩略图id
			toysInfo.setState(0);
			toysInfo.setCreatetime(new Date());
			String imgarr = request.getParameter("imgarr");// 图片列表
			toyService.addToys(toysInfo, imgarr);
			JsonUtil.success2page(response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}

	@RequestMapping(value = "/toysTypeList", method = RequestMethod.POST)
	public void toysTypeList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String name = request.getParameter("name");

			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			Employee em = (Employee) session.getAttribute("loginEmp");

			Map<String, Object> map = toyService.toysTypeList(em, start, limit, name);

			JsonUtil.listToJson((List<Map<String, Object>>) map.get("rows"), (Integer) map.get("pages"), response);
		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}

	@RequestMapping(value = "/addToysType", method = RequestMethod.POST)
	public void addToysType(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ToysType toysType) {
		try {
			Employee em = (Employee) session.getAttribute("loginEmp");
			toyService.addToysType(em, toysType);
			JsonUtil.success2page(response, "{\"op\":\"success\",\"msg\":\"\"}");

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}

	}

	@RequestMapping(value = "/delToysType", method = RequestMethod.POST)
	public void delToysType(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String id = request.getParameter("id");
			Employee em = (Employee) session.getAttribute("loginEmp");

			toyService.delToysType(em, id);
			JsonUtil.success2page(response, "{\"op\":\"success\",\"msg\":\"删除成功\"}");
		} catch (NullPointerException e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}

	}

	@RequestMapping(value = "/findToysTypeById", method = RequestMethod.POST)
	public void findToysTypeById(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String id = request.getParameter("id");
			Employee em = (Employee) session.getAttribute("loginEmp");
			Map<String, Object> map = toyService.findToysTypeById(em, id);
			map.put("op", "success");
			map.put("msg", "");
			JsonUtil.mapToJson(map, response);
		} catch (NullPointerException e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}

	}

	@RequestMapping(value = "/findAllToysType", method = RequestMethod.POST)
	public void findAlltoysType(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			Employee em = (Employee) session.getAttribute("loginEmp");

			Map<String, Object> map = toyService.findAlltoysType(em);
			JsonUtil.mapToJson(map, response);
		} catch (NullPointerException e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}
	
	@RequestMapping(value = "/findToysPhotos", method = RequestMethod.POST)
	public void findToysPhotos(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {
			Employee em = (Employee) session.getAttribute("loginEmp");
			String toysid=request.getParameter("toysid");
			JsonArray toysPhotos = toyService.findToysPhotos(em, toysid);
			JsonUtil.listToJson(toysPhotos, toysPhotos.size(), response);
		} catch (NullPointerException e) {
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
		
		
	}
	
	@RequestMapping(value = "/querybannerimg", method = RequestMethod.POST)
	public void querybannerimg(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {
			List<Map<String, Object>> map = toyService.querybannerimg();
			if(map==null){
				JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
			}else{
				JsonUtil.listToJson(map, map.size(), response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
		
		
	}
	
	@RequestMapping(value = "/updatebannerimgs", method = RequestMethod.POST)
	public void updatebannerimgs(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {
			
			String imgs=request.getParameter("imgs");
			toyService.updatebannerimgs(imgs);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
		
		
	}
	
	@RequestMapping(value = "/deletetoys", method = RequestMethod.POST)
	public void deletetoys(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try {
			String id=request.getParameter("id");
			toyService.deletetoys(id);
			JsonUtil.success2page(response, "{\"op\":\"success\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+e.getMessage()+"\"}");
		}
		
		
	}
	


}
