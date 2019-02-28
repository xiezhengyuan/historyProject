package com.hxy.isw.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hxy.isw.entity.Edition;
import com.hxy.isw.entity.FileInfo;
import com.hxy.isw.service.EditionService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;


@Service
public class EditionServiceImp implements EditionService{

	@Autowired
	DatabaseHelper databaseHelper;
	@Override
	public int countedition(String type) throws Exception {
		
		String sql="select count(id) from edition where type ="+type+" and state !=-2 ";
		
		return databaseHelper.getSqlCount(sql);
	}

	@Override
	public List<Map<String, Object>> edition(String type, int start, int limit) throws Exception {
		String hql="select e from Edition e where e.type ="+type+" and e.state !=-2 order by e.createtime desc ";
		List<Object> lst=databaseHelper.getResultListByHql(hql, start, limit);
		List<Map<String, Object>> lstmap=new ArrayList<Map<String,Object>>();
		for (Object obj : lst) {
			Edition e=(Edition) obj;
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", e.getId());
			map.put("editionno", e.getEditionno());
			map.put("url", e.getUrl());
			map.put("time", e.getCreatetime());
			map.put("state", e.getState()==-1?"停用":"正在使用");
			lstmap.add(map);
		}
		return lstmap;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void usethisedition(String id,String type) throws Exception {


		//使其他版本失效
		String sql=" update edition set state =-1 where type= "+type+" and state !=-2 ";
		int count=databaseHelper.executeNativeSql(sql);
		if(count==0)throw new Exception("数据失效");
		Edition e=(Edition) databaseHelper.getObjectById(Edition.class, Long.parseLong(id));
		e.setState(0);
		e.setCreatetime(new Date());
		databaseHelper.persistObject(e);
	}

	@Override
	public Map<String, Object> updateapp(HttpServletRequest request) throws Exception {
		Map<String, Object> map=readfile(request);
		if(map.get("op").toString().equals("success")){
			Edition e=new Edition();
			e.setUrl(map.get("url").toString());
			e.setState(-2);
			databaseHelper.persistObject(e);
			map.put("editionid", e.getId());
			return map;
		}else{
			throw new Exception("上传失败");
		}

		
		
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateedition(String editionid, String type, String editionno, String description) throws Exception {
		if(description.length()>200)throw new Exception("版本描述字段超过长度");
		//使其他版本失效
		String sql=" update edition set state =-1 where type= "+type+" and state !=-2 ";
		int count=databaseHelper.executeNativeSql(sql);
		if(count==0)throw new Exception("数据失效");
		Edition e=(Edition) databaseHelper.getObjectById(Edition.class, Long.parseLong(editionid));
		e.setState(0);
		e.setCreatetime(new Date());
		e.setDescription(description);
		e.setEditionno(editionno);
		e.setType(1);
		databaseHelper.updateObject(e);
	}

	
	public Map<String, Object>readfile (HttpServletRequest request){
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		
		String filename = req.getFile("filePath").getOriginalFilename();
		
		//String fix = filename.substring(filename.indexOf("."));
		Sys.out("...filename:"+filename);
		
	
		String filePath = "";
		Sys.out("ConstantUtil.environment:"+ConstantUtil.environment);
		if(ConstantUtil.environment.equals("maven")){
			//maven
		
			filePath = ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp/"+ConstantUtil.appfile+"/");//maven
		}
		else{//tomcat
			filePath = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", ConstantUtil.appfile+"/");//tomcat
			
		}
		  
		
           String savefilepath = filePath + filename;
           Map<String, Object> map =new HashMap<String, Object>();

           try {
        	   InputStream is = req.getFile("filePath").getInputStream();
        	   OutputStream bos = new FileOutputStream(savefilepath);//建立一个上传文件的输出流                    

               int bytesRead = 0;

               byte[] buffer = new byte[10*1024];

               while ( (bytesRead = is.read(buffer, 0, 10240)) != -1) {

                 bos.write(buffer, 0, bytesRead);//将文件写入服务器的硬盘上

              }

               bos.close();

               is.close();
			
		} catch (Exception e) {
			map.put("op", "fail");
			return map;
		}
          
         
           Sys.out("newfilename:"+filename);
           
           String url = "/"+ConstantUtil.appfile+"/"+filename;
           
           map.put("op", "success");
           map.put("url", url);
           return map;
        
	}

	@Override
	public Map<String, Object> queryloadurl() throws Exception {
		String hql=" select e from Edition e where  e.state= 0 order  by e.type  ";
		List<Object> lst= databaseHelper.getResultListByHql(hql);
		if(lst.size()==0)throw new Exception("版本维护");
		Edition e1=(Edition) lst.get(0);
		Edition e2=(Edition) lst.get(1);
		Map<String, Object> map =new HashMap<String, Object>();
		String filePath ="";
		if(ConstantUtil.environment.equals("maven")){
			filePath = ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp");//maven
		}
		else{
			filePath = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/","");//tomcat
			
		}
		map.put("androidurl", "http://zzl.runfkj.com"+e1.getUrl());
		map.put("appleurl", "http://zzl.runfkj.com"+e2.getUrl());
		map.put("editionno", e1.getEditionno());
		File file =new File(filePath+e1.getUrl());
		BigDecimal   b   =   new   BigDecimal((double)(file.length())/1024/1024);  
		double    size =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		map.put("size", size);
		return map;
	}

	
}
