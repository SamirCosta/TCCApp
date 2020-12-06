package com.samir.TCCApp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Product;

import static com.samir.TCCApp.fragments.HomeFragment.bagArrayListItem;
import static com.samir.TCCApp.fragments.HomeFragment.internalBag;
import static com.samir.TCCApp.fragments.HomeFragment.recyclerViewBag;

public class DetailsActivity extends AppCompatActivity {
    private MotionLayout motionLayout;
    private ImageView imageView, arrow, more, less;
    private TextView name, desc, val, qtd;
    private Button btn;

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

        btn.setOnClickListener(c -> {
//            bagArrayListItem.add(product);
            if (internalBag.getProductArrayList().size() == 0) {
                internalBag.setProductArrayList(product, internalBag, getApplicationContext());
                recyclerViewBag.getAdapter().notifyDataSetChanged();
            } else {
                boolean contain = false;
                for (int i = 0; i < internalBag.getProductArrayList().size(); i++) {
                    Product prod = internalBag.getProductArrayList().get(i);
                    if (prod.getIdProd() == product.getIdProd()) {
                        prod.setQtd(prod.getQtd() + Integer.parseInt(qtd.getText().toString()));
                        internalBag.save(internalBag, getApplicationContext());
                        contain = true;
                        break;
                    }
                }
                if (!contain) {
                    internalBag.setProductArrayList(product, internalBag, getApplicationContext());
                }
                recyclerViewBag.getAdapter().notifyDataSetChanged();
            }

            ViewGroup viewGroup = findViewById(R.id.container_toast);
            View view = getLayoutInflater().inflate(R.layout.bag_toast, viewGroup);

            Toast toast = new Toast(DetailsActivity.this);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 720);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        });

        less.setOnClickListener(c -> {
            int newQtd = Integer.parseInt(qtd.getText().toString()) - 1;
            if (newQtd > 0)
                product.setQtd(newQtd);
            qtd.setText(String.valueOf(product.getQtd()));
        });

        more.setOnClickListener(c -> {
            product.setQtd(Integer.parseInt(qtd.getText().toString()) + 1);
            qtd.setText(String.valueOf(product.getQtd()));
        });

        motionLayout.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                arrow.setAlpha(v * (-1) + 1);
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
        btn = findViewById(R.id.btnAddBag);
        more = findViewById(R.id.btnQtdMoreDet);
        less = findViewById(R.id.btnQtdLessDet);
        qtd = findViewById(R.id.qtdNum);
    }

    public void back(View view) {
        onBackPressed();
    }

}