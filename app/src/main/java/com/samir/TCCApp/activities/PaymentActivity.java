package com.samir.TCCApp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.BagAdapter;
import com.samir.TCCApp.fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaymentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
//    private ArrayList<Product> arrayListItem = new ArrayList<>();
    public TextView tvDuration, tvEnd;

    @Override
    protected void onStart() {
        super.onStart();
        if (AddressActivity.addressess != null) {
            tvEnd.setText(AddressActivity.addressess.getAddress());

            DurationTask durationTask = new DurationTask();
            durationTask.execute(AddressActivity.addressess.getAddress(), "Rua Rui barbosa, Centro- Bela Vista/SP");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ref();

        findViewById(R.id.cardViewMesa).setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            findViewById(R.id.cardViewMesa).setVisibility(View.VISIBLE);
            TextView textView = findViewById(R.id.mesaPay);
            textView.setText(bundle.getString("mesa"));
        }

        findViewById(R.id.btnBackPay).setOnClickListener(c -> {
            finish();
        });

        /*for (int i = 0; i < 11; i++) {
            Product itemCardapio = new Product("Name " + i, R.drawable.taco);
            arrayListItem.add(itemCardapio);
        }*/

        recyclerView = findViewById(R.id.recyclerItemPay);
        recyclerView.setHasFixedSize(true);
        BagAdapter bagAdapter = new BagAdapter(HomeFragment.bagArrayListItem);
        recyclerView.setAdapter(bagAdapter);

    }

    private void ref() {
        tvDuration = findViewById(R.id.tvPrev);
        tvEnd = findViewById(R.id.tvEndPay);
    }

    public void openEnd(View view){
        startActivity(new Intent(this, AddressActivity.class));
    }

    class DurationTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            final String TOKEN = "AIzaSyDbLJ5Dc7WbXRmjOli9fHsAlPM2Cho9Gh8";
            final String URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String json = null;
            String duration = null;

            try {

                Uri builtURI = Uri.parse(URL_API).buildUpon()
                        .appendQueryParameter("origin", strings[0])
                        .appendQueryParameter("destination", strings[1])
                        .appendQueryParameter("key", TOKEN)
                        .build();

                URL requestURL = new URL(builtURI.toString());

                urlConnection = (HttpURLConnection) requestURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder builder = new StringBuilder();
                String linha;

                while ((linha = reader.readLine()) != null) {
                    builder.append(linha);
                    builder.append("\n");
                }

                if (builder.length() == 0) {
                    return null;
                }
                json = builder.toString();
                Log.i("JSON", json);

                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("routes");
                JSONObject routes = jsonArray.getJSONObject(0);
                JSONArray legs = routes.getJSONArray("legs");
                JSONObject JSONlegs = legs.getJSONObject(0);
                JSONObject JSONduration = JSONlegs.getJSONObject("duration");
                duration = JSONduration.getString("text");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return duration;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvDuration.setText("Previsão: " + s);
        }
    }

}