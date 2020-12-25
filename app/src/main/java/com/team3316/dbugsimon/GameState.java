package com.team3316.dbugsimon;

import java.util.Random;

public class GameState {
    private long gameId;
    private Song song;
    private int playerNumber;

    private int[] songSequence = new int[]{};
    private int currentIndex = 0;
    private int currentPosition = 0;

    public GameState(long gameId, int playerNumber) {
        this.gameId = gameId;
        this.song = pickSong(gameId);
        this.playerNumber = playerNumber;
    }

    public void startGame(int totalPlayers) {
        this.songSequence = generateSequence(this.gameId, this.song.length(), totalPlayers);
    }

    public void setIndex(int index) {
        this.currentIndex = index;
    }

    public void setPosition(int position) {
        this.currentPosition = position;
    }

    public void resetState() {
        this.currentIndex = 0;
        this.currentPosition = 0;
    }

    public boolean isMyIndex() {
        if (currentIndex >= songSequence.length) return false;

        return songSequence[currentIndex] == playerNumber;
    }

    public boolean isMyPosition() {
        if (currentPosition >= songSequence.length) return false;

        return songSequence[currentPosition] == playerNumber;
    }

    private static Song pickSong(long seed) {
        Random random = new Random(seed);
        SongStorage storage = SongStorage.getInstance();
        return storage.getSong(random.nextInt(storage.length));
    }

    private static int[] generateSequence(long seed, int length, int players) {
        Random random = new Random(seed);
        int[] sequence = new int[length];
        for (int i=0; i<length; i++) {
            sequence[i] = random.nextInt(players);
        }
        return sequence;
    }

}
