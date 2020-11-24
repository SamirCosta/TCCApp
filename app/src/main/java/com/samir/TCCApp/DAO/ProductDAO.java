package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.samir.TCCApp.classes.DatabaseHelper;
import com.samir.TCCApp.classes.Product;

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

    public void insertProd(Product product){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IDPROD, product.getIdProd());
        contentValues.put(COL_NOMEPROD, product.getName());
        contentValues.put(COL_DESCPROD, product.getDescProd());
        contentValues.put(COL_VALPROD, product.getValorProd());
        contentValues.put(COL_OBSPROD, product.getObservacao());
        contentValues.put(COL_TIPOPROD, product.getTipoProd());
        contentValues.put(COL_CATPROD, product.getCategoriaProd());
        write.insert(TABLE, null, contentValues);
    }

}
