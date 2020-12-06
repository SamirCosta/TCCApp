package com.samir.TCCApp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
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

import static com.samir.TCCApp.activities.MainActivity.productDAO;
import static com.samir.TCCApp.utils.Helper.COL_VALPROD;

public class PaymentActivity extends AppCompatActivity {
    private static final int CREDITO = R.id.rbCredito;
    private static final int DEBITO = R.id.rbDebito;
    private static final int DIN = R.id.rbDin;
    private RecyclerView recyclerView;
    //    private ArrayList<Product> arrayListItem = new ArrayList<>();
    public TextView tvDuration, tvEnd, tvFormPagText;
    private String END_REST = "Rua Rui barbosa, Centro- Bela Vista/SP";
    private View formaPag;

    @Override
    protected void onStart() {
        super.onStart();
        if (AddressActivity.addressess != null)
            if (AddressActivity.addressess.getAddress() != null)
                if (!AddressActivity.addressess.getAddress().equals(""))
                    tvEnd.setText(AddressActivity.addressess.getAddress());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ref();

        if (AddressActivity.addressess != null) {
            if (AddressActivity.addressess.getAddress() != null)
                if (!AddressActivity.addressess.getAddress().equals(""))
                    new DurationTask().execute(AddressActivity.addressess.getAddress(), END_REST);
        }

        findViewById(R.id.cardViewMesa).setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
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
        BagAdapter bagAdapter = new BagAdapter(HomeFragment.bagArrayListItem, getApplicationContext());
        recyclerView.setAdapter(bagAdapter);

        formaPag.setOnClickListener(c -> {
            Dialog dialog = new Dialog(PaymentActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.form_pag_dialog);
            dialogActions(dialog);
            dialog.show();
        });

    }

    private void dialogActions(Dialog dialog) {
        TextView tvOK = dialog.findViewById(R.id.btnOkPag);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroupPag);
        tvOK.setOnClickListener(c -> {
            dialog.cancel();
            switch (radioGroup.getCheckedRadioButtonId()) {
                case CREDITO:
                    tvFormPagText.setText("Crédito");
                    break;
                case DEBITO:
                    tvFormPagText.setText("Débito");
                    break;
                case DIN:
                    tvFormPagText.setText("Dinheiro");
                    break;
            }
        });
    }

    private void ref() {
        tvDuration = findViewById(R.id.tvPrev);
        tvEnd = findViewById(R.id.tvEndPay);
        formaPag = findViewById(R.id.formaPag);
        tvFormPagText = findViewById(R.id.tvFormPagText);
    }

    public void openEnd(View view) {
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
            if (!s.isEmpty())
                tvDuration.setText("Previsão: " + s);
        }
    }

}