package com.example.segundoparcial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private TextView tvUserInfo;
    private ImageView imgProfile;
    private VideoView videoView;
    private Button btnPlay, btnPause, btnBackToMenu;

    private static final int CAMERA_REQUEST = 1888;

    private String category;
    private String name;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        tvUserInfo = findViewById(R.id.tvUserInfo);
        imgProfile = findViewById(R.id.imgProfile);
        videoView = findViewById(R.id.videoView);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);

        category = getIntent().getStringExtra("CATEGORY");
        name = getIntent().getStringExtra("NAME");
        age = getIntent().getIntExtra("AGE", 0);

        tvUserInfo.setText("Nombre: " + name + ", Edad: " + age);

        showProfilePictureDialog();

        btnPlay.setOnClickListener(v -> videoView.start());
        btnPause.setOnClickListener(v -> videoView.pause());
        btnBackToMenu.setOnClickListener(v -> finish());
    }

    private void showProfilePictureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para ver el video, por favor sube una foto de perfil.")
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                })
                .setNegativeButton("Cancelar", (dialog, which) -> finish())
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            imgProfile.setImageBitmap(imageBitmap);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No se pudo guardar la foto, regresando al menú.")
                    .setPositiveButton("Aceptar", (dialog, which) -> finish())
                    .create()
                    .show();
        }
    }
}
