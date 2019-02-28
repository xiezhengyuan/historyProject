package com.hxy.isw.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.thread.CheckFileState;
import com.hxy.isw.thread.ReflushAccessToken;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;
import com.hxy.isw.util.ThreadPoolManager;



@Controller
public class IndexControl {
	
	@Autowired
	DatabaseHelper databaseHelper;
	
	@PostConstruct
	public void init() throws Exception{
		//Sys.out(MD5.JM("123456"));
		String path = this.getClass().getResource("/").getPath();
	
		initialize();
		
		if(path.indexOf("/workspace/")!=-1){
			ConstantUtil.environment = "maven";
		}else{
			ConstantUtil.environment = "tomcat";
		}
		
		Sys.out("ConstantUtil.environment:"+ConstantUtil.environment);
		//ConstantUtil.PROJECT_PATH = readconfig("prjpath");
		
		//ConstantUtil.PROJECT_PATH = path;//TODO delete when is publish
		
		//启动socket
		//NIOSServer server = NIOSServer.getInstance();
		//ThreadPoolManager.exec(server);
		
		//启动reflushAccessToken
		ReflushAccessToken reflushAccessToken = new ReflushAccessToken();
		ThreadPoolManager.exec(reflushAccessToken);
		
		//启动文件检查
		CheckFileState checkFileState = new CheckFileState();
		ThreadPoolManager.exec(checkFileState);
	}
	
	@PreDestroy
	public void destroy(){
		
	}
	
	
	private void  initialize(){
		CheckFileState.databaseHelper = databaseHelper;
	}
	
	
	private String readconfig(String filename){
		//String path = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", "");
		String path = ConstantUtil.PROJECT_PATH+filename+".txt";
		
	
		File file = new File(path );
		Sys.out("before if ......"+path );
		try {
			if (file.exists()) {
				
				Sys.out("after if ......"+path);
				BufferedReader b = new BufferedReader(new FileReader(file));
				 String ss= "";	
					String s = "";
					do {

						s += ss;
						

					} while ((ss = b.readLine())!= null);
					Sys.out("s............."+s);
				return s;
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
