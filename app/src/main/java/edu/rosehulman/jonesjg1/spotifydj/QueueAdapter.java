package edu.rosehulman.jonesjg1.spotifydj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
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
    private Song curSong;

    public QueueAdapter(Context context, RecyclerView recyclerView, Player player, Party party) {
        mContext = context;
        mRecyclerView = recyclerView;
        mSongs = new ArrayList<>();
        mPlayer = player;
        mParty = party;


        sRef = FirebaseDatabase.getInstance().getReference().child("Parties").child(mParty.getKey()).child("Songs");
        sRef.addChildEventListener(new PartyChildEventListener());


        mPlayer.addNotificationCallback(new Player.NotificationCallback() {
            @Override
            public void onPlaybackEvent(PlayerEvent playerEvent) {
//                if(playerEvent.equals(PlayerEvent.kSpPlaybackNotifyTrackChanged)){
                if (playerEvent.equals(PlayerEvent.kSpPlaybackNotifyAudioDeliveryDone)) {
                    if (mSongs.size() == 1) {
                        removeSong(curSong);
                        curSong = null;
                    } else if (mSongs.size() > 1) {
                        curSong = mSongs.get(1);
                        removeSong(mSongs.get(0));
                        mPlayer.playUri(null, curSong.getmUri(), 0, 0);
                    }

                }
            }

            @Override
            public void onPlaybackError(Error error) {

            }
        });

    }

    public ArrayList<Song> getmSongs() {
        return mSongs;
    }

    public void addSong(Song song) {

        for (Song s : mSongs) {
            if (s.getmName().equals(song.getmName()) && s.getmArtist().equals(song.getmArtist())) {


                Toast.makeText(mContext, "Song already in queue", Toast.LENGTH_LONG).show();
                return;
            }
            if (!((MainActivity) mContext).getUserID().equals(s.getmUserID())) {
                    Toast.makeText(mContext, "One song at a time", Toast.LENGTH_LONG).show();
                    return;

            }
        }
        sRef.push().setValue(song);
    }

    public void handleSkip() {
        if (mSongs.size() == 0) {
            return;
        }
        if (mSongs.size() == 1) {
            removeSong(mSongs.get(0));
            mPlayer.pause(null);
            curSong = null;
        } else {curSong = mSongs.get(1);
            removeSong(mSongs.get(0));

            mPlayer.playUri(null, curSong.getmUri(), 0, 0);
        }
        notifyDataSetChanged();

    }

    public void removeSong(Song song){
        sRef.child(song.getKey()).removeValue();
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
        holder.artistView.setText(mSongs.get(position).getmArtist());
        if (position == 0) {
            holder.playing.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class QueueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private TextView nameView;
        private TextView artistView;
        private ImageView playing;

        public QueueViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.songName);
            artistView = itemView.findViewById(R.id.songArtist);
            playing = itemView.findViewById(R.id.playing);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (mSongs.get(getAdapterPosition()).getmUserID().equals(((MainActivity) mContext).getUserID()) ||
                    mParty.getmOwner().id.equals(((MainActivity) mContext).getUserID())) {
                if(getAdapterPosition()==0){
                    return;
                }
                else {
                    MenuItem Remove = menu.add(Menu.NONE, 1, 1, "Remove");
                    Remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                                          @Override
                                                          public boolean onMenuItemClick(MenuItem item) {
                                                              Log.d("TAG", "Removed");

                                                              removeSong(mSongs.get(getAdapterPosition()));
                                                              return true;
                                                          }

                                                      }
                    );
                }
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class PartyChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Song song = dataSnapshot.getValue(Song.class);

            song.setKey(dataSnapshot.getKey());


            if (mParty.getmOwner().id.equals(((MainActivity) mContext).getUserID())) {
                if (mSongs.size()==0) {
                    curSong = song;
                    mPlayer.playUri(null, song.getmUri(), 0, 0);
                }

            }

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
                if (s.getKey().equals(key)) {
                    if(mSongs.indexOf(s)==0&&mParty.getmOwner().id.equals(((MainActivity)mContext).getUserID())){
                        handleSkip();
                    }
                    mSongs.remove(s);
                    notifyDataSetChanged();
                    return;
                }
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
