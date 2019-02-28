package com.hxy.isw.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.imageio.ImageIO;



import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.util.AliShares4HSUtil;

import com.hxy.isw.util.HttpRequest;
import com.hxy.isw.util.SyncDemo_股票行情;
import com.hxy.isw.util.Sys;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public final class Test {
    /**//*
     * public final static String getPressImgPath() { return ApplicationContext
     * .getRealPath("/template/data/util/shuiyin.gif"); }
     */
    /** *//**
     * 把图片印刷到图片上
     * 
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     * @param x
     *            --x坐标
     * @param y
     *            --y坐标
     */
    public final static void pressImage(String pressImg, String targetImg,
            int x, int y) {
        try {
            //目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            //水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            //水印文件结束
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** *//**
     * 打印文字水印图片
     * 
     * @param pressText
     *            --文字
     * @param targetImg --
     *            目标图片
     * @param fontName --
     *            字体名
     * @param fontStyle --
     *            字体样式
     * @param color --
     *            字体颜色
     * @param fontSize --
     *            字体大小
     * @param x --
     *            偏移量
     * @param y
     */
    public static void pressText(String pressText, String targetImg,
            String fontName, int fontStyle, int color, int fontSize, int x,
            int y) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // String s="www.qhd.com.cn";
            g.setColor(Color.RED);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.drawString(pressText, wideth - fontSize - x, height - fontSize
                    / 2 - y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        /*pressImage("D:/logo.png","D:/11.png", 0, 0);
        pressText("HELLO WORLD", "D:/11.png", "宋体", 0, 0, 30, 150, 150);
    	double y = 50.0;
    	double d1 = Math.sqrt((4*y+25.0)/100)-0.5;
    	System.out.println((int)d1);*/
    	//testJpush();
    	
    	testJpush();
    	
    	/*String path = "E:\\data.txt";
		
    	
		File file = new File(path );
		Sys.out("before if ......"+path );
		try {
			if (file.exists()) {
				
				Sys.out("after if ......"+path);
				BufferedReader b = new BufferedReader(new FileReader(file));
				 String ss= "";	
					String s = "";
					do {
						if(ss.length()>0){
							s += ss;
						}
					} while ((ss = b.readLine())!= null);
					//Sys.out(s);
					
					List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
					
					JsonObject json = (JsonObject) new JsonParser().parse(s);
					
					JsonArray arr = json.get("result").getAsJsonObject().get("lists").getAsJsonArray();
					for (JsonElement jsonElement : arr) {
						JsonObject jsonObject = jsonElement.getAsJsonObject();
						
						String stoid = jsonObject.get("stoid")==null?"":jsonObject.get("stoid").getAsString();
						String symbol = jsonObject.get("symbol")==null?"":jsonObject.get("symbol").getAsString();
						String sname = jsonObject.get("sname")==null?"":jsonObject.get("sname").getAsString();
						
						Map<String, String> map = new HashMap<String, String>();
						map.put("code", stoid);
						map.put("market", "us");
						map.put("name", sname);
						map.put("pinyin", symbol);
						
						Sys.out(map.get("code")+"   "+map.get("market")+"   "+map.get("name")+"   "+map.get("pinyin"));
						
						lst.add(map);
					}
					
					instdbUS(lst);
			} 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	//testAli();
    	//new SyncDemo_股票行情().股票K线数据Demo();
    	//getdata();
    	/*try {
			SyncDemo_股票行情 sd = new SyncDemo_股票行情();
			ApiResponse response = sd.股票行情_批量("300017,000677");
			String result = new String(response.getBody(), "utf-8");
			Sys.out(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	//getWHByNowApi();
    }
    
    public static void getdata(){
    	 try {
			URL u=new URL("http://api.k780.com/?app=finance.stock_list&category=us&appkey=27090&sign=e980e495bafd40e9cc3a3b4417a0e325&format=json");
			 InputStream in=u.openStream();
			 ByteArrayOutputStream out=new ByteArrayOutputStream();
			 try {
			     byte buf[]=new byte[1024];
			     int read = 0;
			     while ((read = in.read(buf)) > 0) {
			         out.write(buf, 0, read);
			     }
			 }  finally {
			     if (in != null) {
			         in.close();
			     }
			 }
			 byte b[]=out.toByteArray( );
			 String result = new String(b,"utf-8");
			 System.out.println(result);
			 
			 String filePath = "E:/data.txt";
			 File file = new File(filePath);
	            PrintStream ps = new PrintStream(new FileOutputStream(file));
	            ps.println(result);// 往文件里写入字符串
	            ps.close();
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
     }
    
    private static void getWHByNowApi(){
    	try {
			URL u=new URL("http://api.k780.com/?app=finance.rate_curlist&appkey=27090&sign=e980e495bafd40e9cc3a3b4417a0e325&format=json");
			InputStream in=u.openStream();
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			try {
			    byte buf[]=new byte[1024];
			    int read = 0;
			    while ((read = in.read(buf)) > 0) {
			        out.write(buf, 0, read);
			    }
			}  finally {
			    if (in != null) {
			        in.close();
			    }
			}
			byte b[]=out.toByteArray( );
			String s = new String(b,"utf-8");
			System.out.println(s);
			
			List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
			
			JsonObject json = (JsonObject) new JsonParser().parse(s);
			
			JsonArray arr = json.get("result").getAsJsonArray();
			for (JsonElement jsonElement : arr) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				
				String curno = jsonObject.get("curno")==null?"":jsonObject.get("curno").getAsString();
				String curnm = jsonObject.get("curnm")==null?"":jsonObject.get("curnm").getAsString();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", curno);
				map.put("name", curnm);
				map.put("pinyin", curno);
				
				Sys.out(map.get("code")+"   "+map.get("name")+"   "+map.get("pinyin"));
				
				lst.add(map);
			}
			instdbWH(lst);
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
    }
    
    public static void getgpByali(){
    	try {
    		
    		List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
    		AliShares4HSUtil sd = new AliShares4HSUtil();
			ApiResponse response = sd.stocklist("sz","41");
			String result = new String(response.getBody(), "utf-8");
			Sys.out(result);
			JsonObject json = (JsonObject) new JsonParser().parse(result);
			
			JsonArray arr = json.get("showapi_res_body").getAsJsonObject().get("contentlist").getAsJsonArray();
			for (JsonElement jsonElement : arr) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				String stockType = jsonObject.get("stockType")==null?"":jsonObject.get("stockType").getAsString();
				String market = jsonObject.get("market")==null?"":jsonObject.get("market").getAsString();
				String name = jsonObject.get("name")==null?"":jsonObject.get("name").getAsString();
				String state = jsonObject.get("state")==null?"":jsonObject.get("state").getAsString();
				String currcapital = jsonObject.get("currcapital")==null?"":jsonObject.get("currcapital").getAsString();
				String profit_four = jsonObject.get("profit_four")==null?"":jsonObject.get("profit_four").getAsString();
				String code = jsonObject.get("code")==null?"":jsonObject.get("code").getAsString();
				String totalcapital = jsonObject.get("totalcapital")==null?"":jsonObject.get("totalcapital").getAsString();
				String mgjzc = jsonObject.get("mgjzc")==null?"":jsonObject.get("mgjzc").getAsString();
				String pinyin = jsonObject.get("pinyin")==null?"":jsonObject.get("pinyin").getAsString();
				String listing_date = jsonObject.get("listing_date")==null?"":jsonObject.get("listing_date").getAsString();
				String ct = jsonObject.get("ct")==null?"":jsonObject.get("ct").getAsString();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("stockType", stockType);
				map.put("market", market);
				map.put("name", name);
				map.put("state", state);
				map.put("currcapital", currcapital);
				map.put("profit_four", profit_four);
				map.put("code", code);
				map.put("totalcapital", totalcapital);
				map.put("mgjzc", mgjzc);
				map.put("pinyin", pinyin);
				map.put("listing_date", listing_date);
				map.put("ct", ct);
				
				lst.add(map);
			}
			
			instdb(lst);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void instdb(List<Map<String, String>> lst ){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	    Connection conn =null;
	    
	    try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://47.94.37.25:3306/shares?useUnicode=true&characterEncoding=utf-8","root","bellatrix7");
			String sql = "";
			for (Map<String, String> map : lst) {
				sql = "insert into shares "
						+ "(sharesname,code,price,upanddown,createtime,type,state,img,stocktype,market,currcapital,profit_four,totalcapital,mgjzc,pinyin,listing_date,ct,sharestate) values "
						+ "('"+map.get("name")+"','"+map.get("code")+"',0,'',now(),0,0,'','"+map.get("stockType")+"','"+map.get("market")+"','"+map.get("currcapital")+"','"+map.get("profit_four")+"','"+map.get("totalcapital")+"','"+map.get("mgjzc")+"','"+map.get("pinyin")+"','"+map.get("listing_date")+"','"+map.get("ct")+"','"+map.get("state")+"')";
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
			}
		
	    }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
    public static void instdbUS(List<Map<String, String>> lst ){
  		PreparedStatement pstmt = null;
  		ResultSet rs = null;
  	    Connection conn =null;
  	    
  	    try {
  			Class.forName("com.mysql.jdbc.Driver");
  			conn = DriverManager.getConnection("jdbc:mysql://47.94.37.25:3306/shares?useUnicode=true&characterEncoding=utf-8","root","bellatrix7");
  			String sql = "";
  			for (Map<String, String> map : lst) {
  				sql = "insert into shares "
  						+ "(sharesname,code,price,upanddown,createtime,type,state,img,stocktype,market,currcapital,profit_four,totalcapital,mgjzc,pinyin,listing_date,ct,sharestate) values "
  						+ "('"+map.get("name")+"','"+map.get("code")+"',0,'',now(),1,0,'','','"+map.get("market")+"','','','','','"+map.get("pinyin")+"','','','')";
  				System.out.println(sql);
  				pstmt = conn.prepareStatement(sql);
  				pstmt.executeUpdate();
  			}
  		
  	    }catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} finally{
  			try {
  				if(rs!=null)rs.close();
  				if(pstmt!=null)pstmt.close();
  				if(conn!=null)conn.close();
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		}
  	}
    
    public static void instdbWH(List<Map<String, String>> lst ){
  		PreparedStatement pstmt = null;
  		ResultSet rs = null;
  	    Connection conn =null;
  	    
  	    try {
  			Class.forName("com.mysql.jdbc.Driver");
  			conn = DriverManager.getConnection("jdbc:mysql://47.94.37.25:3306/shares?useUnicode=true&characterEncoding=utf-8","root","bellatrix7");
  			String sql = "";
  			for (Map<String, String> map : lst) {
  				sql = "insert into shares "
  						+ "(sharesname,code,price,upanddown,createtime,type,state,img,stocktype,market,currcapital,profit_four,totalcapital,mgjzc,pinyin,listing_date,ct,sharestate) values "
  						+ "('"+map.get("name")+"','"+map.get("code")+"',0,'',now(),2,0,'','','','','','','','"+map.get("pinyin")+"','','','')";
  				System.out.println(sql);
  				pstmt = conn.prepareStatement(sql);
  				pstmt.executeUpdate();
  			}
  		
  	    }catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} finally{
  			try {
  				if(rs!=null)rs.close();
  				if(pstmt!=null)pstmt.close();
  				if(conn!=null)conn.close();
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		}
  	}
    
    public static String sendGet(String url, String param,String appcode) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = (param==null||param.length()==0)?url: url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Authorization", "APPCODE " + appcode);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    public static void testJpush() {
  		String url = "http://localhost:5556/shares/manageuserinfo/queryuserbyid.action";
  		//url = "http://localhost:9090/kuaiwa/appservice/login.action";
  		/*List<Map<String,Long>> lstMap = new ArrayList<Map<String,Long>>();
  		for (int i = 1; i < 3; i++) {
			
  			Map<String,Long> map = new HashMap<String,Long>();
  			map.put("connectid", Long.valueOf(i));
  			lstMap.add(map);
		}*/
  		
      	//String p = "userid=2&connectids=[{\"connectid\":\"1\"},{\"connectid\":\"2\"}]";
      	String p = "userid=1";
     

      	
      	//&tag[]=1&tag[]=2&tag[]=3
      	//p="";
  		String result =  HttpRequest.sendPost(url, p);
  		System.out.println(result);

  	}
    
    public static void testAli(){
    	String host = "https://ali-stock.showapi.com";  
        String path = "/real-stockinfo";  
        String method = "GET";  
        String appcode = "712932b558de6877c36fe1e8617a4032";  
        Map<String, String> headers = new HashMap<String, String>();  
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE  
        // 83359fd73fe94948385f570e3c139105  
        headers.put("Authorization", "APPCODE " + appcode);  
        // encoder.encodeToString(pathImage.get);  
        // 根据API的要求，定义相对应的Content-Type  
        headers.put("Content-Type", "application/json; charset=UTF-8");  
        Map<String, String> querys = new HashMap<String, String>();  
        querys.put("code", "600887");
        querys.put("needIndex", "1");
        querys.put("need_k_pic", "1");
        String param = "code=600887&needIndex=1&need_k_pic=1";
        
        sendGet(host+path, param, appcode);
    }
    
}