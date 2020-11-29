package com.samir.TCCApp.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table tbproduto" +
                "(IdProd integer primary key, " +
                "NomeProd text," +
                "DescProd text, " +
                "ValorProd float," +
                "Observacao text," +
                "TipoProd text," +
                "CategoriaProd text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbproduto");
        onCreate(db);
    }

}
