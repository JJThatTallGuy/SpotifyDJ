package edu.rosehulman.jonesjg1.spotifydj;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.Random;


public class QueueFragment extends Fragment {
    private Player mPlayer;
    private MainActivity mActivity;
    private Button buttonPausePlay;
    private OnFragmentInteractionListener mListener;
    private Random mRandom;

    public QueueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.queue_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mPlayer = ((MainActivity) getActivity()).getPlayer();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final QueueAdapter adapter = new QueueAdapter(getContext(), recyclerView, mPlayer,((MainActivity) getActivity()).getParty());
        ((MainActivity)getActivity()).getParty().setAdapter(adapter);
        recyclerView.setAdapter(adapter);

        mRandom = new Random();
        Button randbutton = view.findViewById(R.id.random_button);
        randbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randInt = mRandom.nextInt(4);
                if (randInt == 0) {
                    adapter.addSong(new Song("Hey Jude","spotify:track:0aym2LBJBk9DAYuHHutrIl",
                            ((MainActivity) getActivity()).getUserID()));
                } else if (randInt == 1) {
                    adapter.addSong(new Song("Stairway to Heaven","spotify:track:5CQ30WqJwcep0pYcV4AMNc",
                            ((MainActivity) getActivity()).getUserID()));
                } else {
                    adapter.addSong(new Song("Hotel California", "spotify:track:40riOy7x9W7GXjyGp4pjAv",
                            ((MainActivity) getActivity()).getUserID()));
                }
            }
        });

        Button searchbutton = view.findViewById(R.id.search_button);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.changeFragment(R.id.search_fragment);
            }
        });

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
//
//        Button heyJude = view.findViewById(R.id.play_middle);
//        Button stairwayHeaven = view.findViewById(R.id.play_top);
//        Button hotelCalifornia = view.findViewById(R.id.play_bottom);
//
//        heyJude.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPlayer.playUri(null, "spotify:track:0aym2LBJBk9DAYuHHutrIl", 0, 0);
//                updatePausePlay();
//            }
//        });
//
//        stairwayHeaven.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPlayer.playUri(null, "spotify:track:5CQ30WqJwcep0pYcV4AMNc", 0, 0);
//                updatePausePlay();
//            }
//        });
//
//        hotelCalifornia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPlayer.playUri(null, "spotify:track:40riOy7x9W7GXjyGp4pjAv", 0, 0);
//                updatePausePlay();
//            }
//        });

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

    @Override
    public void onPause() {
        super.onPause();
        mPlayer.pause(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.pause(null);
    }

    // BottomNavigation for buttons at bottom of queue

}
