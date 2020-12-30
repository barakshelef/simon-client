package com.team3316.dbugsimon.handlers;

import com.team3316.dbugsimon.GameState;
import com.team3316.dbugsimon.SimonClient;


public class NextHandler implements SimonClient.NextHandler {
    private GameState state;

    public NextHandler(GameState state) {
        this.state = state;
    }

    @Override
    public void onMessage(int index) {
        state.setIndex(index - 1);
        if (state.isMyIndex()) state.getSongPlayer().playIndex(state.getIndex());
        state.setIndex(index);
    }
}
