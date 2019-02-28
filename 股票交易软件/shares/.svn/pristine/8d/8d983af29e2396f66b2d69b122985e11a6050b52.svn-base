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
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.ExampleService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("example")
public class ExampleController {

	@Autowired
	private ExampleService exampleService;

	/**
	 * 牛人列表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "findExampleList", method = RequestMethod.POST)
	public void findExampleList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			String keyword = request.getParameter("keyword");

			Map<String, Object> map = exampleService.findExampleList(ai, keyword, start, limit);

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
	 * 牛人待审核列表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "findAuthstrList", method = RequestMethod.POST)
	public void findAuthstrList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			String keyword = request.getParameter("keyword");

			Map<String, Object> map = exampleService.findAuthstrList(ai, keyword, start, limit);

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
	 * 添加牛人
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "addExample", method = RequestMethod.POST)
	public void addExample(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			UserInfo userInfo) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");

			Map<String, Object> map = exampleService.addExample(ai, userInfo);

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
	 * 封禁
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "ban", method = RequestMethod.POST)
	public void ban(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("id");
			String id = request.getParameter("id");
			exampleService.ban(ai, id);
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
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "unban", method = RequestMethod.POST)
	public void unban(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			exampleService.unban(ai, id);
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
	 * 牛人审核通过
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "pass", method = RequestMethod.POST)
	public void pass(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			exampleService.pass(ai, id);
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
	 * 牛人审核不通过
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "notpass", method = RequestMethod.POST)
	public void notpass(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			exampleService.notpass(ai, id);
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
	 * 抢购中
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "panicBuying", method = RequestMethod.POST)
	public void panicBuying(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			String id = request.getParameter("id");
			// 0股票 1外汇 2美股
			String type = request.getParameter("type");

			Map<String, Object> map = exampleService.panicBuying(ai, id, type, start, limit);
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
	 * 运行中
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "planRunning", method = RequestMethod.POST)
	public void planRunning(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			Map<String, Object> map = exampleService.planRunning(ai, id,type);
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
	 * 往期成功
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "pastSuccess", method = RequestMethod.POST)
	public void pastSuccess(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");
			

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}

	/**
	 * 设为精选
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "select", method = RequestMethod.POST)
	public void select(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			AccountInfo ai = (AccountInfo) session.getAttribute("loginEmp");
			String id = request.getParameter("id");

			exampleService.select(ai, id);
			JsonUtil.success2page(response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"session失效，请重新登录\"}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\"异常\"}");
		}
	}
	
}
