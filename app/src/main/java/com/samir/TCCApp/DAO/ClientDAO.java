package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.DatabaseHelper;

public class ClientDAO {
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    /*private final String TABLE = "tbusuario";
    private final String COL_IDUSU = "idUsu";
    private final String COL_USER = "userName";
    private final String COL_PASS = "password";
    private final String COL_ACESS = "acessType";*/

    private final String TABLE = "idCli";
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
        contentValues.put(COL_IDCLI, client.getIdCli());
        contentValues.put(COL_IDUSU, client.getUser().getUserName());
        contentValues.put(COL_CEP, client.getAddressess().getCEP());
        contentValues.put(COL_CPF, client.getCPF());
        contentValues.put(COL_NAMECLI, client.getNameCli());
        contentValues.put(COL_EMAILCLI, client.getEmailCli());
        contentValues.put(COL_CELCLI, client.getCelCli());
        contentValues.put(COL_COMP, client.getComp());
        contentValues.put(COL_NUMEDIF, client.getNumEdif());
        contentValues.put(COL_QTDPONT, client.getQtdPonto());
        contentValues.put(COL_IMG, client.getImagem());
        write.insert(TABLE, null, contentValues);
    }

}
