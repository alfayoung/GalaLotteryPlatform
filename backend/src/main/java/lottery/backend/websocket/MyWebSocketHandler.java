package lottery.backend.websocket;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MyWebSocketHandler implements WebSocketHandler {
    private static final int queueSize = 100;
    private static int onlineCount = 0;
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static final Map<String, String> userNames = new ConcurrentHashMap<>();

    private static final Queue<String> latestMsg = new ConcurrentLinkedDeque<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        addOnlineCount();
        String jAccount = session.getAttributes().get("jAccount").toString();
        String userName = session.getAttributes().get("userName").toString();
        WebSocketSession existingSession = sessions.get(jAccount);
        if (existingSession != null) {
            existingSession.close();
        }
        // 同步更新最新的消息//
        // WARNING：有可能存在进程互斥问题！！！
        sessions.put(jAccount, session);
        userNames.put(jAccount, userName);
        for (String msg : latestMsg) {
            sendTo(msg, jAccount);
        }
        System.out.println(String.format("成功建立连接~ jAccount: %s sessionID: %s", jAccount, session.getId()));
    }
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        broadcast(msg);
        latestMsg.add(msg);
        if (latestMsg.size() > queueSize) {
            latestMsg.poll();
        }
        System.out.println(msg);
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("连接出错");
        if (session.isOpen()) {
            session.close();
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        subOnlineCount();
        System.out.println("连接已关闭,status:" + closeStatus);
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
    public void sendTo(String message, String jAccount) throws IOException {
        WebSocketSession session = sessions.get(jAccount);
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
    public static synchronized void addOnlineCount() {
        MyWebSocketHandler.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        MyWebSocketHandler.onlineCount--;
    }
}