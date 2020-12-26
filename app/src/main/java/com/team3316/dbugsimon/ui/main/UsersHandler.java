package com.team3316.dbugsimon.ui.main;

import com.team3316.dbugsimon.GameState;
import com.team3316.dbugsimon.SimonClient;


public class UsersHandler implements SimonClient.UsersHandler {
    private GameState state;
    private boolean playerNumberWasSet = false;

    public UsersHandler(GameState state) {
        this.state = state;
    }

    @Override
    public void onMessage(int count) {
        if (!playerNumberWasSet) {
            state.setPlayerNumber(count);
            playerNumberWasSet = true;
        }
        state.setTotalPlayers(count);
    }
}
