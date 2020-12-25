package com.team3316.dbugsimon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SongTest {
    @Test
    public void length_sanity() {
        assertEquals(new Song(new int[7]).length(), 7);
    }

    @Test
    public void getNote_sanity() {
        assertEquals(new Song(new int[]{1}).getNote(0), 1);
    }

    @Test
    public void getNote_outOfRange() {
        assertEquals(new Song(new int[]{2,3}).getNote(2), -1);
    }
}
