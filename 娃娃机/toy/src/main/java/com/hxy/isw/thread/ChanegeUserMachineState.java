package com.hxy.isw.thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.MachineUsed;
import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;

public class ChanegeUserMachineState implements Runnable {
	public static DatabaseHelper databaseHelper;
	
	/*发送数据缓冲区*/  
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);  
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true)
		{
			
			try {
				checkusermachinestate();
				Thread.sleep(1000l);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//break;
			}
		}
	}
	
	private void checkusermachinestate(){
		//找出所有用户在房间所处的状态
		StringBuffer find = new StringBuffer("select mu from MachineUsed mu where mu.state > 0");
		List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		
		for (Object object : lst) {
			MachineUsed mu = (MachineUsed)object;
			
			long t1 = mu.getCreatetime().getTime();//用户结束一局时的时间
			long t2 = new Date().getTime();//当前时间
			
			long diff = (t2-t1)/1000l;
			//找出所有预约到号的用户
			if(mu.getState()==1){
				//检查开始游戏的时间是否超时
				if(mu.getIndexs()==0){
					if(diff>ConstantUtil.countdownofstartplay){
						//将此用户的状态置为观看
						mu.setCreatetime(new Date());
						mu.setState(0);
						databaseHelper.updateObject(mu);
						
						MachineInfo mi = (MachineInfo)databaseHelper.getObjectById(MachineInfo.class, mu.getFmachineid());
						String info = "{\"type\":\"3\",\"info\":\"等待超时,请重新预约\",\"machineno\":\""+mi.getMachineno()+"\",\"machineid\":\""+mi.getId()+"\"}";
						
						sBuffer.clear();  
				        sBuffer.put(info.getBytes());  
				        sBuffer.flip(); 
				        Sys.out("NIOSServer.clientsLMapOfApp.size..."+NIOSServer.clientsLMapOfApp.size());
				        try {
							if(NIOSServer.clientsLMapOfApp.get(mu.getFuserinfoid())!=null){
								Sys.out("send to user..userid:"+mu.getFuserinfoid()+"...info..."+info);
								NIOSServer.clientsLMapOfApp.get(mu.getFuserinfoid()).write(sBuffer);
								sBuffer.clear();  
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							handlePipe(e, mu);
							sBuffer.clear();  
						}
				        
						//找出所有预约排在此用户之后的用户
						changeorderby(mu,0);
						
					}
				}
			}else if(mu.getState()==2){//检查游戏中的用户是否超时
				if(diff>ConstantUtil.gametime){
					//将此用户的状态置为等待再次开始游戏
					mu.setCreatetime(new Date());
					mu.setState(3);
					databaseHelper.updateObject(mu);
					
					MachineInfo mi = (MachineInfo)databaseHelper.getObjectById(MachineInfo.class, mu.getFmachineid());
					NIOSServer.savemchuser.put(mi.getMachineno(), mu.getFuserinfoid());
					String info = "{\"type\":\"2\",\"info\":\"此局已结束，请选择继续游戏或者退出\",\"issubscribe\":\""+mu.getState()+"\",\"countdown\":\""+ConstantUtil.countdownofrestartplay+"\",\"machineno\":\""+mi.getMachineno()+"\",\"machineid\":\""+mi.getId()+"\"}";
					
					//给app发送一个游戏结束的指令
					sBuffer.clear();  
			        sBuffer.put(info.getBytes());  
			        sBuffer.flip(); 
			        Sys.out("NIOSServer.clientsLMapOfApp.size..."+NIOSServer.clientsLMapOfApp.size());
			        try {
						if(NIOSServer.clientsLMapOfApp.get(mu.getFuserinfoid())!=null){
							Sys.out("send to user..userid:"+mu.getFuserinfoid()+"...info..."+info);
							NIOSServer.clientsLMapOfApp.get(mu.getFuserinfoid()).write(sBuffer);
							sBuffer.clear();  
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						handlePipe(e, mu);
						sBuffer.clear();  
					}
					
			        //超时，给机器发送一个开启红外线的指令
			        info = "TT";
			        sBuffer.clear();  
			        sBuffer.put(info.getBytes());  
			        sBuffer.flip(); 
			        Sys.out("send to machine..machineno:"+mi.getMachineno()+"..info..."+info);
			    	try {
						NIOSServer.clientsLMap.get(Long.parseLong(mi.getMachineno())).write(sBuffer);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						handlePipe(e, mi);
					}
			    	sBuffer.clear(); 
			    	
				}
			}else if(mu.getState()==3){//检查游戏结束等待下一局的用户是否超时(需要加上爪子复位时间)
				if(diff>(ConstantUtil.countdownofrestartplay+ConstantUtil.countdownofresset)){
					//将此用户的状态置为观看
					mu.setCreatetime(new Date());
					mu.setState(0);
					databaseHelper.updateObject(mu);
					String  sql="update userinfo set isplay =0 where id= "+mu.getFuserinfoid()+"";
					databaseHelper.executeNativeSql(sql);
					MachineInfo mi = (MachineInfo)databaseHelper.getObjectById(MachineInfo.class, mu.getFmachineid());
					String info = "{\"type\":\"3\",\"info\":\"等待超时,请重新排队预约\",\"machineno\":\""+mi.getMachineno()+"\",\"machineid\":\""+mi.getId()+"\"}";
					
					sBuffer.clear();  
			        sBuffer.put(info.getBytes());  
			        sBuffer.flip(); 
			        Sys.out("NIOSServer.clientsLMapOfApp.size..."+NIOSServer.clientsLMapOfApp.size());
			        try {
						if(NIOSServer.clientsLMapOfApp.get(mu.getFuserinfoid())!=null){
							Sys.out("send to user..userid:"+mu.getFuserinfoid()+"...info..."+info);
							NIOSServer.clientsLMapOfApp.get(mu.getFuserinfoid()).write(sBuffer);
							sBuffer.clear();  
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						handlePipe(e, mu);
						sBuffer.clear();  
					}
					
					//找出所有预约排在此用户之后的用户
					changeorderby(mu,0);
				}
			}
			
		}
		
		checkmachinestate();
	}
	
	
	//找出所有预约排在此用户之后的用户
	private void changeorderby (MachineUsed mu,int indexs){
		StringBuffer find =  new StringBuffer("select mu from MachineUsed mu where mu.state = 1 ")
				.append(" and mu.fmachineid = ").append(mu.getFmachineid()).append(" and mu.indexs > ").append(indexs);
		List<Object> flst = databaseHelper.getResultListByHql(find.toString());
		
		MachineInfo mi = (MachineInfo)databaseHelper.getObjectById(MachineInfo.class, mu.getFmachineid());
		
		int subscribe = mi.getSubscribe()-1<0?0:mi.getSubscribe()-1;
		Sys.out("subscribe...nums:"+subscribe);
		mi.setSubscribe(subscribe);
		databaseHelper.updateObject(mi);
		
		for (Object obj : flst) {
			MachineUsed machineused = (MachineUsed)obj;
			
			//将这些用户的排号前移
			machineused.setIndexs((machineused.getIndexs()-1)<0?0:(machineused.getIndexs()-1));
			machineused.setCreatetime(new Date());
			databaseHelper.updateObject(machineused);
			
			//通知到号的用户
			if(machineused.getIndexs()<=0){
				String info = "{\"type\":\"1\",\"info\":\"轮到你开始游戏了\",\"issubscribe\":\""+mu.getState()+"\",\"countdown\":\""+ConstantUtil.countdownofstartplay+"\",\"machineno\":\""+mi.getMachineno()+"\",\"machineid\":\""+mi.getId()+"\"}";
				
				sBuffer.clear();  
		        sBuffer.put(info.getBytes());  
		        sBuffer.flip(); 
		        Sys.out("NIOSServer.clientsLMapOfApp.size..."+NIOSServer.clientsLMapOfApp.size());
		        try {
					if(NIOSServer.clientsLMapOfApp.get(machineused.getFuserinfoid())!=null){
						Sys.out("send to user..userid:"+machineused.getFuserinfoid()+"...info..."+info);
						NIOSServer.clientsLMapOfApp.get(machineused.getFuserinfoid()).write(sBuffer);
						sBuffer.clear();  
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					sBuffer.clear();
					handlePipe(e, machineused);
				}
		        
			}
		}
	}
	
	//检查所有机器状态
	private void checkmachinestate(){
		
		StringBuffer check =  new StringBuffer("select mi from MachineInfo mi where mi.state = 1 ");
		
		List<Object> lst = databaseHelper.getResultListByHql(check.toString());
		
		for (Object object : lst) {
			
			MachineInfo mi = (MachineInfo)object;
			
			//检查机器是否被使用
			check =  new StringBuffer("select mu from MachineUsed mu where mu.state > 0 ").append(" and mu.fmachineid = ").append(mi.getId());
			
			List cl = databaseHelper.getResultListByHql(check.toString());
			
			if(cl.size()==0){
				//将机器置为空闲
				mi.setState(0);
				databaseHelper.updateObject(mi);
			}
			
		}
		
	}
	private void handlePipe(IOException e,MachineUsed machineused){
		String mess = e.getMessage();
		if(mess.indexOf("Broken pipe")!=-1){
			Iterator<Map.Entry<Long, SocketChannel>> it = NIOSServer.clientsLMapOfApp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Long, SocketChannel> entry = it.next();
				if (entry.getKey().equals(machineused.getFuserinfoid())) {
					Sys.out("delete app this key: " + machineused.getFuserinfoid() + " = " + entry.getValue());
					it.remove(); // OK
				}
			}
		}
	}
	
	private void handlePipe(Exception e,MachineInfo mi){
		String mess = e.getMessage();
		if(mess.indexOf("Broken pipe")!=-1){
			Iterator<Map.Entry<Long, SocketChannel>> it = NIOSServer.clientsLMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Long, SocketChannel> entry = it.next();
				if (entry.getKey().equals(Long.parseLong(mi.getMachineno()))) {
					Sys.out("delete this key: " + mi.getMachineno() + " = " + entry.getValue());
					it.remove(); // OK
				}
			}
		}
	}
}
