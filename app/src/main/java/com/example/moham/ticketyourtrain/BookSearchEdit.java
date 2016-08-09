package com.example.moham.ticketyourtrain;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;

public class BookSearchEdit extends AppCompatActivity {
    String FragmentsTasks[];
    Spinner myspinner;
    ArrayAdapter myadapter;
    BookingTicketFragment ticketFragment;
    ProfileFragment profileFragment;
    MyTicketsFragment myTicketsFragment;
    DefaultFragment defaultFragment;
    AddBalanceFragment addBalanceFragment;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search_edit);
        ticketFragment = new BookingTicketFragment();
        profileFragment = new ProfileFragment();
        myTicketsFragment = new MyTicketsFragment();
        defaultFragment = new DefaultFragment();
        addBalanceFragment = new AddBalanceFragment();

        FragmentsTasks = getResources().getStringArray(R.array.Fragments);
        myspinner = (Spinner) findViewById(R.id.ToFragmentsSpinner);
        myadapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, FragmentsTasks);
        myspinner.setAdapter(myadapter);
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (parent.getSelectedItemPosition()) {
                    case 0:
                        transaction.replace(android.R.id.content, defaultFragment);
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(android.R.id.content, ticketFragment);
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(android.R.id.content, myTicketsFragment);
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(android.R.id.content, profileFragment);
                        transaction.commit();
                        break;
                    case 4:
                        transaction.replace(android.R.id.content, addBalanceFragment);
                        transaction.commit();
                        break;
                    case 5:
                        finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
