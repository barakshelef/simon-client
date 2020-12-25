package com.team3316.dbugsimon;

public class SongStorage {
    private static SongStorage m_instance = null;
    private Song[] songs;

    private SongStorage() {
        songs = new Song[]{
                new Song(new int[]{  // Mary had a little lamb
                        R.raw.e4,
                        R.raw.d4,
                        R.raw.c4,
                        R.raw.d4,
                        R.raw.e4,
                        R.raw.e4,
                        R.raw.e4,
                        R.raw.d4,
                        R.raw.d4,
                        R.raw.d4,
                        R.raw.e4,
                        R.raw.g4,
                        R.raw.g4,
                        R.raw.e4,
                        R.raw.d4,
                        R.raw.c4,
                        R.raw.d4,
                        R.raw.e4,
                        R.raw.e4,
                        R.raw.e4,
                        R.raw.d4,
                        R.raw.d4,
                        R.raw.e4,
                        R.raw.d4,
                        R.raw.c4
                })
        };
    }

    public static SongStorage getInstance() {
        if (m_instance == null) {
            m_instance = new SongStorage();
        }
        return m_instance;
    }

    public int length() {
        return songs.length;
    }

    public Song getSong(int id) {
        if (id >= length()) return null;
        return songs[id];
    }

}
