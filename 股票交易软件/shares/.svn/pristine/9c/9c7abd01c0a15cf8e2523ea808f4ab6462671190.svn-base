package com.hxy.isw.control;

import java.io.PrintWriter;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.FeedbackService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/feedback")
public class FeedbackControl {

	@Autowired
	FeedbackService feedbackService;
	
	@RequestMapping(method=RequestMethod.POST,value="/queryfeedback")
	public void queryfeedback(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			AccountInfo emp = (AccountInfo)session.getAttribute("loginEmp");
			
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			int records = feedbackService.countfeedback(emp,name,mobile);
			int total = ConstantUtil.pages(records, limit);
			if(records > 0 ){
				
				lstMap = feedbackService.queryfeedback(emp,name,mobile,start, limit);
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.addProperty("total",total);
			obj.addProperty("records",records);
			obj.add("rows", arr);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/replyfeedback")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void replyfeedback(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id = request.getParameter("feedbackid");
			String replyinfo = request.getParameter("replyinfo");
			feedbackService.replyfeedback(id,replyinfo);
			
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"op\":\"fail\",\"msg\":\""+mess+"\"}");
		}
	}
	
}
