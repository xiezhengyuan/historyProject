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
		String url = "http://localhost:5555/ace/model/goEasy.action";
		//url = "http://localhost:9090/kuaiwa/appservice/login.action";
    	String p = "userid=3&info=hello aaaa ccc c ddd";
		String result =  HttpRequest.sendPost(url, p);
		System.out.println(result);

	}
}
