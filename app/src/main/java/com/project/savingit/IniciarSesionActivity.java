package com.project.savingit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IniciarSesionActivity extends AppCompatActivity {

    Button buttonCrearCuenta;
    Button buttonIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        buttonCrearCuenta = findViewById(R.id.buttonCrearCuenta2);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion2);

        buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IniciarSesionActivity.this, RegisterActivity.class));
                finish();
            }
        });

        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IniciarSesionActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}