package com.samir.TCCApp.api;

import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.classes.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientService {

    @POST("/api/Client/Post")
    Call<Client> insertClientAPI(@Body Client client);

    @GET("api/Client/validateLogin")
    Call<Client> validateLogin(@Query("user") String user, @Query("pass") String pass);
//?user={user}&pass={pass}

    @GET("api/Client/validateRegisterByEmail")
    Call<Client> validateRegisterByEmail(@Query("email") String email);

    @PUT("api/Client/updateClient")
    Call<Boolean> updateClient(@Body Client client);

}
