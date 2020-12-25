package com.team3316.dbugsimon;

import android.content.Context;
import android.media.MediaPlayer;

public class SongPlayer implements MediaPlayer.OnCompletionListener {
    private Context context;
    private int[] notes;
    private boolean isPlaying = false;
    private int index = 0;
    private int position = 0;

    public SongPlayer(Context context) {
        super();
        this.context = context;
    }

    public void setSong(Song song) {
        this.notes = song.notes;
    }

    public void playNote(int resid) {
        MediaPlayer player = MediaPlayer.create(context, resid);
        player.setOnCompletionListener(this);
        player.start();
    }

    public void playIndex(int index) {
        if (index >= notes.length) return;
        playNote(notes[index]);
    }

    public void playSong(int position) {
        if (this.notes == null) return;

        this.index = 0;
        this.position = position;
        this.isPlaying = true;
        playIndex(0);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (isPlaying && ++index <= position) {
            playIndex(index);
        } else {
            this.index = 0;
            this.position = 0;
            this.isPlaying = false;
        }
    }
}
