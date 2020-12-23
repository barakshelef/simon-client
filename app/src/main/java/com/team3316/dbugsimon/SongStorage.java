package com.team3316.dbugsimon;

public class SongStorage {
    // TODO: implement
    public int length = 0;

    static SongStorage m_instance = null;

    public SongStorage() {
        m_instance = this;
    }

    public static SongStorage getInstance() {
        return m_instance;
    }

    public Song getSong(int id) {
        // TODO: implement
        return new Song();
    }

}
