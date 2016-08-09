package com.example.moham.ticketyourtrain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBController controller = new DBController(this);
    public Button reg_btn, login_btn;
    public EditText username;
    public static UserModel Logged_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg_btn = (Button) findViewById(R.id.Registerbutton);
        login_btn = (Button) findViewById(R.id.loginbutton);
        username = (EditText) findViewById(R.id.editTextuser);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logged_user = controller.getuser(username.getText().toString());
                try {
                    if (Logged_user.getName().equals(username.getText().toString())) {
                        Toast.makeText(MainActivity.this, Logged_user.getId() + "", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        startActivity(new Intent(MainActivity.this, BookSearchEdit.class));
                    } else {
                        Toast.makeText(MainActivity.this," not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



}

