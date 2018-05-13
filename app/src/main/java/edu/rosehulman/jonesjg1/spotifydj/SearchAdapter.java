package edu.rosehulman.jonesjg1.spotifydj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by jonesjg1 on 5/13/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    ArrayList<Track> mTrackList = new ArrayList<Track>();
    private Context mContext;
    private QueueAdapter QAdapter;
    public SearchAdapter(Context context, RecyclerView recyclerView, QueueAdapter QAdapter){
        this.mContext = context;
        this.QAdapter = QAdapter;
    }



    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    View view = LayoutInflater.from(mContext).inflate(R.layout.song_view, parent, false);
        return new SearchViewHolder(view);
    }

    public void add(Track t){
        mTrackList.add(t);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.nameView.setText(mTrackList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameView;

        public SearchViewHolder(View itemView) {

            super(itemView);

            nameView = itemView.findViewById(R.id.songName);
            nameView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Track tempTrack = mTrackList.get(getAdapterPosition());
            Song newSong = new Song(tempTrack.name,tempTrack.uri,((MainActivity)mContext).getUserID());
            QAdapter.addSong(newSong);
        }
    }
}
