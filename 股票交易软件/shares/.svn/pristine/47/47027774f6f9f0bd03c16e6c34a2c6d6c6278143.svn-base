package com.hxy.isw.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

/**
 * 功能说明：用来存储业务定义的sessionId和连接的对应关系
 * 利用业务逻辑中组装的sessionId获取有效连接后进行后续操作
 * 作者：liuxing(2014-12-26 02:32)
*/
public class SessionUtils {

	public static DatabaseHelper databaseHelper;
	
 public static Map<String, Session> clients = new ConcurrentHashMap<String, Session>();

 public static void put(String relationId, Long userCode, Session session){
 clients.put(getKey(relationId, userCode), session);
 
/*//将此用户的在线状态改为1
UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, userCode);
if(ui!=null){
	 if(ui.getLoginstate()!=1){
		 ui.setLoginstate(1);
		 databaseHelper.updateObject(ui);
	 }
}*/
}

 public static Session get(String relationId, Long userCode){
 return clients.get(getKey(relationId, userCode));
}

 public static void remove(String relationId, Long userCode){
 clients.remove(getKey(relationId, userCode));
 /*//将此用户的在线状态改为0
 UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, userCode);
 if(ui!=null){
	 if(ui.getLoginstate()!=0){
		 ui.setLoginstate(0);
		 databaseHelper.updateObject(ui);
	 }
 }*/
}

/**
 * 判断是否有连接
 * @param relationId
 * @param userCode
 * @return
*/
 public static boolean hasConnection(String relationId, Long userCode) {
 return clients.containsKey(getKey(relationId, userCode));
}

/**
 * 组装唯一识别的key
 * @param relationId
 * @param userCode
 * @return
*/
 public static String getKey(String relationId, Long userCode) {
 return relationId +"_"+ userCode;
}

}