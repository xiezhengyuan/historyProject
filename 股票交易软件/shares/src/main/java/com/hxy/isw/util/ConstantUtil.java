package com.hxy.isw.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.Setting;

import sun.misc.BASE64Decoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class ConstantUtil {

	public ConstantUtil() {
		super();
	}
	
	public static boolean TEXT = true;
	public static boolean SYSOUT = true; //是否开启控制台输出  system.out.print
	public static String PASSWORD;
	public static String URL;
	public static String USER_NAME;
	public static String DRIVER_CLASS_NAME;
	
	public static String PROJECT_PATH = null;

	public static Document DOM = null;
	
	public static Integer LIMIT = 10;//默认页容量  10条
	
	public static Integer RATE = 1;//RMB充值金币兑换比例，金币兑换RMB比例
	public static Integer VRATE = 100;//金币兑换虚拟资金比例
	
	public static String ACCESS_TOKEN = null;
	
	public static final String SERVERURL = "http://gp.runfkj.com";//服务器地址
	
	public static final String MOBILEURL = "http://mobilegp.runfkj.com";//手机页地址
	
	public static final String APPID = "wxbcbf9db020278f4f";//APPID
	
	public static final String SECRET = "24b813721f640acc1b1e4a4c83516b32";//SECRET
	
	public static final Long businessid = 1l;//润丰鞋服商户id
	
	public static final String wxurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri=http://x.runfkj.com/wx/bind_mobile.jsp?businessid="+businessid+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	
	
	public static final String mch_id = "1458843002";//微信商户ID
	
	public static final String key = "H38jkfhifh3849jdIeh94Hidhwudih1q";//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	
	
	public static final String TOKEN = "runfEngkj2017ToyS168O70l";
	
	public final static String notify_url = "http://gp.runfkj.com/appServiceUser/wxnotify.action"; //微信支付结果异步通知地址
	
	
	public static String environment;//项目运行环境
	
	public static final String filedir = "file";//文件存储路径，文件夹名
	
	public static final String [] imgfixarr = {".jpg",".png",".bpm",".jpeg"};//图片格式
	public static final String [] imgfixarr1 = {"jpg","png","bpm","jpeg"};//图片格式
	
	public static final String [] proxyname = {"非代理","特殊代理","银牌代理","金牌代理"};
	
	public static final String [] cashstate = {"未通过","新申请","已通过","已打款"};//-1 未通过 0 新申请 1已通过 2已打款 
	public static final String [] noticestate = {"所有人","所有用户 ","所有公司 ","指定公司"};
	public static final String [] noticestate1 = {"即时消息","定时消息 "};
	public static final String [] planstate = {"抢购中","运行中","已结束 "};
	public static final String [] type = {"沪深","美股","外汇 "};
	
	public static final int ROLE_ADMIN = 0;//平台管理员
	public static final int ROLE_SYS_OPERATION = 1;//平台运维
	public static final int ROLE_COMPANY_ADMIN = 2;//公司管理员
	public static final int ROLE_COMPANY_OPERATION = 3;//公司运维
	public static final int ROLE_COMPANY_MANAGER = 4;//公司经理
	public static final int ROLE_COMPANY_SALESMAN = 5;//公司业务员
	
	public static List<Map<String, String>> lm_index_hs = null;//沪深指数
	public static List<Map<String, String>> lm_index_us = null;//美股指数
	public static List<Map<String, String>> lm_index_wh = null;//外汇指数
	
	public static Setting setting = null;//系统设置
	
	public static final String appkey = "27090";//https://www.nowapi.com 美股appkey
	public static final String sign = "e980e495bafd40e9cc3a3b4417a0e325";//https://www.nowapi.com 美股sign
	
	public static String SIGNATURE;
	public static String NONCE_STR ;
	public static String TIMESTAMP;
	
	public static final String company_code = "8863";//第三方支付公司code
	public static final String company_md5 = "QNeYATOSPnHdNDHH";//第三方支付MD5
	
	private static final String [] usernamefilter = {"=","or","#","<",">"};//防止sql注入
	
	//用户名非法字符检查
	public static boolean checkUsername(String username){
    	boolean flag = true;
    	for (String filter : usernamefilter) {
			if(username.indexOf(filter)!=-1){
				flag = false;
				break;
			}
		}
    	return flag;
	}
	
	
	//计算数据的页数
	public static int pages(int records ,int limit){
		return (records/limit+(records%limit==0?0:1));
	}
	
	// base64字符串转化成图片
	public static String GenerateImage(String imgStr) throws Exception{ // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null)throw new Exception("图片参数错误");
		
		BASE64Decoder decoder = new BASE64Decoder();
		
		String fix = imgStr.split(",")[0].split(";")[0].split("/")[1].toLowerCase();
		Sys.out("GenerateImage...fix..."+fix);
		if(!ConstantUtil.checkimgformat(fix))throw new Exception("请上传.jpg、.png、.bpm、.jpeg格式的图片");
			
		
	// Base64解码
		byte[] b = decoder.decodeBuffer(imgStr.split(",")[1]);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
	// 生成jpeg图片
		String imgFilePath ="";
		if(ConstantUtil.environment.equals("tomcat"))
			imgFilePath=ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", ConstantUtil.filedir+"/");//tomcat
		else if(ConstantUtil.environment.equals("maven"))
			imgFilePath = ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp/"+ConstantUtil.filedir+"/");//maven
		long thistime = new Date().getTime();
		imgFilePath = imgFilePath+thistime+"."+fix;// 新生成的图片
		Sys.out("GenerateImage...filepath..."+imgFilePath);
		OutputStream out = new FileOutputStream(imgFilePath);
		out.write(b);
		out.flush();
		out.close();
		return "/"+ConstantUtil.filedir+"/"+thistime+"."+fix;
		
	}
	
	/**
	 * 将字符串写入Html文件
	 * @param path  文件路径
	 * @param filename 文件名
	 * @param allhtml 需要写入的字符串
	 * @throws Exception
	 */
	public static void writeStr2Html(String path,String filename,String allhtml) throws Exception{
		
		
    	 if (makeDir(path))
         {
             createFile(path, filename);
         }
         
    	 StringBuilder sb = new StringBuilder(); 
    	 
    	 OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(path + File.separator + filename),"UTF-8");
    	 
    	 BufferedWriter writer=new BufferedWriter(write);   
    	 
    	 writer.write(allhtml);
    	 writer.close();
	}
	
	private static boolean makeDir(String path)
    {
        boolean mk = true;
        File myPath = new File(path);
        if (!myPath.exists())
        {
            
            mk = myPath.mkdirs();
            
        }
        return mk;
    }
	
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
	     * 获取服务器IP地址
	     * @return
	     */
	    @SuppressWarnings("unchecked")
	    public static String  getServerIp(){
	        String SERVER_IP = null;
	        try {
	            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
	            InetAddress ip = null;
	            while (netInterfaces.hasMoreElements()) {
	                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
	                ip = (InetAddress) ni.getInetAddresses().nextElement();
	                SERVER_IP = ip.getHostAddress();
	                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
	                        && ip.getHostAddress().indexOf(":") == -1) {
	                    SERVER_IP = ip.getHostAddress();
	                    break;
	                } else {
	                    ip = null;
	                }
	            }
	        } catch (SocketException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    
	        return SERVER_IP;
	    }
	    
	    
	    private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";    
	    /** 
	     * 获取任意位的随机字符串(0-9 a-z A-Z) 
	     * @param size 位数 
	     * @return 
	     */  
	    public static final String getRandomNum(int size){  
	     StringBuffer sb = new StringBuffer();    
	     Random random = new Random();  
	     for (int i = 0; i < size; i++) {    
	         sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));    
	     }  
	     return sb.toString();  
	    }  
	    
	    
	    /**
		 * 将字节数组转换为十六进制字符串
		 *
		 * @param byteArray
		 * @return
		 */
		public static String byteToStr(byte[] byteArray) {
			String strDigest = "";
			for (int i = 0; i < byteArray.length; i++) {
				strDigest += byteToHexStr(byteArray[i]);
			}
			return strDigest;
		}

		/**
		 * 将字节转换为十六进制字符串
		 *
		 * @param mByte
		 * @return
		 */
		public static String byteToHexStr(byte mByte) {
			char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
					'B', 'C', 'D', 'E', 'F' };
			char[] tempArr = new char[2];
			tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
			tempArr[1] = Digit[mByte & 0X0F];
			String s = new String(tempArr);
			return s;
		}
	    
	    public synchronized static final String getMD5Str(String str,String charSet) { //md5加密  
	        MessageDigest messageDigest = null;    
	        try {    
	            messageDigest = MessageDigest.getInstance("MD5");    
	            messageDigest.reset();   
	            if(charSet==null){  
	                messageDigest.update(str.getBytes());  
	            }else{  
	                messageDigest.update(str.getBytes(charSet));    
	            }             
	        } catch (Exception e) {    
	            //log.error("md5 error:"+e.getMessage(),e);  
	        } 
	          
	        byte[] byteArray = messageDigest.digest();    
	        StringBuffer md5StrBuff = new StringBuffer();    
	        for (int i = 0; i < byteArray.length; i++) {                
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)    
	                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));    
	            else    
	                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));    
	        }    
	        return md5StrBuff.toString();    
	    }  
	    
	    
	    /**
	     * 删除单个文件
	     * 
	     * @param sPath
	     *            被删除文件的路径+文件名
	     * @return 单个文件删除成功返回true，否则返回false
	     */
	    public static boolean deleteFile(String sPath) {
	        Boolean flag = false;
	        File file = new File(sPath);
	        Sys.out("sPath:"+sPath);
	        // 路径为文件且不为空则进行删除
	        if (file.isFile() && file.exists()) {
	            file.delete();
	            flag = true;
	        }
	        Sys.out("del.file.:"+flag);
	        return flag;
	    }
	    
	    public static long setThreadStartTimer(String hour,String min,String secd){
	    	long diff = 0l;
	    	hour = hour==null?"00":hour;
	    	min = min==null?"00":min;
	    	secd = secd==null?"00":secd;
	    	try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String day = sdf.format(now).split(" ")[0];
				String threadtime = day+" "+hour+":"+min+":"+secd;
				
				diff = now.getTime()-sdf.parse(threadtime).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	Sys.out(diff);
	    	return diff;
	    }
	    
	    /**
	     * 检查图片是否符合格式要求
	     * @return
	     */
	    public static boolean checkimgformat(String fix){
	    	boolean flag = false;
	    	for (String imgfix : imgfixarr1) {
				if(imgfix.equals(fix)){
					flag = true;
					break;
				}
			}
	    	return flag;
	    }
	    
	    public static boolean checkimgformat1(String fix){
	    	boolean flag = false;
	    	for (String imgfix : imgfixarr) {
				if(imgfix.equals(fix)){
					flag = true;
					break;
				}
			}
	    	return flag;
	    }
	    
	    
	  //EXCEL单元格格式化
		public static String getCellFormatValue(HSSFCell cell) {

	        String cellvalue = "";
	        if (cell != null) {
	            switch (cell.getCellType()) {
	            case HSSFCell.CELL_TYPE_NUMERIC:
	            case HSSFCell.CELL_TYPE_FORMULA: {
	                if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                    
	                    Date date = cell.getDateCellValue();
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	                    cellvalue = sdf.format(date);
	                    
	                }
	                else {
	                	DecimalFormat df = new DecimalFormat("#");
	                    cellvalue = df.format(cell.getNumericCellValue());
	                }
	                break;
	            }
	            case HSSFCell.CELL_TYPE_STRING:
	                cellvalue = cell.getRichStringCellValue().getString();
	                break;
	            default:
	                cellvalue = " ";
	            }
	        } else {
	            cellvalue = "";
	        }
	        

	        return cellvalue;

	    }
		
		public static Map<String, String> getwxopenid(String code,String state){
			Map<String, String> map = new HashMap<String, String>();
			if(code!=null&&state!=null){
	    	 	String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ConstantUtil.APPID+"&secret="+ConstantUtil.SECRET+"&code="+code+"&grant_type=authorization_code";
	    	 	String result =  HttpConnectionUtil.get(url);
	    	 	Sys.out(result);
	    	 	JsonObject json = (JsonObject) new JsonParser().parse(result);
	    	 	if(json.get("errcode")==null){
	    	 		String openid = json.get("openid").getAsString();
	    	 		Sys.out("openid:"+openid);
	    	 		url = "https://api.weixin.qq.com/sns/userinfo?access_token="+json.get("access_token").getAsString()+"&openid="+openid+"&lang=zh_CN";
	    	 		String msg = HttpConnectionUtil.get(url);
	    	 		Sys.out(msg);
	    	 		JsonObject jsonmsg = (JsonObject) new JsonParser().parse(msg);
	    	 		if(jsonmsg.get("errcode")==null){
	    	 			 openid = jsonmsg.get("openid").getAsString();
	   	 				 String nickname =   jsonmsg.get("nickname").getAsString();
	   	 				 int sex = Integer.parseInt(jsonmsg.get("sex").getAsString());
	   	 				 String city =   jsonmsg.get("city").getAsString();
	   	 				 String province =   jsonmsg.get("province").getAsString();
	   	 				 String country =   jsonmsg.get("country").getAsString();
	   	 				 String headimgurl =   jsonmsg.get("headimgurl").getAsString();
	   	 				 
	   	 				 map.put("nickname",nickname);
	   	 				 map.put("sex",jsonmsg.get("sex").getAsString());
	   	 				 map.put("city",city);
	   	 				 map.put("province",province);
	   	 				 map.put("country",country);
	   	 				 map.put("headimgurl",headimgurl);
	    	 				 
	   	 				 map.put("openid", openid);
	   	 				 map.put("msg", "success");
	    	 		}else{
	    	 			 map.put("msg", "fail");
	    	 		}
	    	 		
	    	 	}else{
	    	 		 map.put("msg", "fail");
	    	 	}
	    	 }else{
	    		 map.put("msg", "fail");
	    	 }
			
			return map;
		}
		
		
		public static String filterEmoji(String source) {
		 	 
		    if (!containsEmoji(source)) {
		        return source;//如果不包含，直接返回
		    }
		    //到这里铁定包含
		        StringBuilder buf = null;
		 
		        int len = source.length();
		 
		        for (int i = 0; i < len; i++) {
		            char codePoint = source.charAt(i);
		 
		            if (isEmojiCharacter(codePoint)) {
		                if (buf == null) {
		                    buf = new StringBuilder(source.length());
		                }
		 
		                buf.append(codePoint);
		            } else {
		            }
		        }
		 
		        if (buf == null) {
		            return source;//如果没有找到 emoji表情，则返回源字符串
		    } else {
		        if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
		            buf = null;
		            return source;
		        } else {
		            return buf.toString();
		        }
		    }
				 	 
		}
		
		public static  boolean containsEmoji(String source) {
		        if (StringUtils.isBlank(source)) {
		            return false;
		        }
		 
		        int len = source.length();
		 
	        for (int i = 0; i < len; i++) {
	            char codePoint = source.charAt(i);
		 
		            if (isEmojiCharacter(codePoint)) {
		                //do nothing，判断到了这里表明，确认有表情字符
		                return true;
		            }
		        }
		 
		        return false;
		    }

		private static boolean isEmojiCharacter(char codePoint) {
		        return (codePoint == 0x0) ||
		                (codePoint == 0x9) ||
		                (codePoint == 0xA) ||
		                (codePoint == 0xD) ||
		                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
		                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
		                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
		
		
		public static String getSha1(String str){
		    if (null == str || 0 == str.length()){
		        return null;
		    }
		    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		            'a', 'b', 'c', 'd', 'e', 'f'};
		    try {
		        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
		        mdTemp.update(str.getBytes("UTF-8"));
		         
		        byte[] md = mdTemp.digest();
		        int j = md.length;
		        char[] buf = new char[j * 2];
		        int k = 0;
		        for (int i = 0; i < j; i++) {
		            byte byte0 = md[i];
		            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
		            buf[k++] = hexDigits[byte0 & 0xf];
		        }
		        return new String(buf);
		    } catch (Exception e) {
		        e.printStackTrace();
		    } 
		    return null;
		}
		
		  public static double formatDouble(double d) {
		        // 旧方法，已经不再推荐使用
//		        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);

		        
		        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
		        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);

		        
		        return bg.doubleValue();
		    }
		
		  public static double formatdouble(double d) {
		      
		        return (int)d;
		    }
		  
		/*public static void main(String[] args) {
			String s = "jsapi_ticket=kgt8ON7yVITDhtdwci0qebaGFG8yBB2-e-kb3yh3vB2PBhGCSoxZ1y_R1GnbSOn5aiubIYouFhNk37azE-bllg&noncestr=f7af8ca2-c712-42ec-90c7-7f88ba7fbf4a&timestamp=1498185020&url=http://x.runfkj.com/wx/shopping_mall.jsp?userid=15";
			
			Sys.out(SHA1Utils.hex_sha1(s));
			Sys.out(getSha1(s));
			String spreadurl= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConstantUtil.APPID+"&redirect_uri="+ConstantUtil.MOBILEURL+"?finvateid=1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			try {
				genEncode(spreadurl, "d:\\");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		/** 
	     * 大陆号码或香港号码均可 
	     */  
	    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {  
	        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);  
	    }  
	  
	    /** 
	     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
	     * 此方法中前三位格式有： 
	     * 13+任意数 
	     * 15+除4的任意数 
	     * 18+除1和4的任意数 
	     * 17+除9的任意数 
	     * 147 
	     */  
	    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
	        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";  
	        Pattern p = Pattern.compile(regExp);  
	        Matcher m = p.matcher(str);  
	        return m.matches();  
	    }  
	  
	    /** 
	     * 香港手机号码8位数，5|6|8|9开头+7位任意数 
	     */  
	    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {  
	        String regExp = "^(5|6|8|9)\\d{7}$";  
	        Pattern p = Pattern.compile(regExp);  
	        Matcher m = p.matcher(str);  
	        return m.matches();  
	    }
		
		
		/**
		   * 生成随机6位数
		   */
		  public static String gencode(){
				StringBuffer code = new StringBuffer();
				Random rd = new Random();
				
				for (int i = 0; i < 6; i++) {
					code.append(rd.nextInt(10));
				}
				return code.toString();
			}
		  
		  
		  private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
			 //云片短信
			 public static boolean yunpian(String content,String mobile) {
				 boolean flag = false;
				 
				 String apikey = "128847734f480428ff20e9386e67bd64";
				 Map<String, String> params = new HashMap<String, String>();
			        params.put("apikey", apikey);
			        params.put("text", content);
			        params.put("mobile", mobile);
			        String result = post(URI_SEND_SMS, params);

			        JsonObject jo = JsonUtil.getJsonParser().parse(result).getAsJsonObject();
			        
			        if(jo.get("code")!=null&&"0".equals(jo.get("code").getAsString())&&"发送成功".equals(jo.get("msg").getAsString()))flag = true;
			        
			        return flag;
			 }
			 
			 //创蓝短信
			 public static boolean zz253(String content,String mobile){
				 boolean flag = false;
					
					// 普通短信地址
					String smsSingleRequestServerUrl = "http://vsms.253.com/msg/send/json";
					// 短信内容
				    String msg = content;
					//手机号码
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("account", "N0365341");
					map.put("password", "CSebsyQKwzc9bc");
					map.put("msg", msg);
					map.put("phone", mobile);
					
					String p = new Gson().toJson(map);
					//String result =  HttpRequest.sendPost(smsSingleRequestServerUrl, p);
					
					String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, p);
					
					Sys.out("response after request result is :" + response);
				
					JsonObject jo = JsonUtil.getJsonParser().parse(response).getAsJsonObject();
				
					if(jo.get("code")!=null&&"0".equals(jo.get("code").getAsString()))flag = true;
					
					return flag;
			 }
			 
			 //联合力量
			 public static boolean smsLHLL(String content, String mobile){
				 String url = "http://sms.szlhll.com/sms.aspx";
				 String userid = "893";
				 String account = "xg196";
				 String password = "12345678";
				 String result = SmsClientSend.sendSms(url, userid, account, password, mobile, content);
				 result = result.replace("<?xml version=\"1.0\" encoding=\"utf-8\" ?>", "");
				result=result.replace(" ", "");
				result=result.replace("\r\n", "");
		    	Sys.out(result);
		    	Map<String,String> map = ConstantUtil.parse(result);
		    	boolean flag = false;
		    	if(map.get("returnstatus").equals("Success")&&map.get("message").equals("ok")){
		    		Sys.out("ok..............");
		    		flag = true;
		    	}else{
		    		Sys.out("fail............");
		    		flag = false;
		    	}
		    	return flag;
			 }
			 public static void main(String[] args) {
				 String content = "【FX方程式】验证码为677983。请勿泄露。";
				 String mobile = "15071438685";
				 smsLHLL(content, mobile);
			}
			 
			 
			 
			 private static String ENCODING = "UTF-8";
			 public static String post(String url, Map<String, String> paramsMap) {
			        CloseableHttpClient client = HttpClients.createDefault();
			        String responseText = "";
			        CloseableHttpResponse response = null;
			            try {
			                HttpPost method = new HttpPost(url);
			                if (paramsMap != null) {
			                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			                    for (Map.Entry<String, String> param : paramsMap.entrySet()) {
			                        NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
			                        paramList.add(pair);
			                    }
			                    method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			                }
			                response = client.execute(method);
			                HttpEntity entity = response.getEntity();
			                if (entity != null) {
			                    responseText = EntityUtils.toString(entity, ENCODING);
			                }
			            } catch (Exception e) {
			                e.printStackTrace();
			            } finally {
			                try {
			                    response.close();
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }
			            }
			            return responseText;
			        }
			 
			 
			//计算两个日期相差天数
			    public static int differentDays(Date date1,Date date2){
					 Calendar cal1 = Calendar.getInstance();
					 cal1.setTime(date1);
					  
					 Calendar cal2 = Calendar.getInstance();
					 cal2.setTime(date2);
					 int day1= cal1.get(Calendar.DAY_OF_YEAR);
					 int day2 = cal2.get(Calendar.DAY_OF_YEAR);
					  
					 int year1 = cal1.get(Calendar.YEAR);
					 int year2 = cal2.get(Calendar.YEAR);
					 if(year1 != year2){ //不同年
						 int timeDistance = 0 ;
						 for(int i = year1 ; i < year2 ; i ++){
							 if(i%4==0 && i%100!=0 || i%400==0){ //闰年  
					  
								 timeDistance += 366;
							 }else {//不是闰年
								 timeDistance += 365;
							 }
						 }
					   
						 return timeDistance + (day2-day1) ;
					 }else{ //同年
						 Sys.out("判断day2 - day1 : " + (day2-day1));
						 return day2-day1;
					 }
				 }
			    
			    /*
			     * int相除保留两位
			     * numerator 分子
			     * denominator 分母
			     * rate 是否百分比
			     */
			    public static Double intdevice2(int numerator,int denominator,boolean rate){
			    	if(denominator==0)return 0d;
			    	
			    	if(rate)
			    		return new BigDecimal((float)numerator/denominator*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			    	else
			    		return new BigDecimal((float)numerator/denominator).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			    }
			    
			    
			    /** 
			     * 两个时间相差距离多少天多少小时多少分多少秒 
			     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
			     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
			     * @return String 返回值为：xx天xx小时xx分xx秒 
			     */  
			    public static String getDistanceTime(Date d1, Date d2) {  
			        long day = 0;  
			        long hour = 0;  
			        long min = 0;  
			        long sec = 0;  
			       
		            long time1 = d1.getTime();  
		            long time2 = d2.getTime();  
		            long diff ;  
		            if(time1<time2) {  
		                diff = time2 - time1;  
		            } else {  
		                diff = time1 - time2;  
		            }  
		            day = diff / (24 * 60 * 60 * 1000);  
		            hour = (diff / (60 * 60 * 1000) - day * 24);  
		            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
		            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
			       
			        return day + "天" + hour + "小时" + min + "分" + sec + "秒";  
			    }  
			    

			
			
			        public static String showTime(Long time) {
			           // Calendar now = Calendar.getInstance();
			            Date date=new Date();
			          //  Long diff = now.getTimeInMillis() - time;
			            Long diff = date.getTime() - time;
			            Long month = 0L; Long week = 0L; Long day = 0L; Long hour = 0L; Long min = 0L;

			            month = diff / (30 * 24 * 60 * 60 * 1000L);
			            week = diff / (7 * 24 * 60 * 60 * 1000L);
			            day = diff / (24 * 60 * 60 * 1000L);
			            hour = diff / (60 * 60 * 1000L);
			            min = diff / (60 * 1000L);

			            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			            
			            if(month > 0){
			               /* return month+"月前";*/
			            	return sdf.format(new Date(time));
			            }else if(week > 0){
			                /*return week+"周前";*/
			            	return sdf.format(new Date(time));
			            }else if(day > 0){
			                return day+"天前";
			            }else if(hour > 0){
			                return hour+"小时前";
			            }else if(min > 0){
			                return min+"分钟前";
			            }else{
			                return "1分钟前";
			            }

			        }
			        //格林威治时间取到分为止
			        public static String getMinute(String oldTime){
			            String newTime=oldTime.substring(0,oldTime.lastIndexOf(":"));
			            return newTime;
			        }

			        public static String getSecond(String oldTime){
			            String newTime=oldTime.substring(0,oldTime.lastIndexOf("."));
			            return newTime;
			        }

			        public static String getIp(HttpServletRequest request){
				    	String ip = request.getHeader("x-forwarded-for"); 
			    	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    	         ip = request.getHeader("Proxy-Client-IP"); 
			    	    } 
			    	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    	         ip = request.getHeader("WL-Proxy-Client-IP"); 
			    	    } 
			    	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			    	          ip = request.getRemoteAddr(); 
			    	    }
			    	    
			    	    if(ip.indexOf(",")!=-1){
			    	    	ip = ip.split(",")[0];
			    	    }
			    	    
			    	    return ip;
				    }
				  
				  
				  public static String create_nonce_str() {
						
						String str = "";
						
						for (int i = 0; i < 32; i++) {
							Random r = new Random();
							int num = r.nextInt(61);
							if( num>=0 && num <=9 ){
								str += num;
							}else if( num>9 && num <=35 ){
								num+=55;
								str += (char)num;
							}else if( num>35 && num <=61 ){
								num+=61;
								str += (char)num;
							}
						}
						return str;
				    }
				  
				  public static String create_timestamp() {
				        return Long.toString(System.currentTimeMillis() / 1000);
				    }
				  
				  
				  public static Map<String,String> parse(String protocolXML) {  
					    
						 Map<String,String> map = new HashMap<String,String>();
					       try {  
					            DocumentBuilderFactory factory = DocumentBuilderFactory  
					                    .newInstance();  
					            DocumentBuilder builder = factory.newDocumentBuilder();  
					            Document doc = builder  
					                    .parse(new InputSource(new StringReader(protocolXML)));  
					
					            Element root = doc.getDocumentElement();  
					            NodeList books = root.getChildNodes();  
					           if (books != null) {  
					               for (int i = 0; i < books.getLength(); i++) {  
					                    Node book = books.item(i);  
					                    Sys.out("document=" + book.getNodeName() + "\ttext=" 
					                            + book.getFirstChild().getNodeValue());  
					                    map.put(book.getNodeName(), book.getFirstChild().getNodeValue());
					                }  
					            }  
					        } catch (Exception e) {  
					            e.printStackTrace();  
					        }  
					       return map;
					    }   
			    
				/**
				 * 生成二维码  
				 * @param eqcodeurl 需要被生成二维码的url链接
				 * @param filePath 生成二维码的图片存放在服务器文件目录
				 * @return 二维码图片地址
				 */
				  public static String genEncode(String eqcodeurl,String filePath) throws Exception{  
				        
							
							String fileName = "zxing_"+new Date().getTime()+".png";  
		    
							String content = eqcodeurl;
							int width = 300; // 图像宽度  
							int height = 300; // 图像高度  
							String format = "png";// 图像类型  
							Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
							hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
							hints.put(EncodeHintType.MARGIN, 1);
							BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
							BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
							Path path = FileSystems.getDefault().getPath(filePath, fileName);  
							Sys.out("erqcode..path:"+path);
							MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
							Sys.out("erqcode.success"); 
							return "/qrcode/"+fileName;
						
				    }
				  
				  
				  
				  public static boolean broadcast(String relationId, Long userCode, String message) {
						 if (SessionUtils.hasConnection(relationId, userCode)) {
						 SessionUtils.get(relationId, userCode).getAsyncRemote().sendText(message);
						 	return true;
						 } else {
						 //throw new NullPointerException(SessionUtils.getKey(relationId, userCode) +"Connection does not exist");
							 return false;
						}
					}
				  
				  
				  /**
				     *  利用java原生的摘要实现SHA256加密
				     * @param str 加密后的报文
				     * @return
				     */
				    public static String getSHA256StrJava(String str){
				        MessageDigest messageDigest;
				        String encodeStr = "";
				        try {
				            messageDigest = MessageDigest.getInstance("SHA-256");
				            messageDigest.update(str.getBytes("UTF-8"));
				            encodeStr = byte2Hex(messageDigest.digest());
				        } catch (NoSuchAlgorithmException e) {
				            e.printStackTrace();
				        } catch (UnsupportedEncodingException e) {
				            e.printStackTrace();
				        }
				        return encodeStr;
				    }

				    /**
				     * 将byte转为16进制
				     * @param bytes
				     * @return
				     */
				    private static String byte2Hex(byte[] bytes){
				        StringBuffer stringBuffer = new StringBuffer();
				        String temp = null;
				        for (int i=0;i<bytes.length;i++){
				            temp = Integer.toHexString(bytes[i] & 0xFF);
				            if (temp.length()==1){
				                //1得到一位的进行补0操作
				                stringBuffer.append("0");
				            }
				            stringBuffer.append(temp);
				        }
				        return stringBuffer.toString();
				    }
}
