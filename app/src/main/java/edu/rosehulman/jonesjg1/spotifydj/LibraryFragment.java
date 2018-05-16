package edu.rosehulman.jonesjg1.spotifydj;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.SavedTrack;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LibraryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private OnFragmentInteractionListener mListener;
    private LibraryAdapter mAdapter;
    private boolean mMySongsLoading = false;
    private int offset=0;
    private int counter=0;
    private int counter2=0;

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
        this.mAdapter = new LibraryAdapter(getContext(), recyclerView, ((MainActivity) getActivity()).getParty().getAdapter());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(this.mAdapter);
        if(((MainActivity)getActivity()).getSongs()==null) {
            new getSongTask().execute();
        }
        else{
            this.mAdapter.mTrackList = ((MainActivity)getActivity()).getSongs();
        }








//        offset++
        Log.d("COUNT",counter2+"");
        return view;
    }

    private void loadsongs(int offset){
        SpotifyService SS = ((MainActivity) getActivity()).getWebAPI();
        counter=0;


        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, offset*50);
        options.put(SpotifyService.LIMIT, 50);
        SS.getMySavedTracks(options,new Callback<Pager<SavedTrack>>() {
            @Override
            public void success(Pager<SavedTrack> savedTrackPager, Response response) {


//                List<Track> tracks = new ArrayList<>(savedTrackPager.items.size());
                for (SavedTrack savedTrack : savedTrackPager.items) {
                    counter+=1;
                    counter2++;
                    mAdapter.add(savedTrack);

                }
//                  mAdapter.addAll(savedTrackPager.total, mSavedSongsAdapter.size(), tracks);

                Log.d("TAGGGG" ,mAdapter.mTrackList.size()+"");
                mMySongsLoading = false;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("WIN",error+"");
                mMySongsLoading = false;
            }
        });
    }

    class getSongTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 68; i++) {
                loadsongs(offset++);
            }
            ((MainActivity)getActivity()).setSongs(mAdapter.mTrackList);
            return null;
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
