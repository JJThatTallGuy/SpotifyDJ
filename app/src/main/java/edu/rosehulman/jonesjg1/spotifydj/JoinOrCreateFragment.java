package edu.rosehulman.jonesjg1.spotifydj;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinOrCreateFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    public JoinOrCreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_or_create, container, false);

        Button joinButton = view.findViewById(R.id.buttonJoin);
        Button createButton = view.findViewById(R.id.buttonCreate);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.changeFragment(R.id.queue_in_list);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder Abuilder = new AlertDialog.Builder(inflater.getContext(),AlertDialog.THEME_TRADITIONAL);
                Abuilder.setView(getLayoutInflater().inflate(R.layout.party_create_alert_dialog,null,false));
                Abuilder.setTitle("Create a Party");
                Abuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.changeFragment(R.id.queue_fragment);
                    }
                });

                Abuilder.setNegativeButton(android.R.string.cancel, null);

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
