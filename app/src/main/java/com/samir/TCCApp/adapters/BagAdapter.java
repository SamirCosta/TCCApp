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
import com.samir.TCCApp.fragments.HomeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.samir.TCCApp.fragments.HomeFragment.emptyBag;
import static com.samir.TCCApp.fragments.HomeFragment.tvEmpty;
import static com.samir.TCCApp.fragments.HomeFragment.tvTotalVal;

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
        Product product = arrayList.get(position);
        holder.item.setText(product.getName());
        holder.imageView.setImageResource(R.drawable.nachos);
        holder.qtd.setText(String.valueOf(product.getQtd()));
        holder.val.setText("R$" + product.getValorProd());

        holder.less.setOnClickListener(c -> {
            int newQtd = Integer.parseInt(holder.qtd.getText().toString()) - 1;
            Log.i("QTD", "" + newQtd);
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

        float sum = 0;
        if (!arrayList.isEmpty()) {
            tvEmpty.setVisibility(View.GONE);
            emptyBag.setVisibility(View.GONE);
            tvTotalVal.setVisibility(View.VISIBLE);

            for (Product prod : arrayList) {
                sum += prod.getValorProd();
            }
            tvTotalVal.setText("Total: R$" + new DecimalFormat("0.00").format(sum));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, less, more;
        TextView item, qtd, val;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewBagItem);
            item = itemView.findViewById(R.id.tvItemBag);
            less = itemView.findViewById(R.id.btnQtdLess);
            more = itemView.findViewById(R.id.btnQtdMore);
            qtd = itemView.findViewById(R.id.qtdNum);
            val = itemView.findViewById(R.id.tvItemBagPrice);
        }
    }

}
