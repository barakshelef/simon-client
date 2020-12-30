package com.team3316.dbugsimon;

public class SongStorage {
    private static SongStorage m_instance = null;
    private Song[] songs;

    private SongStorage() {
        songs = new Song[]{
                new Song(new int[]{  // MaozTzur
                        R.raw.c4, R.raw.g3, R.raw.c4, R.raw.f4, R.raw.e4, R.raw.d4, R.raw.c4,
                        R.raw.g4, R.raw.g4, R.raw.a4, R.raw.d4, R.raw.e4, R.raw.f4, R.raw.e4,
                        R.raw.d4, R.raw.c4,
                        R.raw.g4, R.raw.g4, R.raw.g4, R.raw.a4, R.raw.b4, R.raw.c5, R.raw.g4,
                        R.raw.c5, R.raw.b4, R.raw.a4, R.raw.g4, R.raw.g4, R.raw.f4, R.raw.e4,
                        R.raw.f4, R.raw.d4,
                        R.raw.e4, R.raw.f4, R.raw.g4, R.raw.a4, R.raw.d4, R.raw.e4, R.raw.f4,
                        R.raw.e4, R.raw.c4, R.raw.a4, R.raw.g4, R.raw.f4, R.raw.e4, R.raw.d4,
                        R.raw.c4
                }),

                new Song(new int[]{  // HanukaHanuka
                        R.raw.g4, R.raw.e4, R.raw.g4, R.raw.g4, R.raw.e4, R.raw.g4,
                        R.raw.e4, R.raw.g4, R.raw.c5, R.raw.b4, R.raw.a4,
                        R.raw.f4, R.raw.d4, R.raw.f4, R.raw.f4, R.raw.d4, R.raw.f4,
                        R.raw.d4, R.raw.f4, R.raw.b4, R.raw.a4, R.raw.g4,
                        R.raw.g4, R.raw.e4, R.raw.g4, R.raw.g4, R.raw.e4, R.raw.g4,
                        R.raw.e4, R.raw.g4, R.raw.c5, R.raw.b4, R.raw.a4,
                        R.raw.b4, R.raw.b4, R.raw.b4, R.raw.b4, R.raw.b4, R.raw.b4,
                        R.raw.b4, R.raw.g4, R.raw.a4, R.raw.b4, R.raw.c5
                }),

                new Song(new int[]{  // BanuHohehLgaresh
                        R.raw.a4, R.raw.g4, R.raw.a4, R.raw.c5, R.raw.b4, R.raw.c5, R.raw.a4,
                        R.raw.a4, R.raw.g4, R.raw.a4, R.raw.c5, R.raw.b4, R.raw.c5, R.raw.a4,
                        R.raw.d5, R.raw.c5, R.raw.d5, R.raw.c5, R.raw.a4, R.raw.c5, R.raw.d5,
                        R.raw.d5, R.raw.c5, R.raw.d5, R.raw.c5, R.raw.a4, R.raw.c5, R.raw.d5,
                        R.raw.e5, R.raw.d5, R.raw.c5, R.raw.a4, R.raw.a4, R.raw.g4, R.raw.a4,
                        R.raw.e5, R.raw.d5, R.raw.c5, R.raw.e5, R.raw.d5, R.raw.c5, R.raw.a4
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
