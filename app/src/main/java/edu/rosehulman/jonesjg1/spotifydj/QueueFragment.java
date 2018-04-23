package edu.rosehulman.jonesjg1.spotifydj;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueueFragment extends Fragment {
    private Player mPlayer;
    private Button buttonPausePlay;
    private OnFragmentInteractionListener mListener;

    public QueueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.queue_fragment, container, false);

        buttonPausePlay = view.findViewById(R.id.pause_button);
        buttonPausePlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mPlayer.getPlaybackState().isPlaying) {
                    mPlayer.pause(null);
                } else {
                    mPlayer.resume(null);
                }
                updatePausePlay();
            }
        });

        Button heyJude = view.findViewById(R.id.play_middle);
        Button stairwayHeaven = view.findViewById(R.id.play_top);
        Button hotelCalifornia = view.findViewById(R.id.play_bottom);

        heyJude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.playUri(null, "spotify:track:0aym2LBJBk9DAYuHHutrIl", 0, 0);
                updatePausePlay();
            }
        });

        stairwayHeaven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.playUri(null, "spotify:track:5CQ30WqJwcep0pYcV4AMNc", 0, 0);
                updatePausePlay();
            }
        });

        hotelCalifornia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.playUri(null, "spotify:track:40riOy7x9W7GXjyGp4pjAv", 0, 0);
                updatePausePlay();
            }
        });

        return view;
    }

    public void updatePausePlay() {
        String s;
        if (mPlayer.getPlaybackState().isPlaying) {
            s = getString(R.string.play);
        } else {
            s = getString(R.string.pause);
        }
        buttonPausePlay.setText(s);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
