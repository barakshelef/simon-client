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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.team3316.dbugsimon.R;
import com.team3316.dbugsimon.SimonClient;
import com.team3316.dbugsimon.handlers.PrintHandler;

import java.net.URI;
import java.net.URISyntaxException;

public class JoinFragment extends Fragment {
    SimonClient client;
    View joinScreen;

    public static JoinFragment newInstance() {
        return new JoinFragment();
    }

    @SuppressLint("DefaultLocale")
    static private URI createURI(View view) throws URISyntaxException {
        String serverIp = "localhost";
        long gameId = 0;
        int port = 3316;
        EditText editText;

        editText = ((TextInputLayout) view.findViewById(R.id.ServerIP)).getEditText();
        if (editText != null) serverIp = editText.getText().toString();

        editText = ((TextInputLayout) view.findViewById(R.id.GameID)).getEditText();
        if (editText != null) gameId = Long.parseLong(editText.getText().toString());

        return new URI(String.format("ws://%s:%d/%d", serverIp, port, gameId));
    }

    private void _connect() {
        try {
            client = new SimonClient(
                    createURI(joinScreen),
                    new PrintHandler("next"),
                    new PrintHandler("play"),
                    new PrintHandler("user"),
                    new PrintHandler("error"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (!client.isOpen()) {
            Snackbar.make(
                    joinScreen,
                    "Unable to connect to server",
                    BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            // TODO: move to next fragment
            System.out.println("Connected!");
        }
    }

    private void _disconnect() {
        client.close();
        client = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        joinScreen = inflater.inflate(R.layout.join_screen, container, false);
        Button joinButton = (Button) joinScreen.findViewById(R.id.JoinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                if (client != null && client.isOpen()) {
                    _disconnect();
                    button.setText(R.string.join);
                } else {
                    _connect();
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