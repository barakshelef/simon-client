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
        state.setIndex(0);
        SongPlayer player = state.getSongPlayer();
        player.setOnSongCompletionListener(this);
        player.playSong(position);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (state.isMyIndex()) state.getSimonClient().signalNext(0);
    }
}
