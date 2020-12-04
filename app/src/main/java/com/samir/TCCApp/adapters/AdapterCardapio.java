package com.samir.TCCApp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.DetailsActivity;
import com.samir.TCCApp.classes.Product;

import java.util.ArrayList;

public class AdapterCardapio extends RecyclerView.Adapter<AdapterCardapio.MYViewHolder> {
    private ArrayList<Product> arrayList;
    private Context context;

    public AdapterCardapio(ArrayList<Product> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_cardapio_right, parent, false);
        return new MYViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {

        Product product = arrayList.get(position);
        holder.name.setText(product.getName());
        holder.desc.setText(product.getDescProd());
        holder.imgItem.setImageResource(R.drawable.nachos);
//        holder.imgItem.setImageResource(product.getImage());
//        Picasso.get().load("https://i.postimg.cc/R0S148dv/AQTzbaG.jpg").into(holder.imgItem);

        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("item", product);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context,
                    holder.imgItem,
                    ViewCompat.getTransitionName(holder.imgItem));
            context.startActivity(intent, activityOptionsCompat.toBundle());
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MYViewHolder extends RecyclerView.ViewHolder{
        ImageView imgItem;
        TextView name, desc;
        CardView item;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imageViewItemImg);
            name = itemView.findViewById(R.id.tvItemName);
            item = itemView.findViewById(R.id.itemCardapio);
            desc = itemView.findViewById(R.id.tvDescAdapter);
        }
    }

}
