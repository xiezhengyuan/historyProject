package com.hxy.isw.control;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.hxy.isw.entity.FileInfo;
import com.hxy.isw.service.StaticService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Controller
@RequestMapping("/static")
public class StaticControl {
	
	@Autowired
	StaticService staticService;
	
	@RequestMapping(value = "/upload")
	public synchronized void upload(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		JsonObject json = new JsonObject(); //返回的json串
        
    	try{
        request.setCharacterEncoding("utf-8"); //设置编码
        Sys.out("开始访问servlet（dopost） ：");
        
        String sourcename = "";
        String filename = ""; //文件名称
        long size = 0;
        String path = request.getRealPath("/"+ConstantUtil.filedir); //获取文件需要上传到的路径
        
            List<FileItem> items = new ServletFileUpload(
                    new DiskFileItemFactory()).parseRequest(request);
            Sys.out("items.size:"+items.size());
            for (FileItem item : items)
            {
                if (item.isFormField())
                {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                    String fieldname = item.getFieldName();
                    String fieldvalue = "";
                    if (fieldname.equals("name"))
                    {
                        filename = fieldvalue = URLDecoder.decode(item.getString(),
                                "UTF-8");
                        
                    }
                    else
                    {
                        fieldvalue = item.getString();
                    }
                    
                    Sys.out("fieldname:" + fieldname
                            + "--fieldvalue:" + fieldvalue);
                    // ... (do your job here)
                    sourcename = filename;
                }
                else
                {
                    // Process form file field (input type="file").
                    String fieldname = item.getFieldName();
                    //String filename = FilenameUtils.getName(item.getName());
                    InputStream filecontent = item.getInputStream();
                    Sys.out("fieldname:" + fieldname + "--filename:"
                            + filename + "---filecontent:" + filecontent
                            + "---path:" + path);
                    // item.write( new File(path,filename) );//第三方提供的;
                    filename = new Date().getTime() + fieldname.substring(fieldname.lastIndexOf("."));
                    //手动写入硬盘
                    if (makeDir(path))
                    {
                        createFile(path, filename);
                    }
                    
                    File file = new File(path + File.separator + filename);
                    FileOutputStream fos = new FileOutputStream(file, true);
                    InputStream is = item.getInputStream();
                    IOUtils.copy(is, fos);
                    is.close();
                    fos.close();
                    size = item.getSize();
                    Sys.out("获取上传文件的总共的容量：" + item.getSize());
                }
            }
        
            String url = "/"+ConstantUtil.filedir+"/"+filename;
            FileInfo fi = new FileInfo();
            fi.setFilename(filename);
            fi.setUrl(url);
            
            Long fileId = staticService.savefileinfo(fi);
     
	    	json.addProperty("succ", true);
	    	json.addProperty("size", size);
	    	json.addProperty("url", url);
	    	json.addProperty("fileId", fileId);
	    	json.addProperty("sourcename", sourcename);
	    	
	    	Sys.out("post返回json数据:" + json);
	    	
	    	JsonUtil.success2page(response, json.toString());
    } catch (Exception e)
        {
    		json.addProperty("succ", false);
    		JsonUtil.success2page(response, json.toString());
            e.printStackTrace();
        }
        
	}
	
	  /** 创建文件
     * <功能详细描述>
     * @param string
     * @param string2 [参数说明]
     * @return 
     * 
     * @return void [返回类型说明]
     * @throws IOException 
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private static boolean createFile(String path, String fileName)
            throws IOException
    {
        boolean creator = true;
        File myPath = new File(path, fileName);
        if (!myPath.exists())
        {
            creator = myPath.createNewFile();
        }
        return creator;
        
    }
    
    /**
     * 创建目录
     * <功能详细描述>
     * @param path
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean makeDir(String path)
    {
        boolean mk = true;
        File myPath = new File(path);
        if (!myPath.exists())
        {
            
            mk = myPath.mkdirs();
            
        }
        return mk;
    }
    
    /**
     * 上传图片
     * @param request
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/upfileimage")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void upfileimage(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		/*Employee emp = (Employee)session.getAttribute("loginEmp");
		if(emp==null){
			JsonUtil.timeout2page(response);
			return;
		}*/
		
		//String pt = request.getParameter("path");
		//String id = request.getParameter("id");
		
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		InputStream is = req.getFile("filePath").getInputStream();
		String filename = req.getFile("filePath").getOriginalFilename();
		
		//String fix = filename.substring(filename.indexOf("."));
		Sys.out("...filename:"+filename);
		
		String result = "";
		String filesuffix = filename.substring(filename.indexOf("."));
		// if(!filesuffix.toLowerCase().equals(".jpg")&&!filesuffix.toLowerCase().equals(".png")&&!filesuffix.toLowerCase().equals(".bpm")&&!filesuffix.toLowerCase().equals(".jpeg")){
		if(!ConstantUtil.checkimgformat1(filesuffix.toLowerCase())){
			 result = "{\"op\":\"fail\",\"msg\":\"请上传.jpg、.png、.bpm、.jpeg格式的图片\"}";
			 //result = "fail,请上传.jpg、.png、.bpm、.jpeg格式的图片";
			 JsonUtil.success2page(response, result);
			 return;
		 }
		
		String filePath = "";
		Sys.out("ConstantUtil.environment:"+ConstantUtil.environment);
		if(ConstantUtil.environment.equals("maven"))//maven
			filePath = ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp/"+ConstantUtil.filedir+"/");//maven
			
		else//tomcat
			filePath = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", ConstantUtil.filedir+"/");//tomcat
		
		
		   String newfilename = new Date().getTime()+filesuffix;
		
           String savefilepath = filePath + newfilename;
			
           OutputStream bos = new FileOutputStream(savefilepath);//建立一个上传文件的输出流                    

           int bytesRead = 0;

           byte[] buffer = new byte[10*1024];

           while ( (bytesRead = is.read(buffer, 0, 10240)) != -1) {

             bos.write(buffer, 0, bytesRead);//将文件写入服务器的硬盘上

          }

           bos.close();

           is.close();
           Sys.out("resourcefilename:"+newfilename);
           //newfilename = new Date().getTime()+filesuffix;
           //resizeImage(savefilepath, filePath + newfilename, 500, 500);
           Sys.out("newfilename:"+newfilename);
           
           String url = "/"+ConstantUtil.filedir+"/"+newfilename;
           FileInfo fi = new FileInfo();
           fi.setFilename(newfilename);
           fi.setUrl(url);
           
           Long fileId = staticService.savefileinfo(fi);
           
           result = "{\"op\":\"success\",\"msg\":\"上传成功\",\"url\":\""+("/"+ ConstantUtil.filedir+"/"+newfilename)+"\",\"fileid\":\""+fileId+"\",\"name\":\""+newfilename+"\"}";
           //result = "success,上传成功,"+("/"+ ConstantUtil.filedir+"/"+newfilename)+","+fileId+","+newfilename;
           JsonUtil.success2page(response, result);
	}
    
    /**
     * 压缩图片
     * @param srcImgPath
     * @param distImgPath
     * @param width
     * @param height
     * @throws IOException
     */
    public static void resizeImage(String srcImgPath, String distImgPath,int width, int height) throws IOException {  

        String subfix = "jpg";

        subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1,srcImgPath.length());

         

        File srcFile = new File(srcImgPath);  

        Image srcImg = ImageIO.read(srcFile);  

        int _width = srcImg.getWidth(null);    // 得到源图宽  
        int _height = srcImg.getHeight(null);  // 得到源图长  

        if (_width / _height > width / height) {  
        	height = (int) (_height * width / _width);   
        } else {  
        	width = (int) (_width * height / _height);  
        }  
        
        BufferedImage buffImg = null; 

        if(subfix.equals("png")){

            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        }else{

            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        }

     

        Graphics2D graphics = buffImg.createGraphics();

        graphics.setBackground(Color.WHITE);

        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, width, height);

        graphics.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);  

 

        ImageIO.write(buffImg, subfix, new File(distImgPath));  

    }  
    
    /**
     * 根据ID查对象信息
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST, value = "/general")
	public void general(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String tableName = request.getParameter("tableName");
			String id = request.getParameter("id");
			Class tableClass = Class.forName("com.hxy.isw.entity."+tableName);
			Object obj= staticService.general(tableClass,Long.parseLong(id));
			JsonUtil.objToJson(obj, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    
    
}
