package com.samir.TCCApp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.$Gson$Preconditions;
import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Product;
import com.samir.TCCApp.fragments.HomeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.samir.TCCApp.activities.PaymentActivity.tvSubTotal;
import static com.samir.TCCApp.activities.PaymentActivity.tvTotalFinal;
import static com.samir.TCCApp.activities.PaymentActivity.tvTotalProd;
import static com.samir.TCCApp.fragments.HomeFragment.bagArrayListItem;
import static com.samir.TCCApp.fragments.HomeFragment.emptyBag;
import static com.samir.TCCApp.fragments.HomeFragment.internalBag;
import static com.samir.TCCApp.fragments.HomeFragment.sum;
import static com.samir.TCCApp.fragments.HomeFragment.tvEmpty;
import static com.samir.TCCApp.fragments.HomeFragment.tvTotalVal;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.MyViewHolder> {
    private ArrayList<Product> arrayList;
    private Context context;

    public BagAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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

        sum = 0;

        holder.less.setOnClickListener(c -> {
            int newQtd = Integer.parseInt(holder.qtd.getText().toString()) - 1;
            if (newQtd > 0){
                product.setQtd(newQtd);
                for (int i = 0; i < internalBag.getProductArrayList().size(); i++) {
                    Product prod = internalBag.getProductArrayList().get(i);
                    if (prod.getIdProd() == product.getIdProd()) {
                        prod.setQtd(newQtd);
                        sum -= prod.getValorProd();
                        internalBag.save(internalBag, context);
                    }
                }
            }
            holder.qtd.setText(String.valueOf(product.getQtd()));
            notifyDataSetChanged();
        });

        holder.more.setOnClickListener(a -> {
            int newQtd = Integer.parseInt(holder.qtd.getText().toString()) + 1;
            product.setQtd(newQtd);
            for (int i = 0; i < internalBag.getProductArrayList().size(); i++) {
                Product prod = internalBag.getProductArrayList().get(i);
                if (prod.getIdProd() == product.getIdProd()) {
                    prod.setQtd(newQtd);
//                    addTotal(prod);
                    internalBag.save(internalBag, context);
                }
            }
            holder.qtd.setText(String.valueOf(product.getQtd()));
            notifyDataSetChanged();
        });

        if (!arrayList.isEmpty()) {
            tvEmpty.setVisibility(View.GONE);
            emptyBag.setVisibility(View.GONE);
            tvTotalVal.setVisibility(View.VISIBLE);

            for (Product prod : arrayList) {
                sum += prod.getValorProd() * prod.getQtd();
            }
            tvTotalVal.setText("Total: R$" + new DecimalFormat("0.00").format(sum));
            if (tvTotalFinal != null && tvTotalProd != null && tvSubTotal != null){
                tvTotalFinal.setText("Total: R$" + sum);
                tvTotalProd.setText("R$" + sum);
                tvSubTotal.setText("R$" + sum);
            }
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

    private void addTotal(Product prod){
        sum += prod.getValorProd();
        if (tvTotalFinal != null && tvTotalProd != null && tvSubTotal != null){
            tvTotalFinal.setText("Total: R$" + sum);
            tvTotalProd.setText("R$" + sum);
            tvSubTotal.setText("R$" + sum);
        }
    }

}
