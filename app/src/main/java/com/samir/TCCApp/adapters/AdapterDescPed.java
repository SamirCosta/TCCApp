package com.samir.TCCApp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.samir.TCCApp.utils.Helper.BASE_URL;

public class AdapterDescPed extends RecyclerView.Adapter<AdapterDescPed.MyViewHolder> {
    private List<Product> arrayList;

    public AdapterDescPed(List<Product> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prod_desc_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.tvItemProdDes.setText(product.getName());
        holder.tvProdDescPrice.setText("R$" + product.getValorProd());
        holder.qtdNumDes.setText(String.valueOf(product.getQtd()));
        Picasso.get().load(BASE_URL + product.getImagem()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemProdDes, tvProdDescPrice, qtdNumDes;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemProdDes = itemView.findViewById(R.id.tvItemProdDes);
            tvProdDescPrice = itemView.findViewById(R.id.tvProdDescPrice);
            imageView = itemView.findViewById(R.id.imageDesItem);
            qtdNumDes = itemView.findViewById(R.id.qtdNumDesPed);
        }
    }

}
