package com.samir.TCCApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.api.ClientService;
import com.samir.TCCApp.classes.Addressess;
import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.User;
import com.samir.TCCApp.utils.MaskEditUtil;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.samir.TCCApp.utils.Helper.retrofit;
import static com.samir.TCCApp.utils.Helper.snackbar;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout editNome, editEmail, editLayoutCelular, editUserName, editPassword, editConfirmPass;
    private TextInputEditText editCelular;

    private String nome, email, cel, userName, password, confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ref();

        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(RegisterActivity.this, SliderIntroActivity.class);
                intent.putExtra("page", 4);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
        });

        editCelular.addTextChangedListener(MaskEditUtil.mask(editCelular, "(##) #####-####"));

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        FirebaseUser userFace = FirebaseAuth.getInstance().getCurrentUser();

        if (signInAccount != null) {
            editNome.getEditText().setText(signInAccount.getGivenName() + " " + signInAccount.getFamilyName());
            editEmail.getEditText().setText(signInAccount.getEmail());
        }else if (userFace != null) {
            String name = userFace.getDisplayName();
            String[] array = name.split(" ");
            editNome.getEditText().setText(array[0]);

            editEmail.getEditText().setText(userFace.getEmail());
            editCelular.setText(userFace.getPhoneNumber());
        }

    }

    private void ref() {
        editNome = findViewById(R.id.editNameRegister);
        editEmail = findViewById(R.id.editEmailRegister);
        editLayoutCelular = findViewById(R.id.editCelRegister);
        editCelular = findViewById(R.id.meditCelRegister);
        editPassword = findViewById(R.id.editPasswordRegister);
        editConfirmPass = findViewById(R.id.editPasswordConfirmRegister);
        editUserName = findViewById(R.id.editUserNameRegister);
    }

    public void openMain(View view) {
        nome = editNome.getEditText().getText().toString();
        email = editEmail.getEditText().getText().toString();
        cel = editLayoutCelular.getEditText().getText().toString();
        userName = editUserName.getEditText().getText().toString();
        password = editPassword.getEditText().getText().toString();
        confirmPass = editConfirmPass.getEditText().getText().toString();
        if (nome.isEmpty()) {
            editNome.setError("Campo obrigatório");
        }
        /*if (sobrenome.isEmpty()) {
            editSobrenome.setError("Campo obrigatório");
        }*/
        if (userName.isEmpty()) {
            editUserName.setError("Campo obrigatório");
        }
        if (password.isEmpty()) {
            editPassword.setError("Campo obrigatório");
        }
        if (confirmPass.isEmpty()) {
            editConfirmPass.setError("Campo obrigatório");
        }

        boolean pass = true;
        if (!confirmPass.equals(password)) {
            editConfirmPass.setError("As senhas devem ser iguais");
            pass = false;
        }

        validaEmail();
        validaCel();

        if (!verify(nome, email, cel, userName, password, confirmPass)
                && validaEmail() && validaCel() && pass) {

            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setAcessType("1");

            Client client = new Client();
            client.setUser(user);
            client.setAddressess(new Addressess());
            client.setNameCli(nome);
            client.setEmailCli(email);
            client.setCelCli(Long.parseLong(cel.replace("(", "").replace(")", "")
                    .replace("-", "").replace(" ", "")));

            verifyCad(client, view);
        }
    }

    private void verifyCad(Client client, View view) {
        ClientService clientService = retrofit.create(ClientService.class);
        Call<Boolean> call = clientService.verifyCad(client.getEmailCli(), client.getUser().getUserName());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.i("API", "Cod: " + response.code() + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body()){
                        Intent intent = new Intent(RegisterActivity.this, SMSActivity.class);
                        intent.putExtra("smsCli", client);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    }else {
                        snackbar(view, "Usuário já cadastrado");
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("API", "DEU RUIM: " + t.getMessage());
            }
        });

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