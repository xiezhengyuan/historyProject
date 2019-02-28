package com.hxy.isw.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AppServiceSocialService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

/**
 * @Description: 帖子
 * @author: lixq
 * @date 2017年7月4日 下午2:34:49
 */
@Controller
@RequestMapping("/appServiceSocial")
public class AppServiceSocialController {

	@Autowired
	private AppServiceSocialService appServiceSocialService;

	@RequestMapping(value = "/findPostingStyle", method = RequestMethod.POST)
	public void postingstyle(HttpServletRequest request, HttpServletResponse response) {
		try {

			Map<String, Object> map = appServiceSocialService.postingstyle();

			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/postings", method = RequestMethod.POST)
	public void postings(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String postingstyleid = request.getParameter("postingstyleid");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));

			String myself = request.getParameter("myself");

			Map<String, Object> map = appServiceSocialService.postings(userid, postingstyleid, myself, start, limit);

			JsonUtil.mapToJson(map, response);

		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "postingcomments", method = RequestMethod.POST)
	public void postingcomments(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));

			Map<String, Object> map = appServiceSocialService.postingcomments(userid, postingid, start, limit);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "sendpostings", method = RequestMethod.POST)
	public void sendpostings(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String postingsname = request.getParameter("postingsname");
			String postingscontent = request.getParameter("postingscontent");
			String postingstyleid = request.getParameter("postingstyleid");
			String photos = request.getParameter("photos");
			appServiceSocialService.sendpostings(userid, postingsname, postingscontent, postingstyleid, photos);
			JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"\"}");
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "sendcomment", method = RequestMethod.POST)
	public void sendcomment(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			String comment = request.getParameter("comment");
			String commentid = request.getParameter("commentid");
			String isanonymous = request.getParameter("isanonymous");
			appServiceSocialService.sendcomment(userid, postingid, comment, commentid,isanonymous);
			JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"\"}");
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "views", method = RequestMethod.POST)
	public void views(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");
			Map<String, Object> map = appServiceSocialService.views(userid, postingid);
			JsonUtil.mapToJson(map, response);

		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "praise", method = RequestMethod.POST)
	public void praise(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			String postingid = request.getParameter("postingid");

			String haspraise = appServiceSocialService.praise(userid, postingid);

			JsonUtil.success2client(response,
					"{\"msg\":\"success\",\"info\":\"\",\"haspraise\":\"" + haspraise + "\"}");

		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "myreplaypostings", method = RequestMethod.POST)
	public void myreplaypostings(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			Map<String, Object> map = appServiceSocialService.myreplaypostings(userid, start, limit);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"" + mess + "\"}");
			e.printStackTrace();
		}

	}
}
