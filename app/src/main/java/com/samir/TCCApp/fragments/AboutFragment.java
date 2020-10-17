package com.samir.TCCApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;

import static java.lang.Math.abs;

public class AboutFragment extends Fragment {
    ViewPager2 viewPager2;
    public static ArrayList<Integer> imgs = new ArrayList<>();
    private BannerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        viewPager2 = view.findViewById(R.id.viewPager);

        imgs.add(R.drawable.predio);
        imgs.add( R.drawable.predio);
        imgs.add(R.drawable.predio);

        adapter = new BannerAdapter(getActivity(), imgs.size());
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                Handler slider = new Handler();
                Runnable runnable = () -> viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);

                if ((imgs.size() - 2) == position){
                    imgs.addAll(imgs);
                    adapter.itemsCount = imgs.size();
                    adapter.notifyDataSetChanged();
                }

                slider.removeCallbacks(runnable);
                slider.postDelayed(runnable, 3000);

                super.onPageSelected(position);
            }
        });

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setUserInputEnabled(false);
        //viewPager2.endFakeDrag();
//        viewPager2.beginFakeDrag();

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        /*compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                Log.i("Posição:", ""+position);
                *//*float ref = 1 - abs(position);
                page.setScaleY((float) (0.85 + ref * 0.15));*//*
            }
        });*/

        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        viewPager2.setPageTransformer(compositePageTransformer);

        return view;
    }

    public class BannerAdapter extends FragmentStateAdapter {
        int itemsCount;

        public BannerAdapter(@NonNull FragmentActivity fragmentActivity, int itemsCount) {
            super(fragmentActivity);
            this.itemsCount = itemsCount;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return BannerFragment.getInstance(position);
        }

        @Override
        public int getItemCount() {
            return itemsCount;
        }
    }

}