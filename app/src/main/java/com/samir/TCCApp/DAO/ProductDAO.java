package com.samir.TCCApp.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samir.TCCApp.activities.MainActivity;
import com.samir.TCCApp.api.ProductService;
import com.samir.TCCApp.classes.DatabaseHelper;
import com.samir.TCCApp.classes.InternalPed;
import com.samir.TCCApp.classes.PedidoView;
import com.samir.TCCApp.classes.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samir.TCCApp.fragments.PedidosAtuaisFragment.recyclerPedAtu;
import static com.samir.TCCApp.utils.Helper.*;

public class ProductDAO {
    //    public static List<PedidoView> pedidoViews = new ArrayList<>();
    public static InternalPed pedidoViews;
    public static ArrayList<PedidoView> pedidoViewArrayAnte = new ArrayList<>();
    private SQLiteDatabase write;
    private SQLiteDatabase read;
//    private List<Product> products = new ArrayList<>();

    public ProductDAO(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
        requestProducts();
//        deleteAll();
    }

    /*public Integer deleteAll () {
        return write.delete(TABLE_PROD,
                null,
                null);
    }*/

    public ArrayList<Product> getProducts(String query) {
        ArrayList<Product> productArrayList = new ArrayList<>();
        Cursor res = read.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Product product = new Product();
            product.setIdProd(res.getInt(res.getColumnIndex(COL_IDPROD)));
            product.setName(res.getString(res.getColumnIndex(COL_NOMEPROD)));
            product.setDescProd(res.getString(res.getColumnIndex(COL_DESCPROD)));
            product.setValorProd(res.getFloat(res.getColumnIndex(COL_VALPROD)));
            product.setObservacao(res.getString(res.getColumnIndex(COL_OBSPROD)));
            product.setTipoProd(res.getString(res.getColumnIndex(COL_TIPOPROD)));
            product.setCategoriaProd(res.getString(res.getColumnIndex(COL_CATPROD)));
            product.setImagem(res.getString(res.getColumnIndex(COL_IMG)));

            productArrayList.add(product);
            res.moveToNext();
        }

        return productArrayList;
    }

    public void insertProd(Product product) {
        boolean contains = true;
        ArrayList<Integer> ids = this.getIds();
        for (int id : ids) {
            if (id == product.getIdProd()) contains = false;
        }

        if (contains) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_IDPROD, product.getIdProd());
            contentValues.put(COL_NOMEPROD, product.getName());
            contentValues.put(COL_DESCPROD, product.getDescProd());
            contentValues.put(COL_VALPROD, product.getValorProd());
            contentValues.put(COL_OBSPROD, product.getObservacao());
            contentValues.put(COL_TIPOPROD, product.getTipoProd());
            contentValues.put(COL_CATPROD, product.getCategoriaProd());
            contentValues.put(COL_IMG, product.getImagem());

            write.insert(TABLE_PROD, null, contentValues);
        }
    }

    public void requestProducts() {
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getAllProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    for (Product product : products) {
//                        Log.i("PRODUCT", "" + product.getName() + "  " + product.getIdProd());
                        insertProd(product);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    public ArrayList<Integer> getIds() {
        ArrayList<Integer> array_list = new ArrayList<>();

        Cursor res = read.rawQuery(String.format("select %s from %s", COL_IDPROD, TABLE_PROD), null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getInt(res.getColumnIndex(COL_IDPROD)));
            res.moveToNext();
        }

        return array_list;
    }

    public void getPeds(String idCli, boolean pay, Context context) {
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<PedidoView>> call = productService.getPeds(idCli);

        call.enqueue(new Callback<List<PedidoView>>() {
            @Override
            public void onResponse(Call<List<PedidoView>> call, Response<List<PedidoView>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        for (PedidoView pedidoView : response.body()) {
                            if (pedidoView.getProdStatus().equals("Entregue")) pedidoViewArrayAnte.add(pedidoView);
                            else pedidoViews.setPedidoViews(pedidoView, pedidoViews, context);
                        }
                    }
                }
                if (pay) {
                    recyclerPedAtu.getAdapter().notifyDataSetChanged();
                    context.deleteFile(ARQUIVO_BAG);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("payment", 2);
                    ((Activity) context).startActivity(intent);
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onFailure(Call<List<PedidoView>> call, Throwable t) {

            }
        });
    }

}
