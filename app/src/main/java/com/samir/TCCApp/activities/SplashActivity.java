package com.samir.TCCApp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.samir.TCCApp.R;

public class SplashActivity extends AppCompatActivity {
    public MotionLayout motionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        motionLayout = findViewById(R.id.motion_splash_layout);
//        new Load().execute();

        new Handler().postDelayed(() -> motionLayout.transitionToEnd(), 500);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, SliderIntroActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }, 1500);

    }

    private class Load extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected Void doInBackground(Void... arg0) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }

}