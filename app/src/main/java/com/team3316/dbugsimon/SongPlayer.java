package com.team3316.dbugsimon;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class SongPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {
    private Context context;
    private Song song;
    private boolean isPlaying = false;
    private int index = 0;
    private int position = 0;

    public SongPlayer(Context context) {
        super();
        this.context = context;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void playNote(int resid) {
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
        if (afd == null) return;

        try {
            setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        setOnCompletionListener(this);
        start();
    }

    public void playIndex(int index) {
        int note = song.getNote(index);
        if (note < 0) return;
        playNote(note);
    }

    public void playSong(int position) {
        if (this.song == null) return;

        this.index = 0;
        this.position = position;
        this.isPlaying = true;
        playIndex(0);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        reset();

        if (isPlaying && ++index <= position) {
            playIndex(index);
        } else {
            this.index = 0;
            this.position = 0;
            this.isPlaying = false;
        }
    }
}
