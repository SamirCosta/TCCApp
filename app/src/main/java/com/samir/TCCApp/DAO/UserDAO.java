package com.samir.TCCApp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samir.TCCApp.classes.DatabaseHelper;
import com.samir.TCCApp.classes.User;

import static com.samir.TCCApp.classes.Helper.*;

public class UserDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Context context;

    public UserDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
        this.context = context;
    }

    public void insertUser(User user){
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_IDUSU, user.getIdUsu());
        contentValues.put(COL_USERNAME, user.getUserName());
        contentValues.put(COL_PASS, user.getPassword());
        contentValues.put(COL_ACESS, user.getAcessType());
        write.insert(TABLE_USU, null, contentValues);
    }

    public User returnUserAdded() {
        User user = new User();

        Cursor res = read.rawQuery("select * from tbusuario ORDER BY idUsu DESC LIMIT 1", null);
//        res.moveToFirst();
        res.moveToLast();

//        while(!res.isAfterLast()){
        user.setIdUsu(Integer.parseInt(res.getString(res.getColumnIndex(COL_IDUSU))));
        user.setUserName(res.getString(res.getColumnIndex(COL_USERNAME)));
        user.setPassword(res.getString(res.getColumnIndex(COL_PASS)));
//            res.moveToNext();
//        }

        return user;
    }

    public boolean validateLogin(String user, String pass){
        Cursor cursor = read.rawQuery("select idUsu, userName, password from tbusuario", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(COL_USERNAME)).equals(user)
                    && cursor.getString(cursor.getColumnIndex(COL_PASS)).equals(pass)) {
                SharedPreferences pref;
                SharedPreferences.Editor editor;
                pref = context.getSharedPreferences(ARQUIVO_LOGIN, 0);
                editor = pref.edit();
                editor.putInt("id", cursor.getColumnIndex("idCli"));
                editor.apply();
                return true;
            }
            cursor.moveToNext();
        }

        return false;
    }

}
