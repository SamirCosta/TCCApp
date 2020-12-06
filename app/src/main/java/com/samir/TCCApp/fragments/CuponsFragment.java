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

import com.samir.TCCApp.DAO.CupomDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.CuponsAdapter;

public class CuponsFragment extends Fragment {
    private RecyclerView recyclerViewCupom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cupons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ref(view);
        configRecycler();

        if (CupomDAO.cupomArrayList.size() > 0){
            view.findViewById(R.id.imgCupomEmpty).setVisibility(View.GONE);
            view.findViewById(R.id.tvCupomEmpty).setVisibility(View.GONE);
        }

    }

    private void configRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCupom.setLayoutManager(layoutManager);
        CuponsAdapter cuponsAdapter = new CuponsAdapter(CupomDAO.cupomArrayList, getActivity());
        recyclerViewCupom.setAdapter(cuponsAdapter);
    }

    private void ref(View view) {
        recyclerViewCupom = view.findViewById(R.id.recyclerViewCupom);
    }

}