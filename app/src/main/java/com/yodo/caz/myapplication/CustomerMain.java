package com.yodo.caz.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerMain extends AppCompatActivity {

    EditText customerId;
    EditText dvdId;

    Button rentButton;
    Button cartButton;

    Context context;

    LinearLayout linearLayout;

    final ArrayList<Dvd> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        customerId = findViewById(R.id.customer_id);
        dvdId = findViewById(R.id.dvd_id);
        rentButton = findViewById(R.id.rent_button);
        cartButton = findViewById(R.id.cart_button);
        linearLayout = findViewById(R.id.linear_layout);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String auth = Base64.encodeToString("lambda-client:lambda-secret".getBytes(), Base64.DEFAULT);

                Map<String, String> headerProperties = new HashMap<>();
                headerProperties.put("Authorization", "Basic " + auth);

                String tokenRequest = NetworkAdapter.httpRequest(
                        "http://192.168.1.233:2121/oauth/token?grant_type=password&username=lucy&password=Grape8Bitdo!&scope=",
                        "POST", null, headerProperties);

                Log.i("justine", tokenRequest);
                try {
                    String token = new JSONObject(tokenRequest).getString("access_token");

                    headerProperties.clear();
                    headerProperties.put("Authorization", "Bearer " + token);
                    try {
                        String result = NetworkAdapter.httpRequest("http://192.168.1.233:2121/dvds", NetworkAdapter.GET, null, headerProperties);
                        JSONArray dataJsonArray = new JSONArray(result);
                        Log.i("Authentication", result);
                        for (int i = 0; i < dataJsonArray.length(); ++i) {
                            Dvd dvd = new Dvd(dataJsonArray.getJSONObject(i));
                            data.add(dvd);
                        }
//                        Log.i("vonny", data);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i = 0; i < data.size(); i++) {
                                    TextView textView = new TextView(context);
                                    final Dvd getDvds = data.get(i);
                                    textView.setText(getDvds.getDescription()+ " " + getDvds.getDvdtitle());
                                    textView.setTextSize(20);
                                    linearLayout.addView(textView);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();





        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
}
