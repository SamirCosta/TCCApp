package com.samir.TCCApp.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

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
    private Retrofit retrofit;
    public static Client client;

    private final Context context;

    public ClientDAO(Context context) {
        this.context = context;
    }

    public void postClient(Client client, View view, Context mContext) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientService clientService = retrofit.create(ClientService.class);
        Call<Boolean> call = clientService.insertClientAPI(client);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.i("API", "Cod: " + response.code() + response.body());
                if (response.isSuccessful() && response.body()) {
                    ClientDAO.client = client;
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    ((Activity) mContext).finish();
                }else{
                    Snackbar.make(view, "Usuário já cadastrado", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("API", "DEU RUIM: " + t.getMessage());
            }
        });

    }

    public void validateLogin(String username, String pass, View view) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientService clientService = retrofit.create(ClientService.class);
        Call<Client> call = clientService.validateLogin(username, pass);

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    client = response.body();
                    if (client != null) {
                        if (client.getIdCli() > 0){
                            if (save()) openMain();
                        }
                        else
                            Snackbar.make(view, "Usuário ou senha inválidos", Snackbar.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });

    }

    private boolean save() {
        try {
            FileOutputStream fos = new FileOutputStream(context.getFileStreamPath(ARQUIVO_CLIENT));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(client);
            oos.close();
            fos.close();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        getInternalClients();
        return true;
    }

    private void openMain() {
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
        ((Activity) context).finish();

    }

    private void getInternalClients() {
        Client client = null;
        try {
            FileInputStream fis = new FileInputStream(context.getFileStreamPath(ARQUIVO_CLIENT));
            ObjectInputStream ois = new ObjectInputStream(fis);
            client = (Client) ois.readObject();
            Log.i("dsbgd", client.getNameCli());

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
