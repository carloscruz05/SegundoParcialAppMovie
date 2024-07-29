package com.example.segundoparcial;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ReproductorActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private TextView tvInfo;
    private ImageView ivFotoPerfil;
    private VideoView videoView;
    private Button btnPlay, btnPause, btnMenu;
    private String nombre, genero;
    private int edad;
    private boolean fotoTomada = false;

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

        nombre = getIntent().getStringExtra("nombre");
        edad = getIntent().getIntExtra("edad", 0);
        genero = getIntent().getStringExtra("genero");
        String categoria = getIntent().getStringExtra("categoria");

        tvInfo.setText("Nombre: " + nombre + "\nEdad: " + edad + "\nGénero: " + genero);

        configurarVideo(categoria);

        btnPlay.setOnClickListener(v -> {
            if (!fotoTomada) {
                checkCameraPermissionAndTakePhoto();
            } else {
                videoView.start();
            }
        });

        btnPause.setOnClickListener(v -> videoView.pause());
        btnMenu.setOnClickListener(v -> finish());
    }

    private void checkCameraPermissionAndTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            showTakePhotoDialog();
        }
    }

    private void showTakePhotoDialog() {
        new AlertDialog.Builder(this)
                .setMessage("¿Quieres tomar una foto antes de ver el video?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                })
                .setNegativeButton("No", (dialog, which) -> {
                    videoView.start();
                })
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivFotoPerfil.setImageBitmap(imageBitmap);
            fotoTomada = true;
            videoView.start();
        } else {
            videoView.start();
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
            default:
                break;
        }
        videoView.setVideoPath(videoPath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showTakePhotoDialog();
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("Permiso de cámara denegado. No se puede tomar una foto.")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }
        }
    }
}
