package com.andreszapata.familytasker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class Pantalla_splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);


        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.setAnimation(R.raw.animation);
        animationView.playAnimation();


        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(new Intent(Pantalla_splash.this, MainActivity.class));
                        finish();
                    }
                },
                5000
        );
    }
}
