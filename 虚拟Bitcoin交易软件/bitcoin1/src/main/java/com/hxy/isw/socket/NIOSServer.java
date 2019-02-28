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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年4月11日 上午11:18:27
* @describe
*/
public class NIOSServer implements Runnable{
	private int port = 8888;  
    
    //解码buffer    
    private Charset cs = Charset.forName("utf-8");  
      
    /*接受数据缓冲区*/  
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);  
      
    /*发送数据缓冲区*/  
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);  
      
    /*映射客户端channel */  
    private Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();  
    public static Map<Long, SocketChannel> clientsLMap = new HashMap<Long, SocketChannel>();  
      
    private static Selector selector;  
      
    public NIOSServer() {  
        //this.port = port;  
        try {  
            init();  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    
    private static NIOSServer single=null;  
    //静态工厂方法   
    public static synchronized  NIOSServer getInstance() {  
         if (single == null) {    
             single = new NIOSServer();  
         }    
        return single;  
    }  
    
    private void init() throws IOException {  
        /*  
         *启动服务器端，配置为非阻塞，绑定端口，注册accept事件  
         *ACCEPT事件：当服务端收到客户端连接请求时，触发该事件  
         */  
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  
        serverSocketChannel.configureBlocking(false);  
        ServerSocket serverSocket = serverSocketChannel.socket();  
        serverSocket.bind(new InetSocketAddress(port));  
        selector = Selector.open();  
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  
        System.out.println("server start on port:" + port);  
    }  
      
    /**  
     * 服务器端轮询监听，select方法会一直阻塞直到有相关事件发生或超时  
     */  
    public void listen() {  
        while (true) {  
            try {  
                selector.select();//返回值为本次触发的事件数    
                Set<SelectionKey> selectionKeys = selector.selectedKeys();  
                for (SelectionKey key : selectionKeys) {  
                    handle(key);  
                }  
                selectionKeys.clear();//清除处理过的事件    
            }  
            catch (Exception e) {  
                e.printStackTrace();  
                break;  
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
        if (selectionKey.isValid()&& selectionKey.isAcceptable()) {  
            /*  
             * 客户端请求连接事件  
             * serversocket为该客户端建立socket连接，将此socket注册READ事件，监听客户端输入  
             * READ事件：当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件  
             */  
            server = (ServerSocketChannel)selectionKey.channel();  
            client = server.accept();  
            client.configureBlocking(false);  
            client.register(selector, SelectionKey.OP_READ);  
        }  
        else if (selectionKey.isValid()&&selectionKey.isReadable()) {  
            /*  
             * READ事件，收到客户端发送数据，读取数据后继续注册监听客户端  
             */  
            client = (SocketChannel)selectionKey.channel();  
            rBuffer.clear();  
            try{  
            	count = client.read(rBuffer);  
	            if (count > 0) {  
	                rBuffer.flip();  
	                receiveText = String.valueOf(cs.decode(rBuffer).array());  
	                System.out.println( receiveText);  
	                JsonObject json = new JsonParser().parse(receiveText).getAsJsonObject();
	                Long userid = json.get("userid").getAsLong();
	                if(clientsLMap.get(userid)==null)clientsLMap.put(userid, client);
	                dispatch(client, json.get("time").getAsString());  
	                //client = (SocketChannel)selectionKey.channel();  
	                //client.register(selector, SelectionKey.OP_READ);  
	            }  
            }catch(IOException e){  
            	selectionKey.cancel();  
            	client.socket().close();  
            	client.close();  
            	
            	Iterator<Map.Entry<Long, SocketChannel>> it = clientsLMap.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<Long, SocketChannel> entry=it.next();
                    if(entry.getValue().equals(client)){
                        Sys.out("delete this: "+client+" = "+entry.getValue());
                        it.remove();        //OK 
                    }
                }
            	
                return;  
            }  
        }  
    }  
      
    /**  
     * 把当前客户端信息 推送到其他客户端  
     */  
    private void dispatch(SocketChannel client, String info) throws IOException {  
        Socket s = client.socket();  
        String name =  
            "[" + s.getInetAddress().toString().substring(1) + ":" + Integer.toHexString(client.hashCode()) + "]";  
        if (!clientsLMap.isEmpty()) {  
            for (Map.Entry<Long, SocketChannel> entry : clientsLMap.entrySet()) {  
                SocketChannel temp = entry.getValue();  
                if (!client.equals(temp)) {  
                    sBuffer.clear();  
                    sBuffer.put((name + ":" + info).getBytes());  
                    sBuffer.flip();  
                    //输出到通道    
                    temp.write(sBuffer);  
                }  
            }  
        }  
    }  
      
    public void sendmsg(Long userId, String info) throws IOException  {
    	 //Socket s = client.socket();  
        System.out.println("info;;;"+info);
        SocketChannel client = clientsLMap.get(userId);
         sBuffer.clear();  
         sBuffer.put(info.getBytes());  
         sBuffer.flip();  
         System.out.println("ssss;;;;;;;;;;;;"+sBuffer.toString());
         System.out.println("client;;;;;;;;;;;;"+client);
         //输出到通道    
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
