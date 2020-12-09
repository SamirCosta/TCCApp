package com.samir.TCCApp.DAO;

import android.util.Log;

import com.samir.TCCApp.classes.Cupom;
import com.samir.TCCApp.api.CupomService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.samir.TCCApp.utils.Helper.retrofit;

public class CupomDAO {
    public static List<Cupom> cupomArrayList = new ArrayList<>();

    public void getCupom(){
        CupomService cupomService = retrofit.create(CupomService.class);
        Call<List<Cupom>> call = cupomService.getCupons();

        call.enqueue(new Callback<List<Cupom>>() {
            @Override
            public void onResponse(Call<List<Cupom>> call, Response<List<Cupom>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cupomArrayList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Cupom>> call, Throwable t) {
                Log.i("API", "DEU RUIM: " + t.getMessage());
            }
        });
    }

}
