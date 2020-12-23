package com.team3316.dbugsimon;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class SimonClient {
    public interface NextHandler {
        void onMessage(int index);
    }

    public interface PlayHandler {
        void onMessage(int position);
    }

    public interface UsersHandler {
        void onMessage(int count);
    }

    public interface ErrorHandler {
        void onMessage(String message);
    }

    public enum MessageType {
        NEXT("next"),
        PLAY("play"),
        USERS("users"),
        ERROR("error");

        private static final Map<String, MessageType> BY_VALUE = new HashMap<>();

        static {
            for (MessageType t : values()) {
                BY_VALUE.put(t.value, t);
            }
        }

        public final String value;

        MessageType(String value) {
            this.value = value;
        }

        static MessageType fromValue(String value) {
            return BY_VALUE.get(value);
        }
    }

    public static class Message {
        private String type;     // One of: "next", "play", "users" or "error"
        private int index;       // for "next" message
        private int position;    // for "play" message
        @SuppressWarnings("unused")
        private int count;       // for "users" message -- sent only by server
        private String message;  // for "error" message

        public MessageType getType() {
            return MessageType.fromValue(type);
        }

        public void setType(MessageType type) {
            this.type = type.value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getCount() {
            return count;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    private WebSocketClient mWebSocketClient;
    private NextHandler nextHandler;
    private PlayHandler playHandler;
    private UsersHandler usersHandler;
    private ErrorHandler errorHandler;
    private Gson gson = new Gson();

    private void handleMessage(Message message) throws Exception {
        switch (message.getType()) {
            case NEXT:
                nextHandler.onMessage(message.getIndex());
                return;
            case PLAY:
                playHandler.onMessage(message.getPosition());
                return;
            case USERS:
                usersHandler.onMessage(message.getCount());
                return;
            case ERROR:
                errorHandler.onMessage(message.getMessage());
                return;
            default:
                throw new Exception("Unknown message type " + message.getType());
        }
    }

    public SimonClient(URI serverURI, NextHandler nextHandler, PlayHandler playHandler, UsersHandler usersHandler, ErrorHandler errorHandler) {
        this.nextHandler = nextHandler;
        this.playHandler = playHandler;
        this.usersHandler = usersHandler;
        this.errorHandler = errorHandler;

        mWebSocketClient = new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
            }

            @Override
            public void onMessage(String rawMessage) {
                Message message = gson.fromJson(rawMessage, Message.class);
                try {
                    handleMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };

        try {
            mWebSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void signalNext(int index) {
        Message message = new Message();
        message.setType(MessageType.NEXT);
        message.setIndex(index);
        mWebSocketClient.send(gson.toJson(message));
    }

    public void signalPlay(int position) {
        Message message = new Message();
        message.setType(MessageType.PLAY);
        message.setPosition(position);
        mWebSocketClient.send(gson.toJson(message));
    }

    public void signalError(String errorMessage) {
        Message message = new Message();
        message.setType(MessageType.ERROR);
        message.setMessage(errorMessage);
        mWebSocketClient.send(gson.toJson(message));
    }
}