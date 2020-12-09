package com.samir.TCCApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samir.TCCApp.DAO.ProductDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.PedidoAdapter;

public class PedidosAtuaisFragment extends Fragment {
    public static RecyclerView recyclerPedAtu;
    View view;

    @Override
    public void onStart() {
        super.onStart();
        recyclerPedAtu.getAdapter().notifyDataSetChanged();
        if (ProductDAO.pedidoViews.getPedidoViews().size() > 0){
            view.findViewById(R.id.tvEmptyPedAtu).setVisibility(View.GONE);
            view.findViewById(R.id.imgEmptyPedAtu).setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_atuais, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        if (ProductDAO.pedidoViews.getPedidoViews().size() > 0){
            view.findViewById(R.id.tvEmptyPedAtu).setVisibility(View.GONE);
            view.findViewById(R.id.imgEmptyPedAtu).setVisibility(View.GONE);
        }

        recyclerPedAtu = view.findViewById(R.id.recyclerPedidosAtu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerPedAtu.setLayoutManager(layoutManager);
        PedidoAdapter pedidoAdapter = new PedidoAdapter(ProductDAO.pedidoViews.getPedidoViews(), getActivity());
        recyclerPedAtu.setAdapter(pedidoAdapter);
    }
}