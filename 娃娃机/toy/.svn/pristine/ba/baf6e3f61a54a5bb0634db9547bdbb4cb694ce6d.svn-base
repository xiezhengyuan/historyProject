package com.hxy.isw.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.Designation;
import com.hxy.isw.entity.Giftbox;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;
import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * @author lcc
 * @date 2017年4月11日 上午11:18:27
 * @describe
 */
public class NIOSServer implements Runnable {
	private int port = 8888;

	// 解码buffer
	private Charset cs = Charset.forName("utf-8");

	/* 接受数据缓冲区 */
	private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);

	/* 发送数据缓冲区 */
	private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);

	/* 映射客户端channel */
	private Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();
	public static Map<Long, SocketChannel> clientsLMap = new HashMap<Long, SocketChannel>();
	public static Map<Long, SocketChannel> clientsLMapOfApp = new HashMap<Long, SocketChannel>();

	public static Map<String, Long> savemchuser = new HashMap<String, Long>();//记录当时玩娃娃机的用户
	public static Map<String, Date> mchusergift = new HashMap<String, Date>();//记录礼物盒最近的用户
	
	public static DatabaseHelper databaseHelper;
	
	private static Selector selector;

	public NIOSServer() {
		// this.port = port;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static NIOSServer single = null;

	// 静态工厂方法
	public static synchronized NIOSServer getInstance() {
		if (single == null) {
			single = new NIOSServer();
		}
		return single;
	}

	private void init() throws IOException {
		/*
		 * 启动服务器端，配置为非阻塞，绑定端口，注册accept事件 ACCEPT事件：当服务端收到客户端连接请求时，触发该事件
		 */
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(port));
		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		Sys.out("server start on port:" + port);
	}

	/**
	 * 服务器端轮询监听，select方法会一直阻塞直到有相关事件发生或超时
	 */
	public void listen() {
		while (true) {
			try {
				selector.select();// 返回值为本次触发的事件数
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				for (SelectionKey key : selectionKeys) {
					handle(key);
				}
				selectionKeys.clear();// 清除处理过的事件
			} catch (Exception e) {
				e.printStackTrace();
				clientsLMap.clear();
				clientsLMapOfApp.clear();
				//break;
			}

		}
	}

	/**
	 * 处理不同的事件
	 */
	private void handle(SelectionKey selectionKey) throws IOException {
		ServerSocketChannel server = null;
		SocketChannel client = null;
		String receiveText = null;
		int count = 0;
		if (selectionKey.isValid() && selectionKey.isAcceptable()) {
			/*
			 * 客户端请求连接事件 serversocket为该客户端建立socket连接，将此socket注册READ事件，监听客户端输入
			 * READ事件：当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件
			 */
			server = (ServerSocketChannel) selectionKey.channel();
			client = server.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
			Sys.out("selectionKey.isAcceptable.."+selectionKey.isAcceptable());
		} else if (selectionKey.isValid() && selectionKey.isReadable()) {
			/*
			 * READ事件，收到客户端发送数据，读取数据后继续注册监听客户端
			 */
			client = (SocketChannel) selectionKey.channel();
			rBuffer.clear();
			try {
				count = client.read(rBuffer);
				if (count > 0) {
					rBuffer.flip();
					receiveText = String.valueOf(cs.decode(rBuffer).array());
					Sys.out("socket..."+receiveText);
					if(receiveText.length()>0){
						JsonObject json = new JsonParser().parse(receiveText).getAsJsonObject();
						Long userid = json.get("userid")==null?null:"null".equals(json.get("userid").getAsString())?null:json.get("userid").getAsLong();
//						if (userid!=null&&clientsLMap.get(userid) == null){
						if (userid!=null){
							
							if(json.get("type")==null){//APP端连接
								clientsLMapOfApp.put(userid, client);
							}else{//机器端连接
								int type = json.get("type").getAsInt();
								
								if(type==0&&clientsLMap.get(userid) == null)clientsLMap.put(userid, client);//type=0表示客户端与服务端建立连接
								else if(type==1){//type=1表示抓取成功
									Long uid = json.get("uid")==null?null:json.get("uid").getAsLong();
									Sys.out("socket...type..."+type+".....success...uid.."+uid);
									savetogiftbox(uid, userid);
								}
							}
							
							
							
						}
					}
					//dispatch(client, json.get("time").getAsString());
					// client = (SocketChannel)selectionKey.channel();
					// client.register(selector, SelectionKey.OP_READ);
				}/*else if(count==-1){
					selectionKey.cancel();
					client.socket().close();
					client.close();

					//找出机器端删除
					Iterator<Map.Entry<Long, SocketChannel>> it = clientsLMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<Long, SocketChannel> entry = it.next();
						if (entry.getValue().equals(client)) {
							Sys.out("delete this: " + client + " = " + entry.getValue());
							it.remove(); // OK
						}
					}
					
					//找出app端删除
					Iterator<Map.Entry<Long, SocketChannel>> itApp = clientsLMapOfApp.entrySet().iterator();
					while (itApp.hasNext()) {
						Map.Entry<Long, SocketChannel> entry = itApp.next();
						if (entry.getValue().equals(client)) {
							Sys.out("delete this: " + client + " = " + entry.getValue());
							itApp.remove(); // OK
						}
					}
					
					Sys.out("socket read..count==-1....clientsMap.size.."+clientsLMap.size()+"...clientsMapofApp.size.."+clientsLMapOfApp.size());
				}*/
			} catch (Exception e) {
				if(e.getMessage().indexOf("google")!=-1){
					Sys.out("channel.exception.."+e.getMessage());
					return;
				}
				
				
				
				if(e.getMessage().indexOf("Connection")!=-1){
					Sys.out(e.getMessage());
					selectionKey.cancel();
					client.socket().close();
					client.close();

					//找出机器端删除
					Iterator<Map.Entry<Long, SocketChannel>> it = clientsLMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<Long, SocketChannel> entry = it.next();
						if (entry.getValue().equals(client)) {
							Sys.out("delete this: " + client + " = " + entry.getValue());
							it.remove(); // OK
						}
					}
					
					//找出app端删除
					Iterator<Map.Entry<Long, SocketChannel>> itApp = clientsLMapOfApp.entrySet().iterator();
					while (itApp.hasNext()) {
						Map.Entry<Long, SocketChannel> entry = itApp.next();
						if (entry.getValue().equals(client)) {
							Sys.out("delete app this: " + client + " = " + entry.getValue());
							itApp.remove(); // OK
						}
					}
					Sys.out("socket excepion.."+e.getMessage()+"..clientsMap.size.."+clientsLMap.size()+"...clientsMapofApp.size.."+clientsLMapOfApp.size());
				}
				Sys.out("=======socket exception===start====");
				e.printStackTrace();
				Sys.out("=======socket exception===end====");
				/*if(e.getMessage()==null||e.getMessage().indexOf("http://check.best-p")!=-1){
					clientsLMap.clear();
					clientsLMapOfApp.clear();
					listen();
				}*/
				
				return;
			}
		}
	}

	//uid 用户id   userid机器id
	private synchronized void savetogiftbox(Long uid,Long userid){
		//找出使用这台机器的用户
		
			String query = "select mi,ti from MachineInfo mi,ToysInfo ti where ti.id= mi.ftoysid and mi.machineno = '"+userid+"' and mi.state >= 0 order by mi.createtime desc ";
			List<Object[]> milst = databaseHelper.getResultListByHql(query);
			if(milst.size()>0){
				MachineInfo mi = (MachineInfo)milst.get(0)[0];
				ToysInfo ti = (ToysInfo)milst.get(0)[1];
				Date now = new Date();
				Giftbox gb = new Giftbox();
				gb.setCreatetime(now);
				gb.setFmachineid(mi.getId());
				gb.setFtoysid(ti.getId());
				
				
				
				if(uid==null)uid = savemchuser.get(userid.toString())==null?0l:savemchuser.get(userid.toString());
				
				//检查10秒内一台机器一个用户不会能连续抓取成功
				if(mchusergift.get(mi.getId()+"_"+uid)!=null){//先检查缓存
					Date latTime = mchusergift.get(mi.getId()+"_"+uid);
					if(now.getTime()-latTime.getTime()<1000l*6){
						Sys.out("..cache userid:"+uid+" and mid:"+mi.getId()+"..success time is too short");
						return;
					}
				}else{//缓存中没有则检查数据库
					query = "select gb from Giftbox gb where gb.fuserinfoid = "+uid+" and gb.fmachineid = "+mi.getId()+" order by gb.createtime desc";
					List<Object> gl = databaseHelper.getResultListByHql(query);
					if(gl.size()>0){
						Giftbox lastgb = (Giftbox)gl.get(0);
						if(now.getTime()-lastgb.getCreatetime().getTime()<1000l*6){
							Sys.out("..database userid:"+uid+" and mid:"+mi.getId()+"..success time is too short");
							return;
						}
					}
				}
				
				
				
				gb.setFuserinfoid(uid);
				gb.setGetphoto("");
				gb.setMachineno(mi.getMachineno());
				gb.setPhoto(ti.getPhoto());
				gb.setState(0);
				
				
				
				
				gb.setToysname(ti.getName());
				gb.setApplystate(0);
				databaseHelper.persistObject(gb);
				
				UserInfo ui=(UserInfo) databaseHelper.getObjectById(UserInfo.class,userid);
				long number =ui.getExperiencenum()+2;
				ui.setExperiencenum(ui.getExperiencenum()+2);
		       
		        String sql1= "select d FROM Designation d where d.state=0 order by d.experience  asc ";
		       
		        List<Object> lst=databaseHelper.getResultListByHql(sql1);
		        for (int i = 1; i < lst.size(); i++) {
		        	//后一个称号
		        	Designation de= (Designation) lst.get(i);
		        	if(number<de.getExperience()){
		        		//前一个称号
		        		Designation d= (Designation) lst.get(i-1);
		        		ui.setDesignation(d.getDesignation());
		        		ui.setLevel(d.getLevel());
		        		break;
		        	}
		        	
				}
		        //最后一个
		        Designation des= (Designation) lst.get(lst.size()-1);
		        if(number>=des.getExperience()){
		        	ui.setDesignation(des.getDesignation());
		        	ui.setLevel(des.getLevel());
		        }
		        databaseHelper.updateObject(ui);
				
				//更新缓存
				mchusergift.put(mi.getId()+"_"+uid,now);
				
				String info = "{\"type\":\"4\",\"info\":\"恭喜你抓取成功，可以去礼物盒查看哦\",\"machineno\":\""+mi.getMachineno()+"\",\"machineid\":\""+mi.getId()+"\"}";
				sBuffer.clear();  
				sBuffer.put(info.getBytes());  
				sBuffer.flip(); 
				try {
					if(NIOSServer.clientsLMapOfApp.get(uid)!=null){
					    
						Sys.out("send to user..success:"+mi.getMachineno()+"...uid..."+uid+"...info..."+info);
						NIOSServer.clientsLMapOfApp.get(uid).write(sBuffer);
						sBuffer.clear();  
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					sBuffer.clear();  
				}
			}
		
	}
	
	/**
	 * 把当前客户端信息 推送到其他客户端
	 */
	private void dispatch(SocketChannel client, String info) throws IOException {
		Socket s = client.socket();
		String name = "[" + s.getInetAddress().toString().substring(1) + ":" + Integer.toHexString(client.hashCode())
				+ "]";
		if (!clientsLMap.isEmpty()) {
			for (Map.Entry<Long, SocketChannel> entry : clientsLMap.entrySet()) {
				SocketChannel temp = entry.getValue();
				if (!client.equals(temp)) {
					sBuffer.clear();
					sBuffer.put((name + ":" + info).getBytes());
					sBuffer.flip();
					// 输出到通道
					temp.write(sBuffer);
				}
			}
		}
	}

	public void sendmsg(Long userId, String info) throws IOException {
		// Socket s = client.socket();
		Sys.out("info;;;" + info);
		SocketChannel client = clientsLMap.get(userId);
		sBuffer.clear();
		sBuffer.put(info.getBytes());
		sBuffer.flip();
		Sys.out("ssss;;;;;;;;;;;;" + sBuffer.toString());
		Sys.out("client;;;;;;;;;;;;" + client);
		// 输出到通道
		client.write(sBuffer);

	}

	public static void main(String[] args) throws IOException {
		NIOSServer server = getInstance();
		server.listen();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		listen();
	}
}
