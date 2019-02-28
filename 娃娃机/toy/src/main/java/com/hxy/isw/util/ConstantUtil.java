package com.hxy.isw.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sun.misc.BASE64Decoder;

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
	
	public static String ACCESS_TOKEN = null;
	public static String HX_TOKEN = null;//环信token
	
	public static final String APPID = "wx053cb71660fc6e61";//微信开放平台APPID;
	
	public static final String SECRET = "1d054c8ce2d1af14fab99006614d379f";//微信开放平台secret
	
	public static final String mch_id = "1488567872";//微信商户ID
	
	public static final String key = "H38jkfhifh3849jdIeh94Hidhwudih1q";//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	
	public static String environment;//项目运行环境
	
	public static final String filedir = "file";//文件存储路径，文件夹名
	
	public static final String appfile = "app";//文件存储路径，文件夹名
	
	public static final String [] imgfixarr = {".jpg",".png",".bpm",".jpeg"};//图片格式
	
	public static final String [] proxyname = {"非代理","特殊代理","银牌代理","金牌代理"};
	
	public static final String [] cashstate = {"未通过","新申请","已通过","已打款"};//-1 未通过 0 新申请 1已通过 2已打款 
	
	public static final String TOKEN = "runfEngkj2017ToyS168O70l";
	
	public final static String server_url = "http://zzl.runfkj.com";//服务器地址
	public final static String notify_url = "http://zzl.runfkj.com/appServicePay/wxnotify.action"; //微信支付结果异步通知地址
	
	public static String SIGNATURE;
	public static String NONCE_STR ;
	public static String TIMESTAMP;
	
	public final static String org_name = "1161170710178688";//环信企业ID
	public final static String app_name = "toys";//环信企业应用名称
	public final static String client_id = "YXA6-n2V8GUhEeeDw6myJakYCQ";//环信
	public final static String client_secret = "YXA68eYMYUiSQYMmA2GLW0yi47r-1RY";//环信
	
	public final static int countdownofstartplay = 15;//预约到号后开始游戏倒计时15秒
	public final static int countdownofresset = 8;//爪子复位需要8秒
	public final static int countdownofrestartplay = 10;//结束一局后再次开始游戏倒计时10秒
	public final static int gametime = 25;//游戏时间25秒
	
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
		
		public static void main(String[] args) {
			/*String s = "jsapi_ticket=kgt8ON7yVITDhtdwci0qebaGFG8yBB2-e-kb3yh3vB2PBhGCSoxZ1y_R1GnbSOn5aiubIYouFhNk37azE-bllg&noncestr=f7af8ca2-c712-42ec-90c7-7f88ba7fbf4a&timestamp=1498185020&url=http://x.runfkj.com/wx/shopping_mall.jsp?userid=15";
			
			Sys.out(SHA1Utils.hex_sha1(s));
			Sys.out(getSha1(s));*/
			String path = "e:/test-base64.txt";
			
			
			File file = new File(path );
			Sys.out("before if ......"+path );
			String s = "";
			try {
				if (file.exists()) {
					
					Sys.out("after if ......"+path);
					BufferedReader b = new BufferedReader(new FileReader(file));
					 String ss= "";	
						do {

							s += ss.trim();
							

						} while ((ss = b.readLine())!= null);
						Sys.out("s............."+s);
				} 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String imgStr = s;
			
				BASE64Decoder decoder = new BASE64Decoder();
				
				try {
				// Base64解码
					imgStr = imgStr.replaceAll(" ", "+");
					System.out.println("file.type:::::::::::"+imgStr);
					byte[] b = decoder.decodeBuffer(imgStr);
					for (int i = 0; i < b.length; ++i) {
						if (b[i] < 0) {// 调整异常数据
							b[i] += 256;
						}
					}
				// 生成jpeg图片
					String imgFilePath ="e:/";
					
					long thistime = new Date().getTime();
					imgFilePath = imgFilePath+thistime+".jpeg";// 新生成的图片
					System.out.println(imgFilePath);
					OutputStream out = new FileOutputStream(imgFilePath);
					out.write(b);
					out.flush();
					out.close();
					//return "/file/"+thistime+".jpeg";
				} catch (Exception e) {
					e.printStackTrace();
					//return null;
				}
		
		}
		
		 public static boolean checkSignature(String signature, String timestamp, String nonce) {
			 
			    String[] arr = { TOKEN, timestamp, nonce };

			    Arrays.sort(arr);
			    StringBuilder content = new StringBuilder();
			    for (int i = 0; i < arr.length; i++) {
			      content.append(arr[i]);
			    }
			    //Sys.out(content.toString());
			    MessageDigest md = null;
			    String tmpStr = null;
			    try
			    {
			      md = MessageDigest.getInstance("SHA-1");

			      byte[] digest = md.digest(content.toString().getBytes());
			      tmpStr = byteToStr(digest);
			    } catch (NoSuchAlgorithmException e) {
			      e.printStackTrace();
			    }

			    content = null;
			    //Sys.out(tmpStr);
			    return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
			  }
				public static String byteToStr(byte[] byteArray) {
				    String strDigest = "";
				    for (int i = 0; i < byteArray.length; i++) {
				      strDigest = strDigest + byteToHexStr(byteArray[i]);
				    }
				    return strDigest;
				  }

				  public static String byteToHexStr(byte mByte) {
				    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
				    char[] tempArr = new char[2];
				    tempArr[0] = Digit[(mByte >>> 4 & 0xF)];
				    tempArr[1] = Digit[(mByte & 0xF)];

				    String s = new String(tempArr);
				    return s;
				  }
				  
				  
				  public static String getSignature(String token,String timestamp, String nonce) {
						String[] arr = new String[] { token, timestamp, nonce };
						// 将token、timestamp、nonce三个参数进行字典序排序
						Arrays.sort(arr);
						StringBuilder content = new StringBuilder();
						for (int i = 0; i < arr.length; i++) {
							content.append(arr[i]);
						}
						MessageDigest md = null;
						String tmpStr = null;

						try {
							md = MessageDigest.getInstance("SHA-1");
							// 将三个参数字符串拼接成一个字符串进行sha1加密
							byte[] digest = md.digest(content.toString().getBytes());
							tmpStr = byteToStr(digest);
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}

						content = null;
						return tmpStr;
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
				  
				//正则验证
					public static boolean isCorrect(String rgx, String res)
					{
						Pattern p = Pattern.compile(rgx);
						
						Matcher m = p.matcher(res);
						
						return m.matches();
					}
				    
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
				    
				 // base64字符串转化成图片
					public static String GenerateImage(String imgStr) throws Exception{ // 对字节数组字符串进行Base64解码并生成图片
						if (imgStr == null) // 图像数据为空
							return null;
							BASE64Decoder decoder = new BASE64Decoder();
							
							try {
							// Base64解码
								//imgStr = imgStr.replaceAll(" ", "+");
								System.out.println("file.type:::::::::::"+imgStr);
								byte[] b = decoder.decodeBuffer(imgStr);
								for (int i = 0; i < b.length; ++i) {
									if (b[i] < 0) {// 调整异常数据
										b[i] += 256;
									}
								}
							// 生成jpeg图片
								String imgFilePath ="";
								if(ConstantUtil.environment.equals("tomcat"))
									imgFilePath=ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", "file/");//tomcat
								else if(ConstantUtil.environment.equals("maven"))
									imgFilePath = ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp/file/");//maven
								long thistime = new Date().getTime();
								imgFilePath = imgFilePath+thistime+".jpeg";// 新生成的图片
								System.out.println(imgFilePath);
								OutputStream out = new FileOutputStream(imgFilePath);
								out.write(b);
								out.flush();
								out.close();
								return "/file/"+thistime+".jpeg";
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
					}
				  
				  
				  private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
					 //云片短信
					 public static boolean yunpian(String content,String mobile) {
						 boolean flag = false;
						 
						 String apikey = "128847734f480428ff20e9386e67bd64";
						 apikey = "522c3830278d605b3579ce2e397b4462";
						 Map<String, String> params = new HashMap<String, String>();
					        params.put("apikey", apikey);
					        params.put("text", content);
					        params.put("mobile", mobile);
					        String result = post(URI_SEND_SMS, params);

					        JsonObject jo = JsonUtil.getJsonParser().parse(result).getAsJsonObject();
					        
					        if(jo.get("code")!=null&&"0".equals(jo.get("code").getAsString())&&"发送成功".equals(jo.get("msg").getAsString()))flag = true;
					        
					        return flag;
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
					 
					 public static String PostRequest(String URL,String obj,String token) { 
					        String jsonString="";
					        try { 
					            //创建连接 
					            URL url = new URL(URL); 
					            HttpURLConnection connection = (HttpURLConnection) url 
					                    .openConnection(); 
					            connection.setDoOutput(true); 
					            connection.setDoInput(true); 
					            connection.setRequestMethod("POST"); //设置请求方法
					            connection.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
					            connection.setUseCaches(false); 
					            connection.setInstanceFollowRedirects(true); 
					            connection.setRequestProperty("Content-Type", 
					                    "application/json"); 

					            if(token!=null)connection.setRequestProperty("Authorization", "Bearer "+token);
					            connection.connect(); 

					            //POST请求 
					            /*DataOutputStream out = new DataOutputStream( 
					                    connection.getOutputStream()); //关键的一步

					            
					            out.writeBytes(obj); 
					            */
					            PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"utf-8"));  
					            out.println(obj);
					            out.flush(); 
					            out.close(); 

					            // 读取响应
					            if (connection.getResponseCode()==200) {
					                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					                String lines;
					                StringBuffer sb = new StringBuffer("");
					                while ((lines = reader.readLine()) != null) {
					                    lines = new String(lines.getBytes(), "utf-8");
					                    sb.append(lines);
					                }
					                jsonString=sb.toString();
					                reader.close();
					            }//返回值为200输出正确的响应信息

					            if (connection.getResponseCode()==400) {
					                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
					                String lines;
					                StringBuffer sb = new StringBuffer("");
					                while ((lines = reader.readLine()) != null) {
					                    lines = new String(lines.getBytes(), "utf-8");
					                    sb.append(lines);
					                }
					                jsonString=sb.toString();
					                reader.close();
					            }//返回值错误，输出错误的返回信息
					            // 断开连接 
					            connection.disconnect(); 
					        } catch (MalformedURLException e) { 
					            // TODO Auto-generated catch block 
					            e.printStackTrace(); 
					        } catch (UnsupportedEncodingException e) { 
					            // TODO Auto-generated catch block 
					            e.printStackTrace(); 
					        } catch (IOException e) { 
					            // TODO Auto-generated catch block 
					            e.printStackTrace(); 
					        } 
					        return jsonString;
					    } 
}
