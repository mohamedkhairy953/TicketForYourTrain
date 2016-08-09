package com.example.moham.ticketyourtrain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by moham on 5/16/2016.
 */
public class MyTicketsFragment extends ListFragment implements AdapterView.OnItemClickListener {
    ArrayList<TicketModel> userTicketsArrayList = new ArrayList<>();
    private DBController controller;
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_tickets_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        controller = new DBController(getActivity());
        adapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, getTicketsStringFromDB());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertDialog.Builder(getActivity())
                .setItems(R.array.delete_or_view, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent i = new Intent(getActivity().getBaseContext(), TicketProfile.class);
                                i.putExtra("ticket_id", userTicketsArrayList.get(position).getId());
                                startActivity(i);
                                break;
                            case 1:
                                controller.delete(userTicketsArrayList.get(position).getId());
                                controller.AddBalance(MainActivity.Logged_user, userTicketsArrayList.get(position).getPrice());
                                adapter.notifyDataSetChanged();
                                adapter.setNotifyOnChange(true);
                                break;

                        }
                    }
                })
                .show();

    }


    public String[] getTicketsStringFromDB() {
        String ticketsStrings[];
        DBController controller = new DBController(getActivity());
        userTicketsArrayList = controller.GetUserTickets(MainActivity.Logged_user.getId());
        ticketsStrings = new String[userTicketsArrayList.size()];
        for (int i = 0; i < ticketsStrings.length; i++) {
            ticketsStrings[i] = userTicketsArrayList.get(i).getSource() + " >> " +
                    "" + userTicketsArrayList.get(i).getDestination() + " ( " + userTicketsArrayList.get(i).getBooking_date() + " )";
        }
        return ticketsStrings;
    }
}
