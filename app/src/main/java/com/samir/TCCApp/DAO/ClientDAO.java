package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samir.TCCApp.activities.AddressActivity;
import com.samir.TCCApp.api.ClientService;
import com.samir.TCCApp.classes.Addressess;
import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.DatabaseHelper;
import com.samir.TCCApp.classes.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samir.TCCApp.utils.Helper.*;

public class ClientDAO {
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Retrofit retrofit;
    public static ArrayList<Addressess> addressessArrayList = new ArrayList<>();

    private Context context;

    public ClientDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
        this.context = context;
        requestClients();
    }

    /*public void insertCli(Client client) {
        int idUsu = 0;
        String cep = null;
        UserDAO userDAO = new UserDAO(context);

        if (client.getUser() == null) {
            User user = userDAO.returnUserAdded();
            idUsu = user.getIdUsu();

            client.setUser(user);
            client.setAddressess(new Addressess());

            postClient(client);
        } else {
            idUsu = client.getUser().getIdUsu();
            userDAO.insertUser(client.getUser());
            if (client.getAddressess() != null) {
                Addressess addressess = client.getAddressess();
                addressess.setIdCli(client.getIdCli());
                cep = addressess.getCEP();
                String vir = ",";
                addressess.setAddress(addressess.getLogra() + vir + client.getNumEdif() + vir +
                        addressess.getBairro() + vir + addressess.getCidade() + vir + addressess.getCEP());
                addressessArrayList.add(addressess);
            } else {
                client.setAddressess(new Addressess());
            }
        }

        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_IDCLI, client.getIdCli());
        contentValues.put(COL_IDUSU, idUsu);
        contentValues.put(COL_CEP, cep);
        contentValues.put(COL_CPF, client.getCPF());
        contentValues.put(COL_NAMECLI, client.getNameCli());
        contentValues.put(COL_EMAILCLI, client.getEmailCli());
        contentValues.put(COL_CELCLI, client.getCelCli());
        contentValues.put(COL_COMP, client.getComp());
        contentValues.put(COL_NUMEDIF, client.getNumEdif());
        contentValues.put(COL_QTDPONT, client.getQtdPonto());
        contentValues.put(COL_IMG, client.getImagem());
        write.insert(TABLE_CLI, null, contentValues);

    }*/

    public void postClient(Client client) {
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

    /*public ArrayList<Client> getClients(){
        ArrayList<Client> clientArrayList = new ArrayList<>();
        Cursor res =  read.rawQuery( "select * from tbcliente" , null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            Client client = new Client();
            client.setIdProd(res.getInt(res.getColumnIndex(COL_IDPROD)));
            client.setName(res.getString(res.getColumnIndex(COL_NOMEPROD)));

            clientArrayList.add(client);
            res.moveToNext();
        }

        return clientArrayList;
    }*/

    public void requestClients() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientService clientService = retrofit.create(ClientService.class);
        Call<List<Client>> call = clientService.getAllClients();

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful()) {
                    List<Client> clients = response.body();
                    for (Client client : clients) {
                        Log.i("CLIENT", "" + client.getNameCli() + "  " + client.getIdCli());
//                        insertCli(client);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {

            }
        });

    }

}
