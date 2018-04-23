package edu.rosehulman.jonesjg1.spotifydj;


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


    public JoinOrCreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_or_create, container, false);

        Button joinButton = view.findViewById(R.id.buttonJoin);
        Button createButton = view.findViewById(R.id.buttonCreate);

        joinButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO: handle switching to join fragment
                Log.d("TAG", "Join button clicked.");
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO: handle switching to create fragment
                Log.d("TAG", "Create button clicked.");
            }
        });

        return view;
    }

}
