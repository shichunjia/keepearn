package com.keeplearn.shichunjia.nio.chatdemo;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * nio 实现聊天代码
 */
public class NioService {


    // 用于字符集编解码
    private Charset charset = Charset.forName("GBK");
    /**
     * 读缓冲区
     */
    private ByteBuffer readBuffer=ByteBuffer.allocate(1024);
    /**
     * 写 缓冲区
     */
    private ByteBuffer writeBuffer=ByteBuffer.allocate(1024);
    /**
     * 用来存放 需要发送消息的客户端
     */
    private Map<String,SocketChannel>  clientMap=new HashMap<String,SocketChannel>();
    /**
     * 选择器
     */
    private static Selector selector;

    public NioService(int point){
        try {
            init(point);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NioService nioService=new NioService(8088);
        nioService.listen();
    }


    // 进行服务端初始化
    private void init(int point) throws IOException {
        // SocketChannel open
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(point));
        //设置服务端 进行非阻塞
        serverSocketChannel.configureBlocking(false);
        // selector  进行绑定
        selector = Selector.open();
        // 对事件进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("====服务端启动："+point);
    }

    // 开启监听事件
    private void listen(){
        while (true){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    handle(selectionKey);
                });
                selectionKeys.clear();
            }catch (IOException e){
                e.printStackTrace();
            }


        }
    }

    /**
     * 服务端 处理 监听到的事件
     * @param selectionKey
     * @throws IOException
     */
    private void handle(SelectionKey selectionKey){
        try {
            if(selectionKey.isAcceptable()){
                // 接收客户端 ， 放入集合中
                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel= channel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selectionKey.selector(),SelectionKey.OP_READ);
                clientMap.put(getClientName(socketChannel),socketChannel);
            }else if(selectionKey.isReadable()){
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                readBuffer.clear();
                int read = socketChannel.read(readBuffer);
                if(read!=-1){
                    readBuffer.flip();
                    String message = String.valueOf(charset.decode(readBuffer));
                    System.out.println(socketChannel.toString());
                    dispatch(socketChannel, message);
                }
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * 进行消息下发
     * @param socketChannel
     * @param message
     * @throws IOException
     */
    private void dispatch(SocketChannel socketChannel, String message) throws IOException{
        if (!clientMap.isEmpty()) {
            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                SocketChannel temp = entry.getValue();
                if (!clientMap.equals(temp)) {
                    writeBuffer.clear();
                    writeBuffer.put(charset.encode(getClientName(socketChannel) + ":" + message));
                    writeBuffer.flip();
                    temp.write(writeBuffer);
                }
            }
        }
    }

    /**
     * 生成客户端名字
     */
    private String getClientName(SocketChannel client){
        Socket socket = client.socket();
        return "[" + socket.getInetAddress().toString().substring(1) + ":" + Integer.toHexString(client.hashCode()) + "]";
    }


}




