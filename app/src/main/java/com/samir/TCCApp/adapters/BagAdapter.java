package com.samir.TCCApp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Product;

import java.util.ArrayList;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.MyViewHolder> {
    private ArrayList<Product> arrayList;

    public BagAdapter(ArrayList<Product> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bag_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText("Item " + position);
        holder.imageView.setImageResource(R.drawable.taco);
        Product product = arrayList.get(position);
        holder.qtd.setText(String.valueOf(product.getQtd()));

        holder.less.setOnClickListener(c -> {
            int newQtd = Integer.parseInt(holder.qtd.getText().toString()) - 1;
            Log.i("QTD", ""+newQtd);
            if (newQtd > 0)
                product.setQtd(newQtd);
                holder.qtd.setText(String.valueOf(product.getQtd()));
            notifyDataSetChanged();
        });

        holder.more.setOnClickListener(a -> {
            product.setQtd(Integer.parseInt(holder.qtd.getText().toString()) + 1);
            holder.qtd.setText(String.valueOf(product.getQtd()));
            notifyDataSetChanged();
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView, less, more;
        TextView item, qtd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewBagItem);
            item = itemView.findViewById(R.id.tvItemBag);
            less = itemView.findViewById(R.id.btnQtdLess);
            more = itemView.findViewById(R.id.btnQtdMore);
            qtd = itemView.findViewById(R.id.qtdNum);
        }
    }

}
