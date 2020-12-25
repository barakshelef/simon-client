package com.team3316.dbugsimon;

public class Song {
    private int[] notes;

    public Song(int[] notes) {
        this.notes = notes;
    }

    public int length() {
        return notes.length;
    }

    public int getNote(int i) {
        if (i >= length()) return -1;
        return notes[i];
    }
}
