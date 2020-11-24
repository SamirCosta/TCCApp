package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.samir.TCCApp.classes.Addressess;
import com.samir.TCCApp.classes.DatabaseHelper;

public class AddressessDAO {
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    private final String TABLE = "tbendereco";
    private final String COL_CEP = "CEP";
    private final String COL_LOGRA = "Logra";
    private final String COL_BAIRRO = "Bairro";
    private final String COL_CIDADE = "Cidade";
    private final String COL_ESTADO = "Estado";

    public AddressessDAO (Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public void insertAddress(Addressess addressess) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CEP, addressess.getCEP());
        contentValues.put(COL_LOGRA, addressess.getLogra());
        contentValues.put(COL_BAIRRO, addressess.getBairro());
        contentValues.put(COL_CIDADE, addressess.getCidade());
        contentValues.put(COL_ESTADO, addressess.getEstado());
        write.insert(TABLE, null, contentValues);

    }

}
