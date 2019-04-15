package com.yodo.caz.myapplication;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button adminButton;
    Button shopkeeperButton;
    Button customerButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminButton = findViewById(R.id.admin_button);
        shopkeeperButton = findViewById(R.id.shopkeeper_button);
        customerButton = findViewById(R.id.customer_button);



        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent adminIntent = new Intent(MainActivity.this, AdminMain.class);
                startActivity(adminIntent);
            }
        });

        shopkeeperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shopkeeperIntent = new Intent(MainActivity.this, ShopkeeperMain.class);
                startActivity(shopkeeperIntent);
            }
        });

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent customerIntent = new Intent(MainActivity.this, CustomerMain.class);
                startActivity(customerIntent);
            }
        });
    }

}
