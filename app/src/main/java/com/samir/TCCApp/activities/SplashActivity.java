package com.samir.TCCApp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.DAO.CupomDAO;
import com.samir.TCCApp.DAO.ProductDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static com.samir.TCCApp.DAO.CupomDAO.cupomArrayList;
import static com.samir.TCCApp.activities.MainActivity.productDAO;
import static com.samir.TCCApp.utils.Helper.ARQUIVO_CLIENT;

public class SplashActivity extends AppCompatActivity {
    public MotionLayout motionLayout;
    private Handler handler;
    private Runnable runnable;
    private boolean internalClient = false;
    boolean go = false;

    private final String KEY_HOME = "com.samir.TCCApp.SHORT_HOME";
    private final String KEY_CARDAPIO = "com.samir.TCCApp.SHORT_CARDAPIO";
    private final String KEY_PEDIDOS = "com.samir.TCCApp.SHORT_PEDIDOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        motionLayout = findViewById(R.id.motion_splash_layout);
        new Load().execute();

        new Handler().postDelayed(() -> {
            motionLayout.transitionToEnd();
        }, 500);

        motionLayout.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
//                motionLayout.transitionToStart();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });

        runnable = () -> {
            if (internalClient && go) {
                if (findShortcut()){
                    open(SplashActivity.this, MainActivity.class);
                }
            }else if (go){
                open(SplashActivity.this, SliderIntroActivity.class);
            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 1500);

    }

    private void open(Context context, Class activity) {
        Intent intent = new Intent(context, activity);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(SplashActivity.this, intent, optionsCompat.toBundle());
        finish();
    }

    private boolean findShortcut() {
        String KEY = getIntent().getAction();
        switch (KEY){
            case KEY_HOME:
                openMainWhithShortcuts(0);
                return false;
            case KEY_CARDAPIO:
                openMainWhithShortcuts(1);
                return false;
            case KEY_PEDIDOS:
                openMainWhithShortcuts(2);
                return false;
        }
        return true;
    }

    private void openMainWhithShortcuts(int i) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("shortcut", i);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(SplashActivity.this, intent, optionsCompat.toBundle());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);
    }

    private class Load extends AsyncTask<Void, Void, Void> {

        /*@Override
        protected void onPreExecute() {super.onPreExecute();}*/

        @Override
        protected Void doInBackground(Void... arg0) {

            if (getInternalClients() != null) {
                ClientDAO.client = getInternalClients();
                internalClient = true;
            }

            productDAO = new ProductDAO(SplashActivity.this);
            new CupomDAO().getCupom();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            go = true;
        }
    }

    private Client getInternalClients() {
        Client client = null;
        try {
            FileInputStream fis = new FileInputStream(getFileStreamPath(ARQUIVO_CLIENT));
            ObjectInputStream ois = new ObjectInputStream(fis);
            client = (Client) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return client;
    }

}