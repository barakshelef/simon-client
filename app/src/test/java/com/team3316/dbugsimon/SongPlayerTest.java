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
    public void playNote_sanity() {
        try(MockedStatic<MediaPlayer> mediaPlayerClass = Mockito.mockStatic(MediaPlayer.class)) {
            mediaPlayerClass.when(new MockedStatic.Verification() {
                @Override
                public void apply() {
                    MediaPlayer.create(ctx, 1);
                }
            }).thenReturn(mediaPlayer);

            Mockito.doNothing().when(mediaPlayer)
                    .setOnCompletionListener(Mockito.any(MediaPlayer.OnCompletionListener.class));

            Mockito.doNothing().when(mediaPlayer)
                    .start();

            songPlayer = new SongPlayer(ctx);
            songPlayer.playNote(1);

            Mockito.verify(mediaPlayer).setOnCompletionListener(songPlayer);
            Mockito.verify(mediaPlayer).start();
        }
    }

    @Test
    public void playIndex_sanity() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playNote(Mockito.anyInt());
        Song song = new Song();
        song.notes = new int[]{2};
        spySongPlayer.setSong(song);
        spySongPlayer.playIndex(0);
        Mockito.verify(spySongPlayer).playNote(2);
    }

    @Test
    public void playIndex_outOfRange() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playNote(Mockito.anyInt());
        Song song = new Song();
        song.notes = new int[]{3,4};
        spySongPlayer.setSong(song);
        spySongPlayer.playIndex(2);
        Mockito.verify(spySongPlayer, Mockito.times(0))
                .playNote(Mockito.anyInt());
    }

    @Test
    public void playSong_sanity() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playIndex(Mockito.anyInt());
        Song song = new Song();
        song.notes = new int[]{5};
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
        Song song = new Song();
        song.notes = new int[]{6,7};
        spySongPlayer.setSong(song);
        spySongPlayer.playSong(1);
        spySongPlayer.onCompletion(mediaPlayer);
        spySongPlayer.onCompletion(mediaPlayer);
        Mockito.verify(spySongPlayer).playIndex(0);
        Mockito.verify(spySongPlayer).playIndex(1);
        Mockito.verify(spySongPlayer, Mockito.times(2))
                .playIndex(Mockito.anyInt());

    }

    @Test
    public void onCompletion_notPlaying() {
        SongPlayer spySongPlayer = Mockito.spy(songPlayer);
        Mockito.doNothing().when(spySongPlayer).playIndex(Mockito.anyInt());
        Song song = new Song();
        spySongPlayer.setSong(song);
        spySongPlayer.onCompletion(mediaPlayer);
        Mockito.verify(spySongPlayer, Mockito.times(0))
                .playIndex(Mockito.anyInt());
    }

}
