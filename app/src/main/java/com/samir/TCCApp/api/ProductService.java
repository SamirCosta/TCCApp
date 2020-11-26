package com.samir.TCCApp.api;

import com.samir.TCCApp.classes.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("/api/Product/getAll")
    Call<List<Product>> getAllProducts();

}
