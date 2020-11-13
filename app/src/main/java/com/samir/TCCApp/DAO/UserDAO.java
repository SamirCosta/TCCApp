package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.samir.TCCApp.classes.DatabaseHelper;
import com.samir.TCCApp.classes.User;

public class UserDAO {
    private final String TABLE = "tbusuario";
//    private final String COL_IDUSU = "idUsu";
    private final String COL_USERNAME = "userName";
    private final String COL_PASS = "password";
    private final String COL_ACESS = "acessType";

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public UserDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public void insertUser(User user){
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_IDUSU, user.getIdUsu());
        contentValues.put(COL_USERNAME, user.getUserName());
        contentValues.put(COL_PASS, user.getPassword());
        contentValues.put(COL_ACESS, user.getAcessType());
        write.insert(TABLE, null, contentValues);
    }

}
