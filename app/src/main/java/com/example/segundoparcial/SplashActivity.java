package com.example.segundoparcial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Retraso de 3 segundos antes de iniciar la actividad principal
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, com.example.segundoparcial.MenuActivity.class));
            finish();
        }, 3000);
    }
}
