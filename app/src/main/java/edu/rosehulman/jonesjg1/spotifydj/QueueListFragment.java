package edu.rosehulman.jonesjg1.spotifydj;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class QueueListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public QueueListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        view.setHasFixedSize(true);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder Abuilder = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_TRADITIONAL);
//                Abuilder.setTitle("Party Info");
//                Abuilder.setView(getLayoutInflater().inflate(R.layout.party_signin_alert_dialog,null ,false));
//                Abuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        mListener.changeFragment(R.id.queue_fragment);
//                    }
//                });
//
//                Abuilder.setNegativeButton(android.R.string.cancel,null);
//                 Abuilder.create().show();
//            }
//
//        });

        final QueueListAdapter adapter = new QueueListAdapter(getContext(), view);
        view.setAdapter(adapter);

        // Hardcoded queues before we implement Firebase
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
