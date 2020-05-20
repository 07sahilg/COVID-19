package com.example.newcovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewCountryActivity extends AppCompatActivity {
    ListView countryList;
    ArrayAdapter arrayAdapter;
    ArrayList<String> country = new ArrayList<>();
    ArrayList<String> countryFlag = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_country);
        countryList = (ListView)findViewById(R.id.listView);


        fetchData();
        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// position tells which row is clicked and jump to NoteEditorActivity
                Intent intent = new Intent(ViewCountryActivity.this,CountryDetailsActivity.class);
                intent.putExtra("country",country.get(position));
                intent.putExtra("flag",countryFlag.get(position));
                startActivity(intent);


            }
        });
    }
    void fetchData(){

        String url = "https://corona.lmao.ninja/v2/countries";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++){
                              //  Log.i("countries", String.valueOf(response.getJSONObject(i).getString("country")));
                                Log.i("info",response.getJSONObject(i).getString("countryInfo"));
                                country.add(i,response.getJSONObject(i).getString("country"));
                                countryFlag.add(i,response.getJSONObject(i).getJSONObject("countryInfo").getString("flag"));
                                Log.i("flag",response.getJSONObject(i).getJSONObject("countryInfo").getString("flag"));
                            }
                            arrayAdapter = new ArrayAdapter(ViewCountryActivity.this,R.layout.support_simple_spinner_dropdown_item,country);
                            countryList.setAdapter(arrayAdapter);


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
                                ViewCountryActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
