package com.team3316.dbugsimon;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileDescriptor;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SongPlayerTest {
    @Mock
    Context context;
    @Mock
    Resources resources;

    SongPlayer songPlayer;

    @Before
    public void createSongPlayer() {
        songPlayer = spy(new SongPlayer(context));
    }

    @Test
    public void playNote_sanity() throws IOException {
        when(context.getResources()).thenReturn(resources);
        AssetFileDescriptor afd = mock(AssetFileDescriptor.class);
        FileDescriptor fd = mock(FileDescriptor.class);
        when(afd.getFileDescriptor()).thenReturn(fd);
        when(afd.getStartOffset()).thenReturn(0L);
        when(afd.getLength()).thenReturn(0L);
        when(resources.openRawResourceFd(1)).thenReturn(afd);
        doNothing().when(songPlayer).setDataSource(fd, 0L, 0L);
        doNothing().when(songPlayer).setOnCompletionListener(songPlayer);
        doNothing().when(songPlayer).prepare();
        doNothing().when(songPlayer).start();

        songPlayer.playNote(1);
        verify(songPlayer).prepare();
        verify(songPlayer).setOnCompletionListener(songPlayer);
        verify(songPlayer).start();
    }

    @Test
    public void playNote_compressedResource() throws IOException {
        when(context.getResources()).thenReturn(resources);
        when(resources.openRawResourceFd(anyInt())).thenReturn(null);

        songPlayer.playNote(1);
        verify(songPlayer, times(0)).prepare();
    }

    @Test
    public void playNote_IOException() throws IOException {
        when(context.getResources()).thenReturn(resources);
        AssetFileDescriptor afd = mock(AssetFileDescriptor.class);
        FileDescriptor fd = mock(FileDescriptor.class);
        when(afd.getFileDescriptor()).thenReturn(fd);
        when(afd.getStartOffset()).thenReturn(0L);
        when(afd.getLength()).thenReturn(0L);
        when(resources.openRawResourceFd(1)).thenReturn(afd);
        doThrow(IOException.class).when(songPlayer).setDataSource(fd,0L, 0L);

        songPlayer.playNote(1);
        verify(songPlayer, times(0)).prepare();
    }

    @Test
    public void playIndex_sanity() {
        doNothing().when(songPlayer).playNote(anyInt());
        Song song = new Song(new int[]{2});
        songPlayer.setSong(song);
        songPlayer.playIndex(0);
        verify(songPlayer).playNote(2);
    }

    @Test
    public void playIndex_outOfRange() {
        doNothing().when(songPlayer).playNote(anyInt());
        Song song = new Song(new int[]{3,4});
        songPlayer.setSong(song);
        songPlayer.playIndex(2);
        verify(songPlayer, times(0)).playNote(anyInt());
    }

    @Test
    public void playSong_sanity() {
        doNothing().when(songPlayer).playIndex(anyInt());
        Song song = new Song(new int[]{5});
        songPlayer.setSong(song);
        songPlayer.playSong(0);
        verify(songPlayer).playIndex(0);
        verify(songPlayer, times(1)).playIndex(anyInt());
    }

    @Test
    public void playSong_noSongSet() {
        doNothing().when(songPlayer).playIndex(anyInt());
        songPlayer.playSong(0);
        verify(songPlayer, times(0)).playIndex(anyInt());
    }

    @Test
    public void onCompletion_sanity() {
        doNothing().when(songPlayer).playIndex(anyInt());
        doNothing().when(songPlayer).reset();
        Song song = new Song(new int[]{6,7});
        songPlayer.setSong(song);
        songPlayer.playSong(1);
        songPlayer.onCompletion(songPlayer);
        songPlayer.onCompletion(songPlayer);
        verify(songPlayer).playIndex(0);
        verify(songPlayer).playIndex(1);
        verify(songPlayer, times(2)).playIndex(anyInt());

    }

    @Test
    public void onCompletion_notPlaying() {
        doNothing().when(songPlayer).playIndex(anyInt());
        doNothing().when(songPlayer).reset();
        Song song = new Song(new int[]{});
        songPlayer.setSong(song);
        songPlayer.onCompletion(songPlayer);
        verify(songPlayer, times(0)).playIndex(anyInt());
    }

}
