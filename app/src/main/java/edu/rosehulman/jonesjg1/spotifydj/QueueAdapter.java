package edu.rosehulman.jonesjg1.spotifydj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private DatabaseReference sRef;
    private Party mParty;

    public QueueAdapter(Context context, RecyclerView recyclerView, Player player, Party party) {
        mContext = context;
        mRecyclerView = recyclerView;
        mSongs = new ArrayList<>();
        mPlayer = player;
        mParty = party;
        sRef = FirebaseDatabase.getInstance().getReference().child("Parties").child(mParty.getKey()).child("Songs");
        sRef.addChildEventListener(new PartyChildEventListener());
    }

    public void addSong(Song song) {
        sRef.push().setValue(song);
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.song_view, parent, false);
        return new QueueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QueueViewHolder holder, int position) {
        holder.nameView.setText(mSongs.get(position).getmName());
        holder.artistView.setText(mSongs.get(position).getmArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class QueueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameView;
        private TextView artistView;

        public QueueViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.songName);
            artistView = itemView.findViewById(R.id.songArtist);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mPlayer.playUri(null, mSongs.get(getAdapterPosition()).getmUri(), 0, 0);
        }
    }

    private class PartyChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Song song = dataSnapshot.getValue(Song.class);

            song.setKey(dataSnapshot.getKey());
            mSongs.add(song);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Song updatedSong = dataSnapshot.getValue(Song.class);
            for(Song song : mSongs){
                if(song.getKey().equals(key)){
                   song.setValues(updatedSong);
                    notifyDataSetChanged();
                    return;
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for(Song s : mSongs){
                mSongs.remove(s);
                notifyDataSetChanged();
                return;
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

}
