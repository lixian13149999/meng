package com.bcdbook.meng.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author summer
 * @Date 2017/9/12 下午3:16
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;
    //定义一个安全线程的集合, 用于存储session
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

//    private MessageVO messageVO = new MessageVO();

//    /**
//     * @author summer
//     * @date 2017/9/12 下午3:23
//     * @param session
//     * @return void
//     * @description 开启socket
//     */
//    @OnOpen
//    public void onOpen(Session session) {
//
//        this.session = session;
//        webSockets.add(this);//把socket对象加入到session栈中
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        this.sendMessage("[添加连接]");
//        log.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
//    }
//
//
//    /**
//     * @author summer
//     * @date 2017/9/12 下午3:30
//     * @param
//     * @return void
//     * @description 关闭此session
//     */
//    @OnClose
//    public void onClose() {
//
//        webSockets.remove(this);
//
//        this.sendMessage("[断开连接]");
//
//        log.info("【websocket消息】连接断开, 总数:{}", webSockets.size());
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//
//        this.sendMessage("[发送信息]");
//
//        log.info("【websocket消息】收到客户端发来的消息:{}", message);
//    }
//
//    public void sendMessage(String message) {
//        for (WebSocket webSocket : webSockets) {
//            log.info("【websocket消息】广播消息, message={}", message);
//            try {
//                webSocket.session.getBasicRemote().sendText(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private MessageVO messageVO = new MessageVO();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);


        messageVO.setType(1);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage("有新的连接");

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        this.sendMessage(Json);
        log.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
    }


    @OnClose
    public void onClose() {
        webSockets.remove(this);

        messageVO.setType(2);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage("有用户离开");

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        this.sendMessage(Json);


        log.info("【websocket消息】连接断开, 总数:{}", webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {

        messageVO.setType(3);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage(message);

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        this.sendMessage(Json);

        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
