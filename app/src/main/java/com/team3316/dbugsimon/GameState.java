package com.team3316.dbugsimon;

import java.util.Random;

public class GameState {
    static private GameState mGameState;

    private long gameId;
    private Song song;

    private int totalPlayers = 0;
    private int playerIndex;

    private int[] songSequence = new int[]{};
    private int currentIndex = 0;
    private int currentPosition = 0;

    private SimonClient simonClient;
    private SongPlayer songPlayer;

    public SimonClient getSimonClient() {
        return simonClient;
    }

    public void setSimonClient(SimonClient simonClient) {
        this.simonClient = simonClient;
    }

    public SongPlayer getSongPlayer() {
        return songPlayer;
    }

    public void setSongPlayer(SongPlayer songPlayer) {
        this.songPlayer = songPlayer;
        this.songPlayer.setSong(song);
    }

    public GameState(long gameId) {
        this.gameId = gameId;
        this.song = pickSong(gameId);

        mGameState = this;
    }

    public static GameState getGameState() {
        return mGameState;
    }

    public void setPlayerNumber(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getPlayerNumber() {
        return playerIndex + 1;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers + 1;
    }

    public void startGame() {
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

    public int getIndex() {
        return currentIndex;
    }

    public int getPosition() {
        return currentPosition;
    }

    public boolean isMyIndex() {
        if (currentIndex >= songSequence.length) return false;

        return songSequence[currentIndex] == playerIndex;
    }

    public boolean isMyPosition() {
        if (currentPosition >= songSequence.length) return false;

        return songSequence[currentPosition] == playerIndex;
    }

    private static Song pickSong(long seed) {
        Random random = new Random(seed);
        SongStorage storage = SongStorage.getInstance();
        return storage.getSong(random.nextInt(storage.length()));
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
