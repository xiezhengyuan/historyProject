package com.hxy.isw.socket;

import java.io.IOException;

import com.hxy.isw.util.HttpRequest;



/**
* @author lcc
* @date 2017年4月11日 下午1:08:24
* @describe
*/
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testJpush();
	}
	public static void testJpush() {
		String url = "http://zzl.runfkj.com/model/mysocket.action";
		//url = "http://localhost:9090/kuaiwa/appservice/login.action";
    	//第一位  1 预留
		//第二位  0投币  1移动  2停止  3抓取
		//第三位  1 向前  2向后 3向左  4向右
		
		String p = "userid=4&info={114}";
    	
		String result =  HttpRequest.sendPost(url, p);
		System.out.println(result);

	}
}
