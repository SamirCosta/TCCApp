package com.samir.TCCApp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.DAO.UserDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.MaskEditUtil;
import com.samir.TCCApp.classes.User;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout editNome, editSobrenome, editEmail, editLayoutCelular, editUserName, editPassword, editConfirmPass;
    private TextInputEditText editCelular;

    private String nome, sobrenome, email, cel, userName, password, confirmPass;

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
        editUserName = findViewById(R.id.editUserNameRegister);
    }

    public void openMain(View view) {
        nome = editNome.getEditText().getText().toString();
        sobrenome = editSobrenome.getEditText().getText().toString();
        email = editEmail.getEditText().getText().toString();
        cel = editLayoutCelular.getEditText().getText().toString();
        userName = editUserName.getEditText().getText().toString();
        password = editPassword.getEditText().getText().toString();
        confirmPass = editConfirmPass.getEditText().getText().toString();
        if (nome.isEmpty()) {
            editNome.setError("Campo obrigatório");
        }
        if (sobrenome.isEmpty()) {
            editSobrenome.setError("Campo obrigatório");
        }
        if (userName.isEmpty()) {
            editUserName.setError("Campo obrigatório");
        }
        if (password.isEmpty()) {
            editPassword.setError("Campo obrigatório");
        }
        if (confirmPass.isEmpty()) {
            editConfirmPass.setError("Campo obrigatório");
        }

        validaEmail();
        validaCel();

        if (!verify(nome, sobrenome, email, cel, userName, password, confirmPass)
                && validaEmail() && validaCel()) {
            User user = new User();
            UserDAO userDAO = new UserDAO(getApplicationContext());
            user.setUserName(userName);
            user.setPassword(password);
            user.setAcessType("1");

            Client client = new Client();
            ClientDAO clientDAO = new ClientDAO(getApplicationContext());
            client.setNameCli(nome);
            client.setEmailCli(email);
            client.setCelCli(Long.parseLong(cel.replace("(", "").replace(")", "")
                    .replace("-", "").replace(" ", "")));

            if (clientDAO.validateRegister(userName, email)) {
                userDAO.insertUser(user);
                clientDAO.insertCli(client);

                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }else{
//                Toast.makeText(this, "Usuário já cadastrado", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Usuário já cadastrado", Snackbar.LENGTH_LONG).show();
            }

        }
    }

    private boolean validaCel() {
        if (cel.isEmpty()) {
            editLayoutCelular.setError("Campo obrigatório");
            return false;
        } else if (!Patterns.PHONE.matcher(cel).matches() || cel.length() < 15) {
            editLayoutCelular.setError("Insira um número válido");
//            Log.i("RETURN", "AQUI");
            return false;
        } else {
            editLayoutCelular.setError(null);
            return true;
        }
    }

    private boolean validaEmail() {
        if (email.isEmpty()) {
            editEmail.setError("Campo obrigatório");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Insira um email válido");
            return false;
        } else {
            editEmail.setError(null);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean verify(String... data) {
        return Arrays.stream(data).anyMatch(String::isEmpty);
//        return Arrays.stream(data).anyMatch(e -> e.getEditText().getText().toString().isEmpty());
    }

}