package com.samir.TCCApp.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.ScannerActivity;
import com.samir.TCCApp.adapters.SliderAdapterExample;
import com.samir.TCCApp.adapters.SliderItem;
import com.samir.TCCApp.utils.Helper;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class BottomHomeFragment extends Fragment {
    private SliderView sliderView;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SliderItem sliderItem = new SliderItem(R.drawable.nachos);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.guacamole);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.quesadilla);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.taco);
        mSliderItems.add(sliderItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cardDelivery).setOnClickListener(v -> HomeFragment.bottomNavigationViewEx.setCurrentItem(1));

        view.findViewById(R.id.cardPedRest).setOnClickListener(c -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle("Peça no restaurante");
            builder.setMessage("Este pedido é baseado nos itens da sacola, deseja continuar?");
            builder.setPositiveButton("Sim", (dialog, which) -> getActivity().startActivity(new Intent(getActivity(), ScannerActivity.class)))
                    .setNegativeButton("Ir para o cardápio", (dialog, which) -> HomeFragment.bottomNavigationViewEx.setCurrentItem(1));
            builder.create();
            builder.show();;
        });

        sliderView = view.findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new SliderAdapterExample(getActivity(), mSliderItems));

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(getResources().getColor(R.color.transparent_white));
        sliderView.setScrollTimeInSec(3);
        sliderView.setSliderAnimationDuration(1500);
        sliderView.startAutoCycle();

    }

}