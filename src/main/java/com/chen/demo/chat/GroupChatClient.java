package com.chen.demo.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            //连接服务器
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);
            //注册
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("客户端: " + username + ",准备就绪...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器发送数据
     *
     * @param info
     */
    public void sendInfo(String info) {
        info = username + "说: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取服务端回复的消息
     */
    public void readInfo() {
        try {
            //有可用通道
            if (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读取到的缓冲区数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove(); //删除当前的selectionKey，防止重复操作
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main1(String[] args) {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        //启动一个线程,每隔3秒，读取从服务器端发送的数据
        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            chatClient.sendInfo(scanner.nextLine());
        }

    }
    // 保存客户端连接
    static List<SocketChannel> channelList = new ArrayList<>();
    //public static void main(String[] args) throws IOException {
    //
    //    // 创建NIO ServerSocketChannel,与BIO的serverSocket类似
    //    ServerSocketChannel serverSocket = ServerSocketChannel.open();
    //    serverSocket.socket().bind(new InetSocketAddress(9000));
    //    // 设置ServerSocketChannel为非阻塞
    //    serverSocket.configureBlocking(false);
    //    System.out.println("服务启动成功");
    //
    //    while (true) {
    //        // 非阻塞模式accept方法不会阻塞，否则会阻塞
    //        // NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数
    //        SocketChannel socketChannel = serverSocket.accept();
    //        if (socketChannel != null) { // 如果有客户端进行连接
    //            System.out.println("连接成功");
    //            // 设置SocketChannel为非阻塞
    //            socketChannel.configureBlocking(false);
    //            // 保存客户端连接在List中
    //            channelList.add(socketChannel);
    //        }
    //        // 遍历连接进行数据读取
    //        Iterator<SocketChannel> iterator = channelList.iterator();
    //        while (iterator.hasNext()) {
    //            SocketChannel sc = iterator.next();
    //            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
    //            // 非阻塞模式read方法不会阻塞，否则会阻塞
    //            int len = sc.read(byteBuffer);
    //            // 如果有数据，把数据打印出来
    //            if (len > 0) {
    //                System.out.println("接收到消息：" + new String(byteBuffer.array()));
    //            } else if (len == -1) { // 如果客户端断开，把socket从集合中去掉
    //                iterator.remove();
    //                System.out.println("客户端断开连接");
    //            }
    //        }
    //    }
    //}
    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(9000));
        channel.configureBlocking(false);
        Set<SocketChannel> list = new HashSet<>();
        byte[] bytes = new byte[1024];
        while (true) {
            SocketChannel accept1 = channel.accept();
            if (accept1 != null) {
                System.out.println("连接成功................");
                list.add(accept1);
            }
            Iterator<SocketChannel> iterator = list.iterator();
            while (iterator.hasNext()) {
                SocketChannel next = iterator.next();
                next.configureBlocking(false);
                ByteBuffer allocate = ByteBuffer.allocate(128);
                int read = next.read(allocate);
                if (read > 0 ) {
                    System.out.println(new String(allocate.array()));
                } else if (read == -1){
                    System.out.println("..............");
                }
            }
        }
    }
}
