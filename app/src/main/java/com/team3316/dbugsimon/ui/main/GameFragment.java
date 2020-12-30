package com.team3316.dbugsimon.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.team3316.dbugsimon.GameState;
import com.team3316.dbugsimon.R;

public class GameFragment extends Fragment {
    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_screen, container, false);
        view.findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((ImageButton) v).setImageResource(R.drawable.on);
                    GameState gs = GameState.getGameState();
                    if (gs.isMyIndex()) {
                        gs.getSongPlayer().playIndex(gs.getIndex());
                        gs.getSimonClient().signalNext(gs.getIndex() + 1);
                    } else {
                        gs.getSongPlayer().playNote(R.raw.d3);
                        gs.getSimonClient().signalError("Mistake!");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((ImageButton) v).setImageResource(R.drawable.off);
                }
                return true;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
