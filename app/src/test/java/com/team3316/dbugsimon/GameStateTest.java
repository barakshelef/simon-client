package com.team3316.dbugsimon;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GameStateTest {
    private static SongStorage songStorage;
    @BeforeClass
    public static void setupSongStorage() throws NoSuchFieldException, IllegalAccessException {
        SongStorage originalSongStorage = SongStorage.getInstance();
        Field instance = originalSongStorage.getClass().getDeclaredField("m_instance");
        instance.setAccessible(true);
        songStorage = Mockito.spy(originalSongStorage);
        instance.set(originalSongStorage, songStorage);
    }

    @Test
    public void pickSong_sanity() {
        songStorage.length = 10;
        new GameState(1, 2);
        Mockito.verify(songStorage).getSong(5);
    }

    @Test
    public void startGame_sanity() {
        songStorage.length = 10;
        Song testSong = new Song(new int[5]);
        Mockito.when(songStorage.getSong(Mockito.anyInt())).thenReturn(testSong);
        GameState state = new GameState(1,0);
        state.startGame(3);
        state.setIndex(0);
        assertTrue(state.isMyIndex());
        state.setIndex(1);
        assertFalse(state.isMyIndex());
        state.setIndex(2);
        assertFalse(state.isMyIndex());
        state.setIndex(3);
        assertTrue(state.isMyIndex());
        state.setIndex(4);
        assertFalse(state.isMyIndex());
    }

    @Test
    public void isMyIndex_sanity() {
        songStorage.length = 1;
        Song testSong = new Song(new int[1]);
        Mockito.when(songStorage.getSong(Mockito.anyInt())).thenReturn(testSong);
        GameState state = new GameState(1,0);
        state.startGame(1);
        state.setIndex(0);
        assertTrue(state.isMyIndex());
    }

    @Test
    public void isMyPosition_sanity() {
        songStorage.length = 1;
        Song testSong = new Song(new int[1]);
        Mockito.when(songStorage.getSong(Mockito.anyInt())).thenReturn(testSong);
        GameState state = new GameState(1,0);
        state.startGame(1);
        state.setPosition(0);
        assertTrue(state.isMyPosition());
    }

    @Test
    public void isMyIndex_outOfRange() {
        songStorage.length = 1;
        Song testSong = new Song(new int[1]);
        Mockito.when(songStorage.getSong(Mockito.anyInt())).thenReturn(testSong);
        GameState state = new GameState(1,0);
        state.startGame(1);
        state.setIndex(1);
        assertFalse(state.isMyIndex());
    }

    @Test
    public void isMyPosition_outOfRange() {
        songStorage.length = 1;
        Song testSong = new Song(new int[1]);
        Mockito.when(songStorage.getSong(Mockito.anyInt())).thenReturn(testSong);
        GameState state = new GameState(1,0);
        state.startGame(1);
        state.setPosition(1);
        assertFalse(state.isMyPosition());
    }

    @Test
    public void resetState() {
        songStorage.length = 1;
        Song testSong = new Song(new int[1]);
        Mockito.when(songStorage.getSong(Mockito.anyInt())).thenReturn(testSong);
        GameState state = new GameState(1,0);
        state.startGame(1);
        state.setIndex(1);
        state.setPosition(1);
        state.resetState();
        assertTrue(state.isMyIndex());
        assertTrue(state.isMyPosition());
    }
}
