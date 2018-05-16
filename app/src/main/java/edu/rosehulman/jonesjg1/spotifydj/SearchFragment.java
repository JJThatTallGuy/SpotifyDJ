package edu.rosehulman.jonesjg1.spotifydj;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.SpotifyPlayer;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;


public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private SearchView songsearch;
    private SearchAdapter mAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.search_recycler_view);
       this.mAdapter = new SearchAdapter(getContext(), recyclerView, ((MainActivity)getActivity()).getParty().getAdapter());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

       recyclerView.setAdapter(this.mAdapter);



        this.songsearch = view.findViewById(R.id.searchview);
        this.songsearch.setIconified(false);
        this.songsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Log.d("TAG", songsearch.getQuery().toString());

                mAdapter.clearAll();
                new getSongTask().execute(songsearch.getQuery().toString());


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("TAG", songsearch.getQuery().toString());
                if(!s.isEmpty()) {
                    new getSongTask().execute(songsearch.getQuery().toString());
                }
                mAdapter.clearAll();


                return true;

            }
        });
        return view;
    }


    class getSongTask extends AsyncTask<String, Void, TracksPager>{

        @Override
        protected TracksPager doInBackground(String... strings) {
            String search = strings[0];
            SpotifyService SS = ((MainActivity)getActivity()).getWebAPI();

            TracksPager tp = SS.searchTracks(search);
            return tp;
        }
        protected void onPostExecute(TracksPager tp){
            super.onPostExecute(tp);
            for(int i =0;i<tp.tracks.items.size();i++){
                mAdapter.add(tp.tracks.items.get(i));


            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
