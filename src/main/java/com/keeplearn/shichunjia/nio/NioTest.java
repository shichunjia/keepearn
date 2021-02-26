package com.keeplearn.shichunjia.nio;

import org.elasticsearch.common.ssl.SslVerificationMode;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioTest {


    public static void main3(String[] args) throws IOException{
        test();
    }

    public static void main(String[] args) throws IOException {
        //NIO对于serversocket逻辑上的封装
        //这里与BIO的不同就是这条通道是双向的
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8087));
        //将模型设置为非阻塞(这句代码很重要)
        ssc.configureBlocking(false);// ①服务端设置非阻塞模式(sockfd)

        System.out.println("server started, listening on :" + ssc.getLocalAddress());
        Selector selector = Selector.open();
        //对感兴趣的事件进行注册
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        //selector轮询的操作
        while(true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while(it.hasNext()) {
                SelectionKey key = it.next();
                handle(key);
                it.remove();
            }
        }
    }

    private static void test() throws IOException{
        RandomAccessFile aFile = new RandomAccessFile("D:/tempFile/世界末日.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        int num=0;
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            System.out.print(new String(buf.array(),"UTF-8"));
//            while(buf.hasRemaining()){
//
//            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    private static void handle(SelectionKey key) {
        //处理连接
        if(key.isAcceptable()) {//说明客户端想要与server进行连接
            try {
                // ServerSocketChannel channel
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept(); // 建立 SocketChannel 通道
                sc.configureBlocking(false);//只有将这个通道设为flase才不会阻塞   ②服务端与客户端建立的链接 第二次设置非阻塞(connfd)
                //new Client
                //
                //String hostIP = ((InetSocketAddress)sc.getRemoteAddress()).getHostString();

		/*
		log.info("client " + hostIP + " trying  to connect");
		for(int i=0; i<clients.size(); i++) {
			String clientHostIP = clients.get(i).clientAddress.getHostString();
			if(hostIP.equals(clientHostIP)) {
				log.info("this client has already connected! is he alvie " + clients.get(i).live);
				sc.close();
				return;
			}
		}*/
                //在通道上设置一个key，用来监控read这个事件
                sc.register(key.selector(), SelectionKey.OP_READ );
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            //处理数据的读取
        } else if (key.isReadable()) { //flip
            SocketChannel sc = null;
            try {
                sc = (SocketChannel)key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(512);
                buffer.clear();
                int len = sc.read(buffer);
                if(len != -1) {
                    System.out.println(new String(buffer.array(), 0, len));
                }
                ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
                sc.write(bufferToWrite);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(sc != null) {
                    try {
                        sc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
