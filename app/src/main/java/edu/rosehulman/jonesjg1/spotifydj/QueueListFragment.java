package edu.rosehulman.jonesjg1.spotifydj;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class QueueListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public QueueListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_view, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder Abuilder = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_TRADITIONAL);
                Abuilder.setTitle("Party Info");
                Abuilder.setView(getLayoutInflater().inflate(R.layout.party_signin_alert_dialog,null ,false));
                Abuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.changeFragment(R.id.queue_fragment);
                    }
                });

                Abuilder.setNegativeButton(android.R.string.cancel,null);
            Abuilder.create().show();
            }

        });

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
