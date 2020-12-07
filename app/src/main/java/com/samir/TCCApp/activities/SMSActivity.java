package com.samir.TCCApp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.samir.TCCApp.utils.Helper.snackbar;

public class SMSActivity extends AppCompatActivity {
    private EditText editNumVer, editNumVer2, editNumVer3, editNumVer4;
    private TextView tvCelVer;
    private Client client;
    private int numRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s);
        editNumVer = findViewById(R.id.editNumVer1);
        editNumVer2 = findViewById(R.id.editNumVer2);
        editNumVer3 = findViewById(R.id.editNumVer3);
        editNumVer4 = findViewById(R.id.editNumVer4);
        tvCelVer = findViewById(R.id.tvCelVer);

        Bundle bundle = getIntent().getExtras();
        client = (Client) bundle.getSerializable("smsCli");

        tvCelVer.setText(String.valueOf(client.getCelCli()));

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.SEND_SMS},
                    1);
        }else {
            Random randomico = new Random();
            numRandom = randomico.nextInt(9999 - 1000) + 1000;
            String msg = "Código de confirmação: " + numRandom;

            enviaSMS("+55" + client.getCelCli(), msg);
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (verifyEmptyEditText() != null) verifyEmptyEditText().requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editNumVer4.getText().toString().equals("") && !verify(editNumVer, editNumVer2, editNumVer3, editNumVer4)){
                    findViewById(R.id.progressBarVerCel).setVisibility(View.VISIBLE);
                    openMain();
                }
            }
        };

        editNumVer.addTextChangedListener(textWatcher);
        editNumVer2.addTextChangedListener(textWatcher);
        editNumVer3.addTextChangedListener(textWatcher);
        editNumVer4.addTextChangedListener(textWatcher);

    }

    private EditText verifyEmptyEditText() {
         if (editNumVer.getText().toString().isEmpty()) return editNumVer;
         if (editNumVer2.getText().toString().isEmpty()) return editNumVer2;
         if (editNumVer3.getText().toString().isEmpty()) return editNumVer3;
         if (editNumVer4.getText().toString().isEmpty()) return editNumVer4;
         return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean verify(EditText... editTexts) {
        return Arrays.stream(editTexts).anyMatch(e -> e.getText().toString().isEmpty());
    }

    private static String num(EditText... editTexts) {
        StringBuilder num = new StringBuilder();
        for (EditText s : editTexts){
         num.append(s.getText().toString());
        }
        return num.toString();
    }

    private void openMain() {
        if (String.valueOf(numRandom).equals(num(editNumVer, editNumVer2, editNumVer3, editNumVer4))){
            ClientDAO clientDAO = new ClientDAO(getApplicationContext());
            clientDAO.postClient(client, findViewById(android.R.id.content), SMSActivity.this);
        }else {
            findViewById(R.id.progressBarVerCel).setVisibility(View.GONE);
            snackbar(findViewById(android.R.id.content), "Código inválido");
        }

    }

    private void enviaSMS(String cel, String msg){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(cel, null, msg, null, null);
        }catch (Exception e){
            e.printStackTrace();
            snackbar(findViewById(android.R.id.content), "Erro ao enviar SMS");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) finish();
        else {
            Random randomico = new Random();
            numRandom = randomico.nextInt(9999 - 1000) + 1000;
            String msg = "Código de confirmação: " + numRandom;

            enviaSMS("+55" + client.getCelCli(), msg);
        }
    }

}