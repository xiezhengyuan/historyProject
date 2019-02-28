package com.hxy.isw.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.hxy.isw.service.StatisticService;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/statistic")
public class StatisticControl {
	@Autowired
	StatisticService statisticService;
	
	@RequestMapping(method=RequestMethod.POST,value="/queryuserstatistic")
	public void queryuserstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
				
			String json = statisticService.queryuserstatistic(starttime,endtime);
			
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/querytoystatistic")
	public void querytoystatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
				
			String json = statisticService.querytoystatistic(starttime,endtime);
			
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/querypointstatistic")
	public void querypointstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
				
			String json = statisticService.querypointstatistic(starttime,endtime);
			
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/queryfruitstatistic")
	public void queryfruitstatistic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
				
			String json = statisticService.queryfruitstatistic(starttime,endtime);
			
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
