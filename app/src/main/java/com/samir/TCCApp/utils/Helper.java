package com.samir.TCCApp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Helper {

    public static String BASE_URL = "https://webapitcc.azurewebsites.net";
    public static String ARQUIVO_CLIENT = "client";
    public static String ARQUIVO_ADDRESS = "addressess";

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

}
