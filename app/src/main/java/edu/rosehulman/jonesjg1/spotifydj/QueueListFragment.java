package edu.rosehulman.jonesjg1.spotifydj;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QueueListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Party mParty;
    private DatabaseReference PartyRef;

    public QueueListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_queue_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        ((MainActivity) getActivity()).getPlayer().pause(null);

        this.PartyRef = FirebaseDatabase.getInstance().getReference().child("Parties");
        this.PartyRef.keepSynced(true);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder Abuilder = new AlertDialog.Builder(inflater.getContext(),AlertDialog.THEME_TRADITIONAL);
                View popup = getLayoutInflater().inflate(R.layout.party_create_alert_dialog,null,false);
                Abuilder.setView(popup);
                Abuilder.setTitle("Create a Party");
                final EditText title = popup.findViewById(R.id.name_edit);

                final EditText password = popup.findViewById(R.id.password_edit);

                Abuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String titletext = title.getText().toString();
                        String pass = password.getText().toString();
                        mParty = new Party(titletext, pass, ((MainActivity) getActivity()).getUser());
                        PartyRef.push().setValue(mParty);
                        ((MainActivity) getActivity()).setCurrentQueue(mParty);
                    }
                });

                Abuilder.setNegativeButton(android.R.string.cancel, null);

                Abuilder.create().show();
            }
        });

        final QueueListAdapter adapter = new QueueListAdapter(getContext(), recyclerView);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
