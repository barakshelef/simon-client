package com.team3316.dbugsimon;

public class GameState {
    private int gameId;
    private Song song;
    private int playerNumber;

    private int[] songSequence = new int[]{};
    private int currentIndex = 0;
    private int currentPosition = 0;

    public GameState(int gameId, int playerNumber) {
        this.gameId = gameId;
        this.song = pickSong(gameId);
        this.playerNumber = playerNumber;
    }

    public void startGame(int totalPlayers) {
        this.songSequence = generateSequence(this.gameId, this.song.length, totalPlayers);
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

    private static Song pickSong(int seed) {
        // TODO: implement
        return new Song();
    }

    private static int[] generateSequence(int seed, int length, int players) {
        // TODO: implement
        return new int[]{};
    }

}
