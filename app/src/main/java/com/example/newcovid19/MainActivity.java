package com.example.newcovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView tvCases, tvRecovered,
            tvCritical, tvActive,
            tvTodayCases, tvTotalDeaths,
            tvTodayDeaths,
            tvAffectedCountries;
    Button viewCountry;
    Button viewContinent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCases
                = findViewById(R.id.tvCases);
        tvRecovered
                = findViewById(R.id.tvRecovered);
        tvCritical
                = findViewById(R.id.tvCritical);
        tvActive
                = findViewById(R.id.tvActive);
        tvTodayCases
                = findViewById(R.id.tvTodayCases);
        tvTotalDeaths
                = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths
                = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries
                = findViewById(R.id.tvAffectedCountries);
        viewCountry = (Button) findViewById(R.id.button4);
        viewContinent = (Button) findViewById(R.id.button5);

        // Creating a method fetchdata()
        fetchdata();
        viewCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewCountryActivity.class);
                startActivity(intent);
            }
        });
        viewContinent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewContinentActivity.class);
                startActivity(intent);
            }
        });


    }

    private void fetchdata()
    {

        // Create a String request
        // using Volley Library
        String url = "https://corona.lmao.ninja/v2/all";

       JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
               url,
               null,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       try {

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
                           tvAffectedCountries.setText(
                                   response.getString(
                                           "affectedCountries"));
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
                                MainActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}





/* package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    TextView newConfirmed,totalConfirmed,newDeaths,totalDeaths,newRecovered,totalRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newConfirmed = (TextView) findViewById(R.id.newConfirmed);
       /* totalConfirmed= (TextView) findViewById(R.id.totalConfirmed);
        newDeaths = (TextView) findViewById(R.id.newDeath);
        totalDeaths = (TextView) findViewById(R.id.totalDeath);
        newRecovered = (TextView) findViewById(R.id.newRecovered);
        totalRecovered = (TextView) findViewById(R.id.totalRecovered);*/


       /* DownloadTask task =new DownloadTask();
        task.execute("https://covid19.mathdro.id/api");

        }
public class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            urlConnection.connect();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1) {

                char current = (char) data;

                result += current;

                data = reader.read();

            }

            return result;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            String message= "";

            JSONObject jsonObject = new JSONObject(result);//converting website content to json obj
            JSONArray keys = jsonObject.names ();

            for (int i = 0; i < 3; ++i) {

                String key = keys.getString (i); // Here's your key
                String value = jsonObject.getString (key); // Here's your value
                JSONObject arr = new JSONObject(value);
                Log.i(key,arr.getString("value"));
                message += key+" : "+arr.getString("value") + "\n";
            }
            newConfirmed.setText(message);

                /*String casesInfo=jsonObject.getString("confirmed");//we want weather from website content and we'll convert to string
                String casesInfo=jsonObject.getString("confirmed");
                String casesInfo=jsonObject.getString("confirmed");

                Log.i("cases",casesInfo);
                JSONObject arr = new JSONObject(casesInfo);//string into json array
                Log.i("cas",arr.getString("NewConfirmed"));




                newConfirmed.setText("NewConfirm : "+arr.getString("NewConfirmed"));
                totalConfirmed.setText("TotalConfirm : "+ arr.getString("TotalConfirmed"));
                newDeaths.setText("NewDeaths : "+arr.getString("NewDeaths"));
                totalDeaths.setText("TotalDeaths : "+arr.getString("TotalDeaths"));
                newRecovered.setText("NewRecover : "+arr.getString("NewRecovered"));
                totalRecovered.setText("TotalRecover: "+arr.getString("TotalRecovered"));*/
    /*    }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

}*/
