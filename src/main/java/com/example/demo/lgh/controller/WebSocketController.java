package com.example.demo.lgh.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{username}")
@Component
public class WebSocketController {
    //静态变量，用来记录当前的连接数。应该把他设计成线程安全的
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全set，用来存放每个客户端的对应的WebSocketController对象  ，
     * CopyOnWriteArraySet底层包含CopyOnWriteArrayList所以底层是动态数组
     */
    private static ConcurrentHashMap<String, WebSocketController> webSocketControllers = new ConcurrentHashMap<>();
    //与客户端的链接会话 通过session给客户端发送数据
    private Session session;
    private String username;

    /**
     * 链接建立后调用的方法
     *
     * @param param 用户名
     */
    @OnOpen
    public void onOpen(@PathParam(value = "username") String param, Session session) {
        this.session = session;
        username = param;
        webSocketControllers.put(username, this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 关闭连接时调用的
     */
    @OnClose
    public void onClose() {
        webSocketControllers.remove(username);
        subOnlineCount();
        System.out.println("有一条连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端信息时调用
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的信息:" + message);
        String[] strings = message.split("==");
        sendInfo(strings[0], strings[1]);
    }

    /**
     * 发送自定义消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public void sendInfo(String message, String to) throws IOException {
        if (webSocketControllers.get(to) != null)
            webSocketControllers.get(to).sendMessage(message);
        else
            webSocketControllers.get(username).sendMessage("该用户不在线");

    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }
}
