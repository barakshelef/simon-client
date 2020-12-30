package com.team3316.dbugsimon.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.team3316.dbugsimon.GameState;
import com.team3316.dbugsimon.R;
import com.team3316.dbugsimon.SimonClient;
import com.team3316.dbugsimon.handlers.PrintHandler;
import com.team3316.dbugsimon.handlers.UsersHandler;

import java.net.URI;
import java.net.URISyntaxException;

public class JoinFragment extends Fragment {
    SimonClient client;
    View joinScreen;
    GameState gameState;

    public static JoinFragment newInstance() {
        return new JoinFragment();
    }

    private long getGameId() {
        EditText editText = ((TextInputLayout) joinScreen.findViewById(R.id.game_id_input)).getEditText();
        if (editText == null) return 0;

        return Long.parseLong(editText.getText().toString());
    }

    private String getServerIp() {
        EditText editText = ((TextInputLayout) joinScreen.findViewById(R.id.server_ip_input)).getEditText();
        if (editText == null) return "localhost";

        return editText.getText().toString();
    }

    @SuppressLint("DefaultLocale")
    private URI createURI() throws URISyntaxException {
        String serverIp = getServerIp();
        long gameId = getGameId();
        int port = 3316;

        return new URI(String.format("ws://%s:%d/%d", serverIp, port, gameId));
    }

    private boolean _connect() {
        gameState = new GameState(getGameId());
        try {
            client = new SimonClient(
                    createURI(),
                    new PrintHandler("next"),
                    new PrintHandler("play"),
                    new UsersHandler(gameState),
                    new PrintHandler("error"));
            client.connectBlocking();
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!client.isOpen()) {
            Snackbar.make(
                    joinScreen,
                    "Unable to connect to server",
                    BaseTransientBottomBar.LENGTH_SHORT).show();
            return false;
        } else {
            Fragment fragment = new StartFragment();

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            System.out.println("Connected!");
            return true;
        }
    }

    private boolean _disconnect() {
        client.close();
        client = null;
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        joinScreen = inflater.inflate(R.layout.join_screen, container, false);
        Button joinButton = joinScreen.findViewById(R.id.join_btn);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                if (client != null && client.isOpen()) {
                    if (_disconnect())
                        button.setText(R.string.join);
                } else {
                    if (_connect())
                        button.setText(R.string.leave);
                }
            }
        });
        return joinScreen;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}