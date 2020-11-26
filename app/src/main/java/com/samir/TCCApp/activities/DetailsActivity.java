package com.samir.TCCApp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Product;

public class DetailsActivity extends AppCompatActivity {
    private MotionLayout motionLayout;
    private ImageView imageView, arrow;
    private TextView name, desc, val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ref();

        Product product = (Product) getIntent().getExtras().getSerializable("item");
        name.setText(product.getName());
        desc.setText(product.getDescProd());
        val.setText("R$" + product.getValorProd());
        imageView.setImageResource(R.drawable.nachos);
//        imageView.setImageResource(product.getImage());

        motionLayout.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                arrow.setAlpha(v*(-1)+1);
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                arrow.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });

    }

    private void ref() {
        imageView = findViewById(R.id.imageViewItemDetail);
        motionLayout = findViewById(R.id.motionDetails);
        arrow = findViewById(R.id.imageViewArrowDown);
        name = findViewById(R.id.tvProdName);
        desc = findViewById(R.id.tvDescrText);
        val = findViewById(R.id.tvValProd);
    }

    public void back(View view){
        onBackPressed();
    }

}