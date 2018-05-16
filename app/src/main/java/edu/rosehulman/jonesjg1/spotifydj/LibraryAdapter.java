package edu.rosehulman.jonesjg1.spotifydj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by jjone on 5/13/2018.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {
    ArrayList<SavedTrack> mTrackList = new ArrayList<SavedTrack>();
    private Context mContext;
    private QueueAdapter QAdapter;
    private RecyclerView mRecyclerView;

    public LibraryAdapter(Context context, RecyclerView recyclerView, QueueAdapter QAdapter){
        this.mContext = context;
        this.QAdapter = QAdapter;
        mRecyclerView = recyclerView;
    }

    public void clearAll() {
        mTrackList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.song_view, parent, false);
        return new LibraryViewHolder(view);
    }



    public void add(SavedTrack t){
        mTrackList.add(t);
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    public void print(){
//        for(int i =0;i< mTrackList.size();i++){
            Log.d("TAG",mTrackList.size()+"");
//        }
    }

    @Override
    public void onBindViewHolder(LibraryViewHolder holder, int position) {
        holder.nameView.setText(mTrackList.get(position).track.name);
        holder.artistView.setText(mTrackList.get(position).track.artists.get(0).name);
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameView;
        private TextView artistView;

        public LibraryViewHolder(View itemView) {

            super(itemView);

            nameView = itemView.findViewById(R.id.songName);
            artistView = itemView.findViewById(R.id.songArtist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SavedTrack tempTrack = mTrackList.get(getAdapterPosition());
            Song newSong = new Song(tempTrack.track.name,tempTrack.track.uri,((MainActivity)mContext).getUserID(), tempTrack.track.artists.get(0).name);
            QAdapter.addSong(newSong);
            ((MainActivity) mContext).changeFragment(R.id.queue_fragment);
        }
    }
}