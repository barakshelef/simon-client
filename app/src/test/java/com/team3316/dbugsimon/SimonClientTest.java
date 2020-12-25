package com.team3316.dbugsimon;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SimonClientTest {
    private SimonClient client;
    @Mock
    private SimonClient.NextHandler mockNextHandler;
    @Mock
    private SimonClient.PlayHandler mockPlayHandler;
    @Mock
    private SimonClient.ErrorHandler mockErrorHandler;
    @Mock
    private SimonClient.UsersHandler mockUsersHandler;

    @Test
    public void MessageType_value() {
        assertEquals("error", SimonClient.MessageType.ERROR.value);
    }

    @Test
    public void MessageType_fromValue() {
        assertEquals(SimonClient.MessageType.NEXT, SimonClient.MessageType.fromValue("next"));
    }

    @Test
    public void Message_getType() {
        SimonClient.Message msg = new SimonClient.Message(SimonClient.MessageType.NEXT);
        assertEquals(SimonClient.MessageType.NEXT, msg.getType());
    }

    @Test
    public void Message_getIndex() {
        SimonClient.Message msg = new SimonClient.Message(SimonClient.MessageType.NEXT);
        msg.setIndex(1);
        assertEquals(1, msg.getIndex());
    }

    @Test
    public void Message_getPosition() {
        SimonClient.Message msg = new SimonClient.Message(SimonClient.MessageType.PLAY);
        msg.setPosition(2);
        assertEquals(2, msg.getPosition());
    }

    @Test
    public void Message_getCount() {
        SimonClient.Message msg = new SimonClient.Message(SimonClient.MessageType.USERS);
        try {
            Field countField = msg.getClass().getDeclaredField("count");
            countField.setAccessible(true);
            countField.set(msg, 3);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(3, msg.getCount());
    }

    @Test
    public void Message_getMessage() {
        SimonClient.Message msg = new SimonClient.Message(SimonClient.MessageType.ERROR);
        msg.setMessage("4");
        assertEquals("4", msg.getMessage());
    }

    @Before
    public void createClient() throws URISyntaxException {
        client = new SimonClient(
                new URI("ws://localhost/"),
                mockNextHandler,
                mockPlayHandler,
                mockUsersHandler,
                mockErrorHandler);
    }

    @Test
    public void onOpen_sanity() {
        client.onOpen(new ServerHandshake() {
            @Override
            public short getHttpStatus() {
                return 200;
            }

            @Override
            public String getHttpStatusMessage() {
                return "OK";
            }

            @Override
            public Iterator<String> iterateHttpFields() {
                return null;
            }

            @Override
            public String getFieldValue(String name) {
                return null;
            }

            @Override
            public boolean hasFieldValue(String name) {
                return false;
            }

            @Override
            public byte[] getContent() {
                return new byte[0];
            }
        });
    }

    @Test
    public void onClose_sanity() {
        client.onClose(400, "BAD", true);
    }

    @Test
    public void onError_sanity() {
        client.onError(new Exception("Very Bad"));
    }

    @Test
    public void onMessage_NEXT_sanity() {
        Mockito.doNothing().when(mockNextHandler).onMessage(Mockito.anyInt());
        client.onMessage("{\"type\": \"next\", \"index\": 1}");
        Mockito.verify(mockNextHandler, Mockito.times(1)).onMessage(1);
    }

    @Test
    public void onMessage_PLAY_sanity() {
        Mockito.doNothing().when(mockPlayHandler).onMessage(Mockito.anyInt());
        client.onMessage("{\"type\": \"play\", \"position\": 2}");
        Mockito.verify(mockPlayHandler, Mockito.times(1)).onMessage(2);
    }

    @Test
    public void onMessage_ERROR_sanity() {
        Mockito.doNothing().when(mockErrorHandler).onMessage(Mockito.anyString());
        client.onMessage("{\"type\": \"error\", \"message\": \"BANANA\"}");
        Mockito.verify(mockErrorHandler, Mockito.times(1)).onMessage("BANANA");
    }

    @Test
    public void onMessage_USERS_sanity() {
        Mockito.doNothing().when(mockUsersHandler).onMessage(Mockito.anyInt());
        client.onMessage("{\"type\": \"users\", \"count\": 3}");
        Mockito.verify(mockUsersHandler, Mockito.times(1)).onMessage(3);
    }

    @Test
    public void onMessage_bad_json() {
        client.onMessage("asdas");
    }

    @Test
    public void onMessage_bad_message() {
        client.onMessage("{\"foo\": \"bar\"}");
    }

    @Test
    public void onMessage_bad_type() {
        client.onMessage("{\"type\": \"baz\"}");
    }

    @Test
    public void onMessage_full_message() {
        Mockito.doNothing().when(mockNextHandler).onMessage(Mockito.anyInt());
        client.onMessage("{\"type\": \"next\", \"index\": 4, \"position\": 5, \"count\": 6, \"message\": \"apple\"}");
        Mockito.verify(mockNextHandler, Mockito.times(1)).onMessage(4);
    }

    @Test
    public void signalNext_sanity() {
        SimonClient spyClient = Mockito.spy(client);
        Mockito.doNothing().when(spyClient).send(Mockito.anyString());
        spyClient.signalNext(7);
        Mockito.verify(spyClient).send("{\"type\":\"next\",\"index\":7,\"position\":0,\"count\":0}");
    }

    @Test
    public void signalPlay_sanity() {
        SimonClient spyClient = Mockito.spy(client);
        Mockito.doNothing().when(spyClient).send(Mockito.anyString());
        spyClient.signalPlay(8);
        Mockito.verify(spyClient).send("{\"type\":\"play\",\"index\":0,\"position\":8,\"count\":0}");
    }

    @Test
    public void signalError_sanity() {
        SimonClient spyClient = Mockito.spy(client);
        Mockito.doNothing().when(spyClient).send(Mockito.anyString());
        spyClient.signalError("9");
        Mockito.verify(spyClient).send("{\"type\":\"error\",\"index\":0,\"position\":0,\"count\":0,\"message\":\"9\"}");
    }

}
