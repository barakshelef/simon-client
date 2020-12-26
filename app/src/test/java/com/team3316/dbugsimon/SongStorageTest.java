package com.team3316.dbugsimon;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class SongStorageTest {
    Field songs;

    @Before
    public void setSongsAccessible() throws NoSuchFieldException {
        songs = SongStorage.class.getDeclaredField("songs");
        songs.setAccessible(true);
    }

    @Test
    public void length_sanity() throws IllegalAccessException {
        songs.set(SongStorage.getInstance(), new Song[5]);
        assertEquals(5, SongStorage.getInstance().length());
    }

    @Test
    public void getSong_sanity() throws IllegalAccessException {
        Song song = new Song(new int[0]);
        songs.set(SongStorage.getInstance(), new Song[]{null, null, song, null});
        assertEquals(SongStorage.getInstance().getSong(2), song);
    }

    @Test
    public void getSong_outOfRange() throws IllegalAccessException {
        Song song = new Song(new int[0]);
        songs.set(SongStorage.getInstance(), new Song[]{null, null, song, null});
        assertNull(SongStorage.getInstance().getSong(5));
    }

    @Test
    public void getInstance_sanity() throws NoSuchFieldException, IllegalAccessException {
        Field instance = SongStorage.class.getDeclaredField("m_instance");
        instance.setAccessible(true);
        instance.set(SongStorage.class, null);
        SongStorage fromGetInstance = SongStorage.getInstance();
        assertNotNull(fromGetInstance);
        assertEquals(fromGetInstance, instance.get(SongStorage.class));
    }
}
