package com.samir.TCCApp.DAO;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.samir.TCCApp.activities.MainActivity;
import com.samir.TCCApp.api.ProductService;
import com.samir.TCCApp.classes.InsertProd;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.samir.TCCApp.utils.Helper.retrofit;

public class PedidoDAO {

    public void insertProd(InsertProd insertProd, Context context){
        ProductService productService = retrofit.create(ProductService.class);
        Call<Boolean> call = productService.insertPed(insertProd);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.i("API", "Cod: " + response.code() + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    boolean b = response.body();
                    if (b) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("payment", 2);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("API", "DEU RUIM: " + t.getMessage());
            }
        });
    }

}
