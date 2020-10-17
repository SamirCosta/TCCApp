package com.samir.TCCApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.samir.TCCApp.R;
import com.samir.TCCApp.utils.Helper;

import java.util.ArrayList;

public class ScannerActivity extends AppCompatActivity {
    private ArrayList<String> mesas = new ArrayList<>();
    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        codeScannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, codeScannerView);

        for (int i = 1; i <= 30; i++){
            mesas.add("Mesa " + i);
        }

        codeScanner.setDecodeCallback(result -> {
            runOnUiThread(() -> {
                String resu = result.getText();

                for(int i = 0; i < mesas.size(); i++){
                    if (resu.equals(mesas.get(i))){
                        Intent intent = new Intent(this, PaymentActivity.class);
                        intent.putExtra("mesa", resu);
                        startActivity(intent);
                    }else{
                        textView = findViewById(R.id.tvMesa);
                        textView.setText("Código inválido");
                    }
                }

            });
        });

        codeScannerView.setOnClickListener(c -> {
            codeScanner.startPreview();
            textView.setText("");
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}