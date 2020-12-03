package com.samir.TCCApp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.AdapterCardapio;
import com.samir.TCCApp.classes.Product;

import java.util.ArrayList;

import static com.samir.TCCApp.activities.MainActivity.productDAO;
import static com.samir.TCCApp.utils.Helper.COL_CATPROD;
import static com.samir.TCCApp.utils.Helper.COL_VALPROD;
import static com.samir.TCCApp.utils.Helper.hideKeyBoard;

public class CardapioFragment extends Fragment{
    private RecyclerView recyclerViewCardapio;
    private MotionLayout motionLayout;
    private CardView filtros, order, pratos, bebidas, sobremesas;
    private SearchView searchView;

    private final int ORD_MENOR = R.id.rbPrecoMenor;
    private final int ORD_MAIOR = R.id.rbPrecoMaior;
    private final int ORD_TEMPO = R.id.rbTempo;
    private final int ORD_POP = R.id.rbMaisVendido;
    private String qPrato = "";
    private String qBebida = "";
    private String qSobremesa = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cardapio, container, false);
        ref(view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCardapio.setLayoutManager(layoutManager);
        configAdapter(productDAO.getProducts("select * from tbproduto"));

        filtros.setOnClickListener(c -> {
            motionLayout.setVisibility(View.VISIBLE);
            if (motionLayout.getProgress() == 0){
                motionLayout.transitionToEnd();
            }else{
                motionLayout.transitionToStart();
            }
        });

        motionLayout.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (motionLayout.getProgress() == 0){
                    motionLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });

        order.setOnClickListener(c -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.order_layout);
            dialogActions(dialog);
            dialog.show();
        });

        pratos.setOnClickListener(c -> {
            qPrato = "where CategoriaProd = 'Prato'";
            qBebida = "";
            qSobremesa = "";
            configAdapter(productDAO.getProducts("select * from tbproduto " + qPrato));
        });

        bebidas.setOnClickListener(c -> {
            qPrato = "";
            qBebida = "where CategoriaProd = 'Bebida'";
            qSobremesa = "";
            configAdapter(productDAO.getProducts("select * from tbproduto " + qBebida));
        });

        sobremesas.setOnClickListener(c -> {
            qPrato = "";
            qBebida = "";
            qSobremesa = "where CategoriaProd = 'Sobremesa'";
            configAdapter(productDAO.getProducts("select * from tbproduto " + qSobremesa));
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideKeyBoard(getActivity(), searchView);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                configAdapter(productDAO.getProducts("select * from tbproduto where NomeProd like '" + newText + "%'"));
                return false;
            }
        });

        return view;
    }

    private void configAdapter(ArrayList<Product> products) {
        AdapterCardapio adapterCardapio = new AdapterCardapio(products, getActivity());
        recyclerViewCardapio.setAdapter(adapterCardapio);
    }

    private void dialogActions(Dialog dialog) {
        TextView tvOK = dialog.findViewById(R.id.buttonOrder);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        tvOK.setOnClickListener(a -> {
            dialog.cancel();
            switch (radioGroup.getCheckedRadioButtonId()){
                case ORD_MAIOR:
                    configAdapter(productDAO.getProducts(String.format("select * from tbproduto %s order by %s desc ",
                            qPrato + qBebida + qSobremesa, COL_VALPROD)));
//                    msgToast("Maior -> menor");
                    break;
                case ORD_MENOR:
                    configAdapter(productDAO.getProducts(String.format("select * from tbproduto %s order by %s ",
                            qPrato + qBebida + qSobremesa, COL_VALPROD)));
//                    msgToast("Menor -> maior");
                    break;
                case ORD_TEMPO:
                    msgToast("Tempo");
                    break;
                case ORD_POP:
                    msgToast("Mais vendidos");
                    break;
            }

        });
    }

    private void msgToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }


    private void ref(View view) {
        recyclerViewCardapio = view.findViewById(R.id.recyclerCardapio);
        motionLayout = view.findViewById(R.id.motionFilter);
        filtros = view.findViewById(R.id.filterOptions);
        order = view.findViewById(R.id.orderButton);
        pratos = view.findViewById(R.id.filterPratos);
        bebidas = view.findViewById(R.id.filterBebidas);
        sobremesas = view.findViewById(R.id.filterSobremesas);
        searchView = view.findViewById(R.id.searchViewCardapio);
    }
}