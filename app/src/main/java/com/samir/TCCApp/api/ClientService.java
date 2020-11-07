package com.samir.TCCApp.api;

import com.samir.TCCApp.classes.Client;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClientService {

    @POST("values/Post")
    Call<Client> insertClientAPI(@Body Client client);

}
