package edu.rosehulman.jonesjg1.spotifydj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;

/**
 * Created by jonesjg1 on 4/30/2018.
 */

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueViewHolder> {

    private ArrayList<Song> mSongs;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Player mPlayer;

    public QueueAdapter(Context context, RecyclerView recyclerView, Player player) {
        mContext = context;
        mRecyclerView = recyclerView;
        mSongs = new ArrayList<>();
        mPlayer = player;
    }

    public void addSong(Song song) {
        mSongs.add(song);
        notifyDataSetChanged();
    }

    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.song_view, parent, false);
        return new QueueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QueueViewHolder holder, int position) {
        holder.nameView.setText(mSongs.get(position).getmName());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class QueueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameView;

        public QueueViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.songName);
            nameView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mPlayer.playUri(null, mSongs.get(getAdapterPosition()).getmUri(), 0, 0);
        }
    }
}
