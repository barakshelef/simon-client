package com.team3316.dbugsimon;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class SimonClient extends WebSocketClient{
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

        public Message(MessageType type) {
            this.type = type.value;
        }

        public MessageType getType() {
            if (type == null)
                return null;

            return MessageType.fromValue(type);
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

    private NextHandler nextHandler;
    private PlayHandler playHandler;
    private UsersHandler usersHandler;
    private ErrorHandler errorHandler;
    private Gson gson = new Gson();

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.printf("Opened connection: %d - %s\n",
                handshakedata.getHttpStatus(),
                handshakedata.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String rawMessage) {
        Message message;

        try {
            message = gson.fromJson(rawMessage, Message.class);
        } catch (JsonSyntaxException ex) {
            System.err.printf("Bad json format message: %s\n", rawMessage);
            return;
        }

        MessageType type = message.getType();
        if (type == null) {
            System.err.printf("Undefined message type: %s\n", rawMessage);
            return;
        }

        switch (type) {
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

        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.err.printf("Closed connection: %d - %s %s\n",
                code,
                reason,
                remote ? "remote" : "local");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public SimonClient(URI serverURI, NextHandler nextHandler, PlayHandler playHandler, UsersHandler usersHandler, ErrorHandler errorHandler) {
        super(serverURI);

        this.nextHandler = nextHandler;
        this.playHandler = playHandler;
        this.usersHandler = usersHandler;
        this.errorHandler = errorHandler;
    }

    public void signalNext(int index) {
        Message message = new Message(MessageType.NEXT);
        message.setIndex(index);
        send(gson.toJson(message));
    }

    public void signalPlay(int position) {
        Message message = new Message(MessageType.PLAY);
        message.setPosition(position);
        send(gson.toJson(message));
    }

    public void signalError(String errorMessage) {
        Message message = new Message(MessageType.ERROR);
        message.setMessage(errorMessage);
        send(gson.toJson(message));
    }
}