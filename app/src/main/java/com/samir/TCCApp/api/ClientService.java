package com.samir.TCCApp.api;

import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClientService {

    @POST("/api/Client/Post")
    Call<Client> insertClientAPI(@Body Client client);

    @GET("/api/Client/getAll")
    Call<List<Client>> getAllClients();

}
