package com.team3316.dbugsimon;

public class SongStorage {
    // TODO: implement
    public int length = 0;

    private static SongStorage m_instance = null;

    public static SongStorage getInstance() {
        if (m_instance == null) {
            m_instance = new SongStorage();
        }
        return m_instance;
    }

    public Song getSong(int id) {
        // TODO: implement
        return new Song();
    }

}
