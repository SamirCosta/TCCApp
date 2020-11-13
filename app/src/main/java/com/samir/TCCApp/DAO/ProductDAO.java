package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.samir.TCCApp.classes.DatabaseHelper;

import retrofit2.Retrofit;

public class ProductDAO {

    private final String TABLE = "tbproduto";
    private final String COL_IDPROD = "IdProd";
    private final String COL_NOMEPROD = "NomeProd";
    private final String COL_DESCPROD = "DescProd";
    private final String COL_VALPROD = "ValorProd";
    private final String COL_OBSPROD = "Observacao";
    private final String COL_TIPOPROD = "TipoProd";
    private final String COL_CATPROD = "CategoriaProd";

    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Retrofit retrofit;

    public ProductDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    /*public void insertProd(){
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_IDCLI, client.getIdCli());
        contentValues.put(COL_IDPROD, returnId());
        contentValues.put(COL_NOMEPROD, );
        contentValues.put(COL_DESCPROD, client.getCPF());
        contentValues.put(COL_VALPROD, client.getNameCli());
        contentValues.put(COL_OBSPROD, client.getEmailCli());
        contentValues.put(COL_TIPOPROD, client.getCelCli());
        contentValues.put(COL_CATPROD, client.getComp());
        write.insert(TABLE, null, contentValues);
    }*/

}
