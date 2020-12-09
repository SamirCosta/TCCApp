package com.samir.TCCApp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.PedidoView;
import com.samir.TCCApp.classes.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {
    private List<PedidoView> pedidoArrayList;
    private Context context;
    private String dateFin;
    private float valTot = 0;

    public PedidoAdapter(List<PedidoView> pedidoArrayList, Context context) {
        this.pedidoArrayList = pedidoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedido_item_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PedidoView pedidoView = pedidoArrayList.get(position);
        String[] date = pedidoView.getDataPed().split(" ")[0].split("/");
        String bar = "/";
        dateFin = date[1] + bar + date[0] + bar + date[2];
        holder.tvDatePed.setText(dateFin);
        holder.tvEndPed.setText(pedidoView.getLogra() + ", " + pedidoView.getNumEdif());
        valTot = 0;
        for (Product product: pedidoView.getProdutos()){
            valTot += product.getValorProd() * product.getQtd();
        }
        holder.tvPrecoTotalPed.setText("R$" + new DecimalFormat("0.00").format(valTot));

        holder.itemView.setOnClickListener(c -> {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.ped_desc);
            dialogActions(dialog, pedidoView);
            dialog.show();
        });
    }

    private void dialogActions(Dialog dialog, PedidoView pedidoView) {
        TextView tvDatePedDesc = dialog.findViewById(R.id.tvDatePedDesc);
        TextView tvPrecoTotalPedDesc = dialog.findViewById(R.id.tvPrecoTotalPedDesc);
        TextView txtEndDescPed = dialog.findViewById(R.id.txtEndDescPed);

        tvDatePedDesc.setText(dateFin);
        tvPrecoTotalPedDesc.setText(String.valueOf(valTot));
        txtEndDescPed.setText(pedidoView.getLogra());
        RecyclerView recyclerProdDescPed = dialog.findViewById(R.id.recyclerProdDescPed);
        recyclerProdDescPed.setAdapter(new AdapterDescPed(pedidoView.getProdutos()));
    }

    @Override
    public int getItemCount() {
        return pedidoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDatePed, tvPrecoTotalPed, tvEndPed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDatePed = itemView.findViewById(R.id.tvDatePed);
            tvPrecoTotalPed = itemView.findViewById(R.id.tvPrecoTotalPed);
            tvEndPed = itemView.findViewById(R.id.tvEndPed);
        }
    }

}
