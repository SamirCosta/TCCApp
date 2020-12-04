package com.samir.TCCApp.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.AddressActivity;
import com.samir.TCCApp.activities.MainActivity;
import com.samir.TCCApp.api.ClientService;
import com.samir.TCCApp.classes.Addressess;
import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.DatabaseHelper;
import com.samir.TCCApp.classes.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samir.TCCApp.activities.SliderIntroActivity.progressBar;
import static com.samir.TCCApp.utils.Helper.*;

public class ClientDAO {
    public static Client client;
    private final Context context;

    public ClientDAO(Context context) {
        this.context = context;
    }

    public void postClient(Client client, View view, Context mContext) {
        ClientService clientService = retrofit.create(ClientService.class);
        Call<Boolean> call = clientService.insertClientAPI(client);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.i("API", "Cod: " + response.code() + response.body());
                if (response.isSuccessful() && response.body()) {
                    ClientDAO.client = client;
                    save();
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    ((Activity) mContext).finish();
                } else {
                    snackbar(view, "Usuário já cadastrado");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("API", "DEU RUIM: " + t.getMessage());
            }
        });

    }

    public void validateLogin(String username, String pass, View view) {
        ClientService clientService = retrofit.create(ClientService.class);
        Call<Client> call = clientService.validateLogin(username, pass);

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    client = response.body();
                    if (client != null) {
                        if (client.getIdCli() > 0) {
                            if (save()) openMain();
                        } else
                            snackbar(view, "Usuário ou senha inválidos");
                    } else {
                        snackbar(view, "Usuário ou senha inválidos");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.i("ERRO NO ONFAILuRE", t.getMessage());
            }
        });

    }

    public void updateClient(Client client, View view){
        ClientService clientService = retrofit.create(ClientService.class);
        Call<Boolean> call = clientService.updateClient(client);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body()){
                        save();
                        if (view != null) snackbar(view, "Usuário alterado com sucesso");
                    }else {
                        if (view != null) snackbar(view, "Erro ao alterar usuário");
                        else Toast.makeText(context, "Erro ao salvar endereço", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("ERRO", t.getMessage());
            }
        });
    }

    public boolean save() {
        try {
            FileOutputStream fos = new FileOutputStream(context.getFileStreamPath(ARQUIVO_CLIENT));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(client);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void openMain() {
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
        ((Activity) context).finish();
    }

}
