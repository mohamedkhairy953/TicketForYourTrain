package com.example.moham.ticketyourtrain;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Register extends AppCompatActivity {
    public String genders[] = {"Male", "Female", "Other"};
    public Button registerBtn, BirthDateBtn;
    public ImageView imgView;
    public EditText phonetext, nametext, balance;
    public Spinner GenderSpinner;
    ArrayAdapter spinadapter;
    // registeration user entity
    private int years;
    private int months;
    private int days;
    private static final int PHOTO_CODE = 1;
    private static final int DATE_PICKER = 1;
    UserModel myuser = new UserModel();
    DBController controller = new DBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intialize_datefields();
        registerBtn = (Button) findViewById(R.id.register_btn);
        BirthDateBtn = (Button) findViewById(R.id.birth_date_btn);
        imgView = (ImageView) findViewById(R.id.img_picker);
        phonetext = (EditText) findViewById(R.id.phone_txt);
        balance = (EditText) findViewById(R.id.balance_txt);
        nametext = (EditText) findViewById(R.id.name_txt);
        GenderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        spinadapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genders);
        spinadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        GenderSpinner.setAdapter(spinadapter);

        GenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myuser.setType((String) parent.getItemAtPosition(position));
                Toast.makeText(Register.this, myuser.getType(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Register.this, " please choose one of them", Toast.LENGTH_SHORT).show();
            }
        });


        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, PHOTO_CODE);

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myuser.setName(String.valueOf(nametext.getText()));
                    myuser.setPhone(phonetext.getText().toString());
                    myuser.setBalance(Double.parseDouble(balance.getText().toString()));
                    int i = controller.insert_user(myuser);
                    Toast.makeText(Register.this, i + "", Toast.LENGTH_SHORT).show();
                    if (i == -1) {
                        Toast.makeText(Register.this, "Check your inputs", Toast.LENGTH_SHORT).show();
                    } else {
                        finish();
                    }
                } catch (Exception s) {
                    Toast.makeText(Register.this, s.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        BirthDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER:
                return new DatePickerDialog(this, birth_date, years, months, days);
        }

        return null;
    }


    DatePickerDialog.OnDateSetListener birth_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            years = year;
            months = monthOfYear;
            days = dayOfMonth;
            view.init(1980, 0, 1, null);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            java.util.Date date_birth = new java.util.Date(years, months, days, 0, 0, 0);

            String s = format.format(date_birth);
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();

            myuser.setBirth_date(s);

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    final Uri img_uri = data.getData();
                    try {
                        InputStream img_is = getContentResolver().openInputStream(img_uri);
                        final Bitmap img_bitmap = BitmapFactory.decodeStream(img_is);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        img_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        myuser.setImage(byteArrayOutputStream.toByteArray());
                        imgView.setImageBitmap(img_bitmap);
                    } catch (Exception f) {

                    } catch (OutOfMemoryError b) {
                        Toast.makeText(Register.this, "too size photo", Toast.LENGTH_SHORT).show();
                    }
                }


        }
    }

    public void intialize_datefields() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        years = date.getYear();
        months = date.getMonth();
        days = date.getDay();
    }

}


