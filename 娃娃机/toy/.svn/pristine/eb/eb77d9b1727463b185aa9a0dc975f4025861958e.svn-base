package com.hxy.isw.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.FeedBackService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

//意见反馈
@Controller
@RequestMapping("/ServiceFeedBack")
public class FeedBackControl {
	
	@Autowired
	private FeedBackService feedBackService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/info")
	public void info(HttpServletRequest request, HttpServletResponse response){
		try {
			
			
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT
					: Integer.parseInt(request.getParameter("rows"));
			Map<String, Object> map = feedBackService.searchFeedback(start, limit);
			JsonUtil.mapToJson(map, response);
		}catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
		
	}
	
}
