package com.example.segundoparcial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.segundoparcial.R;

public class ReproductorActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView tvInfo;
    private ImageView ivFotoPerfil;
    private VideoView videoView;
    private Button btnPlay, btnPause, btnMenu;
    private String nombre, genero;
    private int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        tvInfo = findViewById(R.id.tvInfo);
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil);
        videoView = findViewById(R.id.videoView);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnMenu = findViewById(R.id.btnMenu);

        // Obtener datos del intent
        nombre = getIntent().getStringExtra("nombre");
        edad = getIntent().getIntExtra("edad", 0);
        genero = getIntent().getStringExtra("genero");
        String categoria = getIntent().getStringExtra("categoria");

        tvInfo.setText("Nombre: " + nombre + "\nEdad: " + edad + "\nGénero: " + genero);

        // Mostrar diálogo para foto de perfil
        tomarFotoPerfil();

        // Configurar video según la categoría
        configurarVideo(categoria);

        btnPlay.setOnClickListener(v -> videoView.start());
        btnPause.setOnClickListener(v -> videoView.pause());
        btnMenu.setOnClickListener(v -> finish());
    }

    private void tomarFotoPerfil() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivFotoPerfil.setImageBitmap(imageBitmap);
        } else {
            // Si la foto no fue tomada correctamente, regresar al menú
            finish();
        }
    }

    private void configurarVideo(String categoria) {
        String videoPath = "android.resource://" + getPackageName() + "/";
        switch (categoria) {
            case "Caricatura":
                videoPath += R.raw.caricatura_video;
                break;
            case "Acción":
                videoPath += R.raw.accion_video;
                break;
            case "Terror":
                videoPath += R.raw.terror_video;
                break;
        }
        videoView.setVideoPath(videoPath);
    }
}
