package edu.rosehulman.jonesjg1.spotifydj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QueueListAdapter extends RecyclerView.Adapter<QueueListAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<Party> mParties;
    private DatabaseReference mPartiesRef;

    public QueueListAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        mRecyclerView = recyclerView;
        mParties = new ArrayList<>();
        this.mPartiesRef = FirebaseDatabase.getInstance().getReference().child("Parties");
        mPartiesRef.addChildEventListener(new PartyChildEventListener());

    }

    public void addParty(Party party) {
        mPartiesRef.push().setValue(party);
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.party_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.party = mParties.get(position);
        holder.mName.setText(mParties.get(position).getmName());
        holder.mMembers.setText(mParties.get(position).getmMembers() + "");
        if (!mParties.get(position).ismIsPasswordProtected()) {
            holder.mLocked.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mParties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Party party;
        private TextView mName;
        private ImageView mLocked;
        private TextView mMembers;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.queueName);
            mLocked = itemView.findViewById(R.id.queuelocked);
            mMembers = itemView.findViewById(R.id.numMembers);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder Abuilder = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_TRADITIONAL);
            Abuilder.setTitle("Party Info");
            View popup = LayoutInflater.from(mContext).inflate(R.layout.party_signin_alert_dialog,null ,false);
            Abuilder.setView(popup);

            TextView name = popup.findViewById(R.id.queueName);
            TextView nummemebers = popup.findViewById(R.id.numMembers);

            name.setText(party.getmName());
            nummemebers.setText(party.getmMembers()+"");



            Abuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ((MainActivity) mContext).changeFragment(R.id.queue_fragment);
                }
            });

            Abuilder.setNegativeButton(android.R.string.cancel,null);
            Abuilder.create().show();
        }
    }

    private class PartyChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Party party = dataSnapshot.getValue(Party.class);
            party.setKey(dataSnapshot.getKey());
            mParties.add(party);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Party updatedParty = dataSnapshot.getValue(Party.class);
            for(Party p : mParties){
                if(p.getKey().equals(key)){
                    p.setValues(updatedParty);
                    notifyDataSetChanged();
                    return;
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for(Party p : mParties){
                mParties.remove(p);
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
