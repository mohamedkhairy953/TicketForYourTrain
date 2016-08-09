package com.example.moham.ticketyourtrain;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by moham on 5/15/2016.
 */
public class BookingTicketFragment extends Fragment {
    ArrayList<CitiesModel> cities = new ArrayList<>();
    String citiesnames[];

    ArrayAdapter MyCityAdapter;
    ArrayAdapter MyTypeAdapter;

    Spinner sourcespinner, destspinner, typespinner;
    TextView pricetxt;

    TicketModel ticket = new TicketModel();
    int value1, value2, price;
    String SourceCity, DestCity, TypeCity;

    boolean isSingandRet = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookin_tickets, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        final DBController controller = new DBController(getActivity());
        sourcespinner = (Spinner) getActivity().findViewById(R.id.SourceCitiesSpinner);
        destspinner = (Spinner) getActivity().findViewById(R.id.DestinationCitiesSpinner);
        typespinner = (Spinner) getActivity().findViewById(R.id.TicketTypeSpinner);
        Button bok = (Button) getActivity().findViewById(R.id.Booking_btn);
        pricetxt = (TextView) getActivity().findViewById(R.id.PriceTextView);

        cities = (ArrayList<CitiesModel>) controller.GetAllCities();
        citiesnames = new String[cities.size()];
        citiesnames(citiesnames);
        MyCityAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, citiesnames);
        MyTypeAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, getResources().getTextArray(R.array.type));
        sourcespinner.setAdapter(MyCityAdapter);
        destspinner.setAdapter(MyCityAdapter);
        typespinner.setAdapter(MyTypeAdapter);

        sourcespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value1 = cities.get(parent.getSelectedItemPosition()).getValue();
                SourceCity = (String) parent.getSelectedItem();
                price = (value1 - value2);
                if (price < 0) {
                    price = price * -1;
                }
                if (isSingandRet) {
                    pricetxt.setText((price) + " * 2 = " + price * 2);

                } else {
                    pricetxt.setText(price + "");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        destspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value2 = cities.get(parent.getSelectedItemPosition()).getValue();
                DestCity = (String) parent.getSelectedItem();
                price = (value1 - value2);
                if (price < 0) {
                    price = price * -1;
                }
                if (isSingandRet) {
                    pricetxt.setText((price) + " * 2 = " + price * 2);

                } else {
                    pricetxt.setText(price + "");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeCity = (String) parent.getSelectedItem();
                if (parent.getSelectedItemPosition() == 2) {
                    isSingandRet = true;
                    pricetxt.setText((price) + " * 2 = " + price * 2);

                } else {
                    isSingandRet = false;
                    pricetxt.setText(price + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price == 0) {
                    Toast.makeText(getActivity(), "please choose your destination", Toast.LENGTH_SHORT).show();
                } else {
                    if (isSingandRet)
                        ticket.setPrice(price * 2);
                    else {
                        ticket.setPrice(price);
                    }
                    ticket.setDestination(DestCity);
                    ticket.setSource(SourceCity);
                    ticket.setType(TypeCity);
                    ticket.setBooking_date(getdate());
                    String l = controller.insert_Ticket(ticket, controller.getuser(MainActivity.Logged_user.getName()));
                    Toast.makeText(getActivity(), l, Toast.LENGTH_SHORT).show();

                }
            }

        });

        super.onActivityCreated(savedInstanceState);

    }


    public void citiesnames(String c[]) {
        for (int i = 0; i < cities.size(); i++) {
            c[i] = cities.get(i).getName();
        }
    }

    public String getdate() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        return time.toString();
    }

}
