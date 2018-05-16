package edu.rosehulman.jonesjg1.spotifydj;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.Random;


public class QueueFragment extends Fragment {
    private Player mPlayer;
    private MainActivity mActivity;
    private Button buttonPausePlay;
    private Party mParty;

    private OnFragmentInteractionListener mListener;

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
        mParty = ((MainActivity) getActivity()).getParty();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        if (((MainActivity) getActivity()).getParty().getAdapter() != null) {
            recyclerView.setAdapter(((MainActivity) getActivity()).getParty().getAdapter());
        } else {
            final QueueAdapter adapter = new QueueAdapter(getContext(), recyclerView, mPlayer, mParty);
            ((MainActivity) getActivity()).getParty().setAdapter(adapter);
            recyclerView.setAdapter(adapter);
        }

        Button searchbutton = view.findViewById(R.id.search_button);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.changeFragmentNoBS(R.id.search_fragment);

            }
        });

        ImageView skipButton = view.findViewById(R.id.skipButton);
        if (((MainActivity) getActivity()).getUserID().equals(mParty.getmOwner().id)) {
            skipButton.setVisibility(View.VISIBLE);
            skipButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).getParty().getAdapter().handleSkip();
                }
            });
        }

        buttonPausePlay = view.findViewById(R.id.pause_button);
        if (((MainActivity) getActivity()).getUserID().equals(mParty.getmOwner().id)) {
            buttonPausePlay.setVisibility(View.VISIBLE);
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
        }

       Button buttonLibrary = view.findViewById(R.id.library_button);
        buttonLibrary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mListener.changeFragment(R.id.library_fragment);
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

    @Override
    public void onPause() {
        super.onPause();
//        mPlayer.pause(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mPlayer.pause(null);
    }

    // BottomNavigation for buttons at bottom of queue

}
