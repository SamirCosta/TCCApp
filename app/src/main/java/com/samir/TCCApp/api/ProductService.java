package com.samir.TCCApp.api;

import com.samir.TCCApp.classes.InsertProd;
import com.samir.TCCApp.classes.PedidoView;
import com.samir.TCCApp.classes.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductService {

    @GET("/api/Product/getAll")
    Call<List<Product>> getAllProducts();

    @POST("/api/Product/InsertPed")
    Call<Boolean> insertPed(@Body InsertProd insertProd);

    @GET("/api/Product/getPed")
    Call<List<PedidoView>> getPeds(@Query("idCli") String idCli);

}
