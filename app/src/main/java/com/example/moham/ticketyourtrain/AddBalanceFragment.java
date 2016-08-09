package com.example.moham.ticketyourtrain;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by moham on 5/15/2016.
 */
public class AddBalanceFragment extends Fragment {
    EditText amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_balance, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        amount = (EditText) getActivity().findViewById(R.id.amount_txt);
        Button add_btn = (Button) getActivity().findViewById(R.id.add_balance_btn);
        final DBController controller = new DBController(getActivity());


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double new_amount = Double.parseDouble(amount.getText().toString());
                    if (new_amount > 0) {
                        UserModel getuser = controller.getuser(MainActivity.Logged_user.getName());
                        String s = controller.AddBalance(getuser, new_amount);
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "add a positive number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception d) {
                    Toast.makeText(getActivity(), d.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        super.onActivityCreated(savedInstanceState);
    }
}
