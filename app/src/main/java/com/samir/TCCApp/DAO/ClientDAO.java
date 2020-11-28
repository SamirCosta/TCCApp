package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samir.TCCApp.api.ClientService;
import com.samir.TCCApp.classes.Addressess;
import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.DatabaseHelper;
import com.samir.TCCApp.classes.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samir.TCCApp.classes.Helper.*;

public class ClientDAO {
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Retrofit retrofit;

    /*private final String TABLE = "tbusuario";
    private final String COL_IDUSU = "idUsu";
    private final String COL_USER = "userName";
    private final String COL_PASS = "password";
    private final String COL_ACESS = "acessType";*/

    private Context context;

    public ClientDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
        this.context = context;
    }

    public void insertCli(Client client) {
        UserDAO userDAO = new UserDAO(context);
        User user = userDAO.returnUserAdded();

        client.setUser(user);
        client.setAddressess(new Addressess());

        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_IDCLI, client.getIdCli());
        contentValues.put(COL_IDUSU, user.getIdUsu());
        contentValues.put(COL_CEP, (byte[]) null);
        contentValues.put(COL_CPF, client.getCPF());
        contentValues.put(COL_NAMECLI, client.getNameCli());
        contentValues.put(COL_EMAILCLI, client.getEmailCli());
        contentValues.put(COL_CELCLI, client.getCelCli());
        contentValues.put(COL_COMP, client.getComp());
        contentValues.put(COL_NUMEDIF, client.getNumEdif());
        contentValues.put(COL_QTDPONT, client.getQtdPonto());
        contentValues.put(COL_IMG, client.getImagem());
        write.insert(TABLE_CLI, null, contentValues);

        postClient(client);
    }

    private void postClient(Client client) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientService clientService = retrofit.create(ClientService.class);
        Call<Client> call = clientService.insertClientAPI(client);

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Log.i("API", "Cod: " + response.code());
                if (response.isSuccessful()) {
//                    Log.i("API", "Cod: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.i("API", "Cod: ");
            }
        });

    }

    public boolean validateRegister(String user, String email) {
        Cursor res = read.rawQuery("select userName from tbusuario", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            if (res.getString(res.getColumnIndex(COL_USERNAME)).equals(user)) {
                return false;
            }
            res.moveToNext();
        }

        Cursor cursor = read.rawQuery("select emailCli from tbcliente", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COL_EMAILCLI)).equals(email)) {
                return false;
            }
            cursor.moveToNext();
        }

        return true;
    }

}
