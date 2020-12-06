package com.samir.TCCApp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.SliderAdapterExample;
import com.samir.TCCApp.adapters.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class AboutFragment extends Fragment {
    private SliderView sliderView;
    private List<SliderItem> mSliderItems = new ArrayList<>();
    private CardView cardLoc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_about, container, false);

        SliderItem sliderItem = new SliderItem(R.drawable.predio);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.predio);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.predio);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.predio);
        mSliderItems.add(sliderItem);

        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardLoc = view.findViewById(R.id.cardLoc);

        cardLoc.setOnClickListener(c -> {
            double latitude = -23.5571871;
            double longitude = -46.6486407;
            String strUri = "http://maps.google.com/maps?q=loc:" +
                    latitude + "," + longitude;
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(strUri));

            intent.setClassName("com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity");

            startActivity(intent);
        });

        sliderView = view.findViewById(R.id.imageSliderAbout);
        sliderView.setSliderAdapter(new SliderAdapterExample(getActivity(), mSliderItems));

        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(getResources().getColor(R.color.transparent_white));
        sliderView.setScrollTimeInSec(3);
        sliderView.setSliderAnimationDuration(1500);
        sliderView.startAutoCycle();

    }
}