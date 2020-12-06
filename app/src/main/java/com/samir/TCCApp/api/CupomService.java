package com.samir.TCCApp.api;

import com.samir.TCCApp.adapters.Cupom;
import com.samir.TCCApp.classes.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CupomService {

    @GET("api/Product/getCupom")
    Call<List<Cupom>> getCupons();

}
