package com.samir.TCCApp.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS tbcliente");
        db.execSQL("DROP TABLE IF EXISTS tbusuario");
        db.execSQL("DROP TABLE IF EXISTS tbendereco");

        db.execSQL("create table tbusuario " +
                "(idUsu int primary key autoincrement, " +
                "userName text unique, " +
                "password text, " +
                "acessType text default 1)"
        );

        db.execSQL("create table tbendereco" +
                "(CEP text primary key," +
                "Logra text not null," +
                "Bairro text not null," +
                "Cidade text not null," +
                "Estado text not null," +
                "UF text not null)"
        );

        db.execSQL("create table tbcliente " +
                "(idCli int primary key, " +
                "idUsu int," +
                "CEP text," +
                "CPF int, " +
                "nameCli text, " +
                "emailCli text, " +
                "celCli int, " +
                "comp text, " +
                "numEdif int, " +
                "qtdPonto float, " +
                "imagem text," +
                "FOREIGN KEY ( idUsu  ) REFERENCES tbusuario ( idUsu  )," +
                "FOREIGN KEY ( CEP  ) REFERENCES tbendereco ( CEP  ))"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbcliente");
        db.execSQL("DROP TABLE IF EXISTS tbusuario");
        db.execSQL("DROP TABLE IF EXISTS tbendereco");
        onCreate(db);
    }

}
