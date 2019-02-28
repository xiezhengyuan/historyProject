package com.hxy.isw.util;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

/**
* @author lcc
* @date 2017年10月25日 上午10:19:35
* @describe
*/
public class KLineData {
	
	private final static String apiurl = "https://www.alphavantage.co/query";
	private final static String apikey = "WLTYUYN4LYGP7JG3";
	private final static int timeOut = 60000;//60秒
	/**
	 * 分时K线
	 * @param scur
	 * @return
	 */
	 public static String getMinKLine(String scur){
		 //https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=1min&apikey=WLTYUYN4LYGP7JG3
	    	try {
	    		scur = scur.toUpperCase();
//				String result = HttpRequest.sendGet(apiurl,"function=TIME_SERIES_INTRADAY&symbol="+scur+"&interval=1min&apikey="+apikey);
				String result = getRequest(apiurl+"?function=TIME_SERIES_INTRADAY&symbol="+scur+"&interval=1min&apikey="+apikey, timeOut);
				/*result = result.replace("jsonret(", "").replace(")", "");
				JsonObject object = (JsonObject) new JsonParser().parse(result);*/
				
				//System.out.println("getresult..minKline.."+result);
				//String price = object.get("list").getAsJsonArray().size()==0?"0":object.get("list").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsString();
				return result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }

	 	/**
		 * 日K线
		 * @param scur
		 * @return
		 */
		 public static String getDayKLine(String scur){
			 //https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=MSFT&apikey=WLTYUYN4LYGP7JG3
		    	try {
		    		scur = scur.toUpperCase();
//					String result = HttpRequest.sendGet(apiurl,"function=TIME_SERIES_DAILY_ADJUSTED&symbol="+scur+"&apikey="+apikey);
					String result = getRequest(apiurl+"?function=TIME_SERIES_DAILY_ADJUSTED&symbol="+scur+"&apikey="+apikey, timeOut);
					/*result = result.replace("jsonret(", "").replace(")", "");
					JsonObject object = (JsonObject) new JsonParser().parse(result);*/
					
					//System.out.println("getresult..dayKline.."+result);
					//String price = object.get("list").getAsJsonArray().size()==0?"0":object.get("list").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsString();
					return result;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return null;
		    }
		 
		 	/**
			 * 周K线
			 * @param scur
			 * @return
			 */
			 public static String getWeekKLine(String scur){
				 //https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY_ADJUSTED&symbol=MSFT&apikey=WLTYUYN4LYGP7JG3
			    	try {
			    		scur = scur.toUpperCase();
//						String result = HttpRequest.sendGet(apiurl,"function=TIME_SERIES_WEEKLY_ADJUSTED&symbol="+scur+"&apikey="+apikey);
						String result = getRequest(apiurl+"?function=TIME_SERIES_WEEKLY_ADJUSTED&symbol="+scur+"&apikey="+apikey,timeOut);
						/*result = result.replace("jsonret(", "").replace(")", "");
						JsonObject object = (JsonObject) new JsonParser().parse(result);*/
						
						//System.out.println("getresult..weekKline.."+result);
						//String price = object.get("list").getAsJsonArray().size()==0?"0":object.get("list").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsString();
						return result;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	return null;
			    }
			 
			 
			 	/**
				 * 月K线
				 * @param scur
				 * @return
				 */
				 public static String getMonthKLine(String scur){
					 //https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY_ADJUSTED&symbol=MSFT&apikey=WLTYUYN4LYGP7JG3
				    	try {
				    		scur = scur.toUpperCase();
//							String result = HttpRequest.sendGet(apiurl,"function=TIME_SERIES_MONTHLY_ADJUSTED&symbol="+scur+"&apikey="+apikey);
							String result = getRequest(apiurl+"?function=TIME_SERIES_MONTHLY_ADJUSTED&symbol="+scur+"&apikey="+apikey,timeOut);
							/*result = result.replace("jsonret(", "").replace(")", "");
							JsonObject object = (JsonObject) new JsonParser().parse(result);*/
							
							//System.out.println("getresult..monthKline.."+result);
							//String price = object.get("list").getAsJsonArray().size()==0?"0":object.get("list").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsString();
							return result;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	return null;
				    }
	 
				 public static String getRequest(String url,int timeOut) throws Exception{  
				        URL u = new URL(url);  
				        if("https".equalsIgnoreCase(u.getProtocol())){  
				            SslUtils.ignoreSsl();  
				        }  
				        URLConnection conn = u.openConnection();  
				        conn.setConnectTimeout(timeOut);  
				        conn.setReadTimeout(timeOut);  
				        return IOUtils.toString(conn.getInputStream());  
				    }  
				       
				    public static String postRequest(String urlAddress,String args,int timeOut) throws Exception{  
				        URL url = new URL(urlAddress);  
				        if("https".equalsIgnoreCase(url.getProtocol())){  
				            SslUtils.ignoreSsl();  
				        }  
				        URLConnection u = url.openConnection();  
				        u.setDoInput(true);  
				        u.setDoOutput(true);  
				        u.setConnectTimeout(timeOut);  
				        u.setReadTimeout(timeOut);  
				        OutputStreamWriter osw = new OutputStreamWriter(u.getOutputStream(), "UTF-8");  
				        osw.write(args);  
				        osw.flush();  
				        osw.close();  
				        u.getOutputStream();  
				        return IOUtils.toString(u.getInputStream());  
				    }  
				 
				 public static void main(String[] args) {
					getMinKLine("baba");
				}
}
