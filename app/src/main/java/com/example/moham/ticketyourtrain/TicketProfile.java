package com.example.moham.ticketyourtrain;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by moham on 5/17/2016.
 */
public class TicketProfile extends Activity {
    TextView souercTxtView, desTxtView, typeTxtView, priceTxtView, dateTxtView;
    Button backbtn;
    DBController controller = new DBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_ticket_info);
        souercTxtView = (TextView) findViewById(R.id.SourceCitiestxtinfo);
        desTxtView = (TextView) findViewById(R.id.DestinationCitiestxtinfo);
        typeTxtView = (TextView) findViewById(R.id.TicketTypetxtinfo);
        priceTxtView = (TextView) findViewById(R.id.PriceTxtinfo);
        dateTxtView = (TextView) findViewById(R.id.Datetxtinfo);
        backbtn = (Button) findViewById(R.id.back_btn_info);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TicketModel geticket = controller.Geticket(getIntent().getIntExtra("ticket_id", -1));
        if (geticket.getId() > 0) {
            souercTxtView.setText(geticket.getSource().toString());
            desTxtView.setText(geticket.getDestination().toString());
            typeTxtView.setText(geticket.getType().toString());
            priceTxtView.setText(geticket.getPrice() + "");
            dateTxtView.setText(geticket.getBooking_date().toString());
        }


    }
}
