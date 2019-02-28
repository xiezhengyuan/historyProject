package com.hxy.isw.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class DocumentManagerUtil {
	
	
	public static void initDom(String path){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			ConstantUtil.DOM = documentBuilder.parse(path);
		}catch(Exception e){
		   e.printStackTrace();
		}
	}
}
