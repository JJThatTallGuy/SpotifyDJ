package edu.rosehulman.jonesjg1.spotifydj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<Party> mParties;

    public QueueAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        mRecyclerView = recyclerView;
        mParties = new ArrayList<>();
    }

    public void addParty(Party party) {
        mParties.add(0, party);
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
//            AlertDialog.Builder Abuilder = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_TRADITIONAL);
//            Abuilder.setTitle("Party Info");
//            Abuilder.setView(getLayoutInflater().inflate(R.layout.party_signin_alert_dialog,null ,false));
//            Abuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    mListener.changeFragment(R.id.queue_fragment);
//                }
//            });
//
//            Abuilder.setNegativeButton(android.R.string.cancel,null);
//            Abuilder.create().show();
        }
    }
}
