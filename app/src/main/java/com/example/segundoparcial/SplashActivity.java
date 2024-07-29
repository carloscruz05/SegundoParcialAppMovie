package com.example.segundoparcial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView logo = findViewById(R.id.logo);


        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_logo);


        logo.startAnimation(rotateAnimation);


        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MenuActivity.class));
            finish();
        }, 3000);
    }
}
