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

import java.text.DecimalFormat;
import java.util.List;

public class CuponsAdapter extends RecyclerView.Adapter<CuponsAdapter.MyViewHolder> {
    private List<Cupom> arrayList;
    private Context context;

    public CuponsAdapter(List<Cupom> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cupom_item, parent, false);
        return new CuponsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cupom cupom = arrayList.get(position);
        holder.tvCod.setText(cupom.getCodCupom());
        holder.tvDesc.setText(new DecimalFormat("0").format(cupom.getDesconto() * 100) + "%");
        String[] date = cupom.getDataTerm().split("T")[0].split("-");
        holder.tvDate.setText("*Válido até: " + date[2] + "/" + date[1] + "/" + date[0]);

        holder.itemView.setOnClickListener(c -> {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.cupom_dialog);
            dialogActions(dialog);
            dialog.show();
        });
    }

    private void dialogActions(Dialog dialog) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCod, tvDesc, tvDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCod = itemView.findViewById(R.id.tvCodCupom);
            tvDesc = itemView.findViewById(R.id.tvDescCupom);
            tvDate = itemView.findViewById(R.id.tvDescDateCupom);
        }

    }

}
