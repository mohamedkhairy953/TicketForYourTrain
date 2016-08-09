package com.example.moham.ticketyourtrain;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by moham on 5/15/2016.
 */
public class DefaultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.default_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        TextView t= (TextView) getActivity().findViewById(R.id.Welcomtxt);
        t.setText("Welcom "+MainActivity.Logged_user.getName());
        super.onActivityCreated(savedInstanceState);
    }
}
