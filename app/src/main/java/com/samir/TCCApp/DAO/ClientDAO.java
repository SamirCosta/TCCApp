package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samir.TCCApp.api.ClientService;
import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.DatabaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientDAO {
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Retrofit retrofit;

    /*private final String TABLE = "tbusuario";
    private final String COL_IDUSU = "idUsu";
    private final String COL_USER = "userName";
    private final String COL_PASS = "password";
    private final String COL_ACESS = "acessType";*/

    private final String TABLE = "tbcliente";
    private final String COL_IDCLI = "idCli";
    private final String COL_IDUSU = "idUsu";
    private final String COL_CEP = "CEP";
    private final String COL_CPF = "CPF";
    private final String COL_NAMECLI = "nameCli";
    private final String COL_EMAILCLI = "emailCli";
    private final String COL_CELCLI = "celCli";
    private final String COL_COMP = "comp";
    private final String COL_NUMEDIF = "numEdif";
    private final String COL_QTDPONT = "qtdPonto";
    private final String COL_IMG = "imagem";

    public ClientDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public void insertCli(Client client){
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_IDCLI, client.getIdCli());
        contentValues.put(COL_IDUSU, returnId());
        contentValues.put(COL_CEP, (byte[]) null);
        contentValues.put(COL_CPF, client.getCPF());
        contentValues.put(COL_NAMECLI, client.getNameCli());
        contentValues.put(COL_EMAILCLI, client.getEmailCli());
        contentValues.put(COL_CELCLI, client.getCelCli());
        contentValues.put(COL_COMP, client.getComp());
        contentValues.put(COL_NUMEDIF, client.getNumEdif());
        contentValues.put(COL_QTDPONT, client.getQtdPonto());
        contentValues.put(COL_IMG, client.getImagem());
        write.insert(TABLE, null, contentValues);

        requestApi(client);
    }

    private void requestApi(Client client) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://localhost:44310/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        192.168.10.1

        ClientService clientService = retrofit.create(ClientService.class);
        Call<Client> call = clientService.insertClientAPI(client);

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()){
                    Client client1 = response.body();
                    Log.i("API", "Cod: " + response.code() + "ID: " + client1.getNameCli());
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });

    }

    private int returnId(){
        int id = 0;

        Cursor res = read.rawQuery( "select idUsu from tbusuario ORDER BY idUsu DESC LIMIT 1", null );
//        res.moveToFirst();
        res.moveToLast();

//        while(!res.isAfterLast()){
            id = Integer.parseInt(res.getString(res.getColumnIndex("idUsu")));
//            res.moveToNext();
//        }

        return id;
    }

}
