package com.example.segundoparcial;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class MenuActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private ImageView imgCaricatura, imgAccion, imgTerror;
    private LinearLayout sectionCaricatura, sectionAccion, sectionTerror;
    private String nombre;
    private int edad;
    private String genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        imgCaricatura = findViewById(R.id.imgCaricatura);
        imgAccion = findViewById(R.id.imgAccion);
        imgTerror = findViewById(R.id.imgTerror);
        sectionCaricatura = findViewById(R.id.sectionCaricatura);
        sectionAccion = findViewById(R.id.sectionAccion);
        sectionTerror = findViewById(R.id.sectionTerror);

        // Mostrar diálogo para capturar datos
        mostrarDialogoCapturaDatos();

        imgCaricatura.setOnClickListener(v -> abrirReproductor("Caricatura", imgCaricatura.getId()));
        imgAccion.setOnClickListener(v -> abrirReproductor("Acción", imgAccion.getId()));
        imgTerror.setOnClickListener(v -> abrirReproductor("Terror", imgTerror.getId()));
    }

    private void mostrarDialogoCapturaDatos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingrese sus datos");

        // Vista personalizada con campos de entrada
        final View customLayout = getLayoutInflater().inflate(R.layout.dialogo_captura_datos, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etNombre = customLayout.findViewById(R.id.etNombre);
                EditText etEdad = customLayout.findViewById(R.id.etEdad);
                RadioGroup rgGenero = customLayout.findViewById(R.id.rgGenero);

                nombre = etNombre.getText().toString();
                try {
                    edad = Integer.parseInt(etEdad.getText().toString());
                } catch (NumberFormatException e) {
                    edad = 0;
                }
                genero = ((RadioButton) customLayout.findViewById(rgGenero.getCheckedRadioButtonId())).getText().toString();

                if (nombre.isEmpty() || edad <= 0 || rgGenero.getCheckedRadioButtonId() == -1) {
                    mostrarDialogoCapturaDatos();
                } else {
                    textViewWelcome.setText("Hola " + nombre + ", de acuerdo a tu edad: " + edad);
                    configurarCategorias();
                }
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void configurarCategorias() {
        sectionCaricatura.setVisibility(View.VISIBLE);
        sectionAccion.setVisibility(View.GONE);
        sectionTerror.setVisibility(View.GONE);

        if (edad > 12) {
            sectionAccion.setVisibility(View.VISIBLE);
        }

        if (edad >= 18) {
            sectionTerror.setVisibility(View.VISIBLE);
        }
    }

    private void abrirReproductor(String categoria, int imageId) {
        Intent intent = new Intent(MenuActivity.this, com.example.segundoparcial.ReproductorActivity.class);
        intent.putExtra("categoria", categoria);
        intent.putExtra("nombre", nombre);
        intent.putExtra("edad", edad);
        intent.putExtra("genero", genero);
        intent.putExtra("imageId", imageId);
        startActivity(intent);
    }
}
