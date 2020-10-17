package com.samir.TCCApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.samir.TCCApp.R;

public class BannerFragment extends Fragment {
    private ImageView imageView;

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    public static Fragment getInstance(int position) {

        BannerFragment bannerFragment = new BannerFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        bannerFragment.setArguments(args);

        return bannerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView);

        int position = requireArguments().getInt("position");
        imageView.setImageResource(AboutFragment.imgs.get(position));

    }
}