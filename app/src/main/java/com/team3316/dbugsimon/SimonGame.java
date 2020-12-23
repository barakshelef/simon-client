package com.team3316.dbugsimon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.team3316.dbugsimon.ui.main.JoinFragment;

public class SimonGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, JoinFragment.newInstance())
                    .commitNow();
        }
    }
}