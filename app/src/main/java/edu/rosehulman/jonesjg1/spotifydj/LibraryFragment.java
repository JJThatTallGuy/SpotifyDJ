package edu.rosehulman.jonesjg1.spotifydj;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.TracksPager;




public class LibraryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private OnFragmentInteractionListener mListener;
    private LibraryAdapter mAdapter;
    public LibraryFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.library_recycler_view);
        this.mAdapter = new LibraryAdapter(getContext(), recyclerView, ((MainActivity)getActivity()).getParty().getAdapter());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(this.mAdapter);
        new getSongTask().execute();
        return view;
    }



    class getSongTask extends AsyncTask<Void, Void, Pager<SavedTrack>> {


        @Override
        protected Pager<SavedTrack> doInBackground(Void... voids) {
            SpotifyService SS = ((MainActivity)getActivity()).getWebAPI();

            Pager<SavedTrack> tp = SS.getMySavedTracks();
            return tp;
        }

        protected void onPostExecute(Pager<SavedTrack> tp){
            super.onPostExecute(tp);
            for(int i =0;i<tp.items.size();i++){
                mAdapter.add(tp.items.get(i));


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


}
