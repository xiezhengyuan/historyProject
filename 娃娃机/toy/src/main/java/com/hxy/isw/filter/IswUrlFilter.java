package com.hxy.isw.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

public class IswUrlFilter implements Filter {
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		requestlog(req);
		
		String url = req.getRequestURI();
		
		//Sys.out("ConstantUtil.environment:"+ConstantUtil.environment);
		//log(request, response);
		// url like /sms/company/findall.xhtml
		if( ConstantUtil.TEXT ){
			if(ConstantUtil.environment.equals("maven"))//maven
				url = url.replace(req.getContextPath()+"/","");
			else //tomcat
				url = url.substring(1);
		}else 
			url = url.substring(1, url.length());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date now = new Date();
		
		url = url.replace(".action","");
		Sys.out(sdf.format(now)+"....url..."+url);
		
		HttpSession session = req.getSession(true);
		Object obj = session.getAttribute("loginEmp");
		
		if(url.indexOf("appService")!=-1){
			
			//来自客户端的请求
			String signature = req.getParameter("signature");
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			
			if(url.indexOf("wxnotify")==-1&&url.indexOf("notifyurl")==-1&&url.indexOf("uploadvideo")==-1){//过滤掉微信或支付宝异步通知请求（不需要签名验证）
				
				boolean flag = ConstantUtil.checkSignature(signature, timestamp, nonce);
				if(!flag){
					JsonUtil.success2page(resp, "{\"msg\":\"fail\",\"info\":\"签名错误\"}");
					return;
				}
				
			}
			
			
			
			/*resp.addHeader("Access-Control-Allow-Origin", "*");
			chain.doFilter(req, resp);*/
			chain.doFilter(request, response);
			return;
		}else if(obj==null &&url.indexOf("doLogin")==-1&&url.indexOf("getwechatJsData")==-1&&url.indexOf("mysocket")==-1&&url.indexOf("queryloadurl")==-1){//除登录请求外，其他请求在session过期时需重新登录
			
			JsonUtil.timeout2page(resp);
			return;
			
		}else{//系统内部可通过的接口
			chain.doFilter(request, response);
			return;
		}
		
	}


	@Override
	public void destroy() {

	}
	
	
	private void requestlog(HttpServletRequest req){
		Map map=req.getParameterMap();  
	    Set keSet=map.entrySet();  
	    for(Iterator itr=keSet.iterator();itr.hasNext();){  
	        Map.Entry me=(Map.Entry)itr.next();  
	        Object ok=me.getKey();  
	        Object ov=me.getValue();  
	        String[] value=new String[1];  
	        if(ov instanceof String[]){  
	            value=(String[])ov;  
	        }else{  
	            value[0]=ov.toString();  
	        }  
	  
	        for(int k=0;k<value.length;k++){  
	            Sys.out(ok+"="+value[k]);  
	        }  
	      }  
	}
	
	
}
