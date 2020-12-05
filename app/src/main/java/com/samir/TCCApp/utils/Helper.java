package com.samir.TCCApp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Helper {

    public static String BASE_URL = "https://webapitcc.azurewebsites.net";
    public static String ARQUIVO_CLIENT = "client";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static String TABLE_PROD = "tbproduto";
    public static String COL_IDPROD = "IdProd";
    public static String COL_NOMEPROD = "NomeProd";
    public static String COL_DESCPROD = "DescProd";
    public static String COL_VALPROD = "ValorProd";
    public static String COL_OBSPROD = "Observacao";
    public static String COL_TIPOPROD = "TipoProd";
    public static String COL_CATPROD = "CategoriaProd";
    public static String COL_IMG = "Imagem";

    public static void hideKeyBoard(Context context, View v){
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void snackbar(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

}
