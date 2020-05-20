package com.example.newcovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ContinentDetailsActivity extends AppCompatActivity {
    String continent;
    TextView tvCases, tvRecovered,name,
            tvCritical, tvActive,
            tvTodayCases, tvTotalDeaths,
            tvTodayDeaths,
            tvTests,tvPopulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_details);

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

        Intent intent = getIntent();
        continent = intent.getStringExtra("continent");
        name.setText(continent);
        Log.i("continent",continent);
        fetchData();
    }
    void fetchData(){

        String url = "https://corona.lmao.ninja/v2/continents/"+continent;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("info", String.valueOf(response.getString("continent")));
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
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(
                                ContinentDetailsActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
