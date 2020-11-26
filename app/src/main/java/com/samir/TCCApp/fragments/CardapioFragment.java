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
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.AdapterCardapio;

import static com.samir.TCCApp.activities.MainActivity.productDAO;

public class CardapioFragment extends Fragment{
    private RecyclerView recyclerViewCardapio;
    private MotionLayout motionLayout;
    private CardView filtros, order;

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
        AdapterCardapio adapterCardapio = new AdapterCardapio(productDAO.getProducts(), getActivity());
        recyclerViewCardapio.setAdapter(adapterCardapio);

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
            /*AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
//            mBuilder.setTitle("Ordenar");
            View mView = getLayoutInflater().inflate(R.layout.order_layout, null);

            mBuilder.setView(mView);
            Dialog dialog = mBuilder.create();
            dialog.show();*/

            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.order_layout);
            dialogActions(dialog);
            dialog.show();

        });

        return view;
    }

    private void dialogActions(Dialog dialog) {
        TextView tvOK = dialog.findViewById(R.id.buttonOrder);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        tvOK.setOnClickListener(a -> {
            dialog.cancel();
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.rbPrecoMaior:
                    msgToast("Maior -> menor");
                case R.id.rbPrecoMenor:
                    msgToast("Menor -> maior");
                case R.id.rbTempo:
                    msgToast("Tempo");
                case R.id.rbMaisVendido:
                    msgToast("Mais vendidos");
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
    }
}