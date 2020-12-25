package com.team3316.dbugsimon;

import android.content.Context;
import android.media.MediaPlayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SongPlayerTest {
    @Spy
    MediaPlayer mediaPlayer = new MediaPlayer();
    @Mock
    Context ctx;
    @InjectMocks
    SongPlayer songPlayer;

    @Test
    public void playIndex_sanity() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playNote(Mockito.anyInt());
        Song song = new Song(new int[]{2});
        spySongPlayer.setSong(song);
        spySongPlayer.playIndex(0);
        Mockito.verify(spySongPlayer).playNote(2);
    }

    @Test
    public void playIndex_outOfRange() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playNote(Mockito.anyInt());
        Song song = new Song(new int[]{3,4});
        spySongPlayer.setSong(song);
        spySongPlayer.playIndex(2);
        Mockito.verify(spySongPlayer, Mockito.times(0))
                .playNote(Mockito.anyInt());
    }

    @Test
    public void playSong_sanity() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playIndex(Mockito.anyInt());
        Song song = new Song(new int[]{5});
        spySongPlayer.setSong(song);
        spySongPlayer.playSong(0);
        Mockito.verify(spySongPlayer).playIndex(0);
        Mockito.verify(spySongPlayer, Mockito.times(1))
                .playIndex(Mockito.anyInt());
    }

    @Test
    public void playSong_noSongSet() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playIndex(Mockito.anyInt());
        spySongPlayer.playSong(0);
        Mockito.verify(spySongPlayer, Mockito.times(0))
                .playIndex(Mockito.anyInt());
    }

    @Test
    public void onCompletion_sanity() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playIndex(Mockito.anyInt());
        Mockito.doNothing().when(spySongPlayer).reset();
        Song song = new Song(new int[]{6,7});
        spySongPlayer.setSong(song);
        spySongPlayer.playSong(1);
        spySongPlayer.onCompletion(spySongPlayer);
        spySongPlayer.onCompletion(spySongPlayer);
        Mockito.verify(spySongPlayer).playIndex(0);
        Mockito.verify(spySongPlayer).playIndex(1);
        Mockito.verify(spySongPlayer, Mockito.times(2))
                .playIndex(Mockito.anyInt());

    }

    @Test
    public void onCompletion_notPlaying() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playIndex(Mockito.anyInt());
        Mockito.doNothing().when(spySongPlayer).reset();
        Song song = new Song(new int[]{});
        spySongPlayer.setSong(song);
        spySongPlayer.onCompletion(spySongPlayer);
        Mockito.verify(spySongPlayer, Mockito.times(0))
                .playIndex(Mockito.anyInt());
    }

}
