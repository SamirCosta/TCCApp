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

import static com.samir.TCCApp.DAO.ProductDAO.pedidoViewArrayAnte;

public class PedidosAnterioresFragment extends Fragment {
    public static RecyclerView recyclerPedAnte;
    View view;

    @Override
    public void onStart() {
        super.onStart();
        recyclerPedAnte.getAdapter().notifyDataSetChanged();
        if (pedidoViewArrayAnte.size() > 0){
            view.findViewById(R.id.imgEmptyPedAnt).setVisibility(View.GONE);
            view.findViewById(R.id.tvEmptyPedAnt).setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_anteriores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        recyclerPedAnte = view.findViewById(R.id.recyclerPedidosAnt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerPedAnte.setLayoutManager(layoutManager);
        PedidoAdapter pedidoAdapter = new PedidoAdapter(pedidoViewArrayAnte, getActivity());
        recyclerPedAnte.setAdapter(pedidoAdapter);
    }
}