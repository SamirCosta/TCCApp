package com.samir.TCCApp.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.MaskEditUtil;

import java.util.Arrays;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout editNome, editSobrenome, editEmail, editLayoutCelular, editPassword, editConfirmPass;
    private TextInputEditText editCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ref();

        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Intent intent = new Intent(RegisterActivity.this, SliderIntroActivity.class);
                intent.putExtra("page", 4);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();

            }
        });

        editCelular.addTextChangedListener(MaskEditUtil.mask(editCelular, "(##) #####-####"));

    }

    private void ref() {
        editNome = findViewById(R.id.editNameRegister);
        editSobrenome = findViewById(R.id.editSobrenome);
        editEmail = findViewById(R.id.editEmailRegister);
        editLayoutCelular = findViewById(R.id.editCelRegister);
        editCelular = findViewById(R.id.meditCelRegister);
        editPassword = findViewById(R.id.editPasswordRegister);
        editConfirmPass = findViewById(R.id.editPasswordConfirmRegister);
    }

    public void openMain(View view) {
        if (editNome.getEditText().getText().toString().isEmpty()) {
            editNome.setError("Campo obrigatório");
        }
        if (editSobrenome.getEditText().getText().toString().isEmpty()){
            editSobrenome.setError("Campo obrigatório");
        }
        if (editPassword.getEditText().getText().toString().isEmpty()){
            editPassword.setError("Campo obrigatório");
        }
        if (editConfirmPass.getEditText().getText().toString().isEmpty()){
            editConfirmPass.setError("Campo obrigatório");
        }

        validaEmail();
        validaCel();

        if (!verify(editNome, editSobrenome, editEmail, editLayoutCelular, editPassword, editConfirmPass)
        && validaEmail() && validaCel()){
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
    }

    private boolean validaCel() {
        String cel = editLayoutCelular.getEditText().getText().toString();
        if (cel.isEmpty()){
            editLayoutCelular.setError("Campo obrigatório");
            return false;
        }else if (!Patterns.PHONE.matcher(cel).matches() || cel.length() < 15){
            editLayoutCelular.setError("Insira um número válido");
            Log.i("RETURN", "AQUI");
            return false;
        }else{
            editLayoutCelular.setError(null);
            return true;
        }
    }

    private boolean validaEmail() {
        if (editEmail.getEditText().getText().toString().isEmpty()){
            editEmail.setError("Campo obrigatório");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getEditText().getText().toString()).matches()){
            editEmail.setError("Insira um email válido");
            return false;
        }else{
            editEmail.setError(null);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean verify(TextInputLayout... editTexts){
        return Arrays.stream(editTexts).anyMatch(e -> e.getEditText().getText().toString().isEmpty());
    }

}