package com.samir.TCCApp.api;

import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.InsertProd;
import com.samir.TCCApp.classes.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductService {

    @GET("/api/Product/getAll")
    Call<List<Product>> getAllProducts();

    @POST("/api/Product/InsertPed")
    Call<Boolean> insertPed(@Body InsertProd insertProd);

}
