package com.team3316.dbugsimon.handlers;

import com.team3316.dbugsimon.SimonClient;

public class PrintHandler implements SimonClient.PlayHandler, SimonClient.ErrorHandler, SimonClient.UsersHandler, SimonClient.NextHandler {
    private String name;

    public PrintHandler(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(int i) {
        System.out.printf("%s: %d", name, i);
    }

    @Override
    public void onMessage(String s) {
        System.out.printf("%s: %s", name, s);

    }
}
