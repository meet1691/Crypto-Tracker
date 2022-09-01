package com.meet.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText searchEdit ;
    RecyclerView currencies ;
    ProgressBar loadingIndicator;
    ArrayList<CurrencyModel> currencyModelArrayList ;
    CurrencyAdapter currencyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencies = findViewById(R.id.currencies);
        loadingIndicator = findViewById(R.id.pbLoading);
        searchEdit  = findViewById(R.id.editSearch);
        currencyModelArrayList = new ArrayList<>();
        currencyAdapter = new CurrencyAdapter(currencyModelArrayList,this);
        currencies.setLayoutManager(new LinearLayoutManager(this));
        currencies.setAdapter(currencyAdapter);


        fetchData();

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterCurrency(editable.toString());
            }
        });
    }


    // filter method that will be filter the data for search
    private void filterCurrency(String cur){
        ArrayList<CurrencyModel> filterdList = new ArrayList<>();

        for (CurrencyModel item : currencyModelArrayList){
            if (item.getCurrencyName().toLowerCase().contains(cur.toLowerCase())){
                filterdList.add(item);
            }
        }
//        if (filterdList.isEmpty()) {
//            Toast.makeText(this, "No currency found ", Toast.LENGTH_SHORT).show();
//        }
//        else {
            currencyAdapter.filterList(filterdList);
//        }

    }


    // we fetch data from the volley
    private void fetchData(){
        loadingIndicator.setVisibility(View.VISIBLE);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingIndicator.setVisibility(View.GONE);
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject dataObj = data.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");

                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject usd = quote.getJSONObject("USD");
                        double price = usd.getDouble("price");
                        currencyModelArrayList.add(new CurrencyModel(name,symbol,price));
                    }
                    currencyAdapter.notifyDataSetChanged();
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "failed to load data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Some error occur", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","Your Api Key");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}