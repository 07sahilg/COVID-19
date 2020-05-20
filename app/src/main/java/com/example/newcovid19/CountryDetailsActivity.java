package com.example.newcovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;

public class CountryDetailsActivity extends AppCompatActivity {
    String country,flag;

    TextView tvCases, tvRecovered,name,
            tvCritical, tvActive,
            tvTodayCases, tvTotalDeaths,
            tvTodayDeaths,
            tvTests,tvPopulation;
    ImageView imageView;
    ScrollView cnty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        name = findViewById(R.id.name);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvTests = findViewById(R.id.tvtests);
        tvPopulation = findViewById(R.id.tvpopulation);
        cnty = findViewById(R.id.country);

        Intent intent = getIntent();
        country = intent.getStringExtra("country");
        flag = intent.getStringExtra("flag"); // url for flag
        Log.i("country",country);

        name.setText(country);

        fetchData();
    }
    void fetchData() {

        String url = "https://corona.lmao.ninja/v2/countries/" + country;

        ImageRequest imageRequest = new ImageRequest(flag,new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(response);
                //cnty.getBackground().setColorFilter(Color.parseColor("#0055A4"),PorterDuff.Mode.SRC);
            }
        }, 0, 0, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CountryDetailsActivity.this, "Error Occured while loading flag", Toast.LENGTH_SHORT).show();

            }
        });

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("info", String.valueOf(response));
                            JSONObject countryInfo = new JSONObject();
                            countryInfo = response.getJSONObject("countryInfo");
                            tvCases.setText(
                                    response.getString("cases"));
                            tvRecovered.setText(
                                    response.getString(
                                            "recovered"));
                            tvCritical.setText(
                                    response.getString(
                                            "critical"));
                            tvActive.setText(
                                    response.getString(
                                            "active"));
                            tvTodayCases.setText(
                                    response.getString(
                                            "todayCases"));
                            tvTotalDeaths.setText(
                                    response.getString(
                                            "deaths"));
                            tvTodayDeaths.setText(
                                    response.getString(
                                            "todayDeaths"));
                            tvTests.setText(
                                    response.getString(
                                            "tests"));
                            tvPopulation.setText(response.getString("population"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                CountryDetailsActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(imageRequest);
        requestQueue.add(request);

    }

}
