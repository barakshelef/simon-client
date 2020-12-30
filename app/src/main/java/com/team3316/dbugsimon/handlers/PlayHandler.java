package com.team3316.dbugsimon.handlers;

import android.media.MediaPlayer;

import com.team3316.dbugsimon.GameState;
import com.team3316.dbugsimon.SimonClient;
import com.team3316.dbugsimon.SongPlayer;


public class PlayHandler implements SimonClient.PlayHandler, MediaPlayer.OnCompletionListener {
    private GameState state;

    public PlayHandler(GameState state) {
        this.state = state;
    }

    @Override
    public void onMessage(int position) {
        SongPlayer player = state.getSongPlayer();
        player.setOnSongCompletionListener(this);
        player.playSong(position);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        state.getSimonClient().signalNext(0);
    }
}
