package com.project.savingit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class inicioActivity extends AppCompatActivity {

    Button buttonIniciarSesion;
    Button buttonRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        buttonRegistrarse = findViewById(R.id.buttonRegistrarse);

        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(inicioActivity.this, IniciarSesionActivity.class));
            }
        });

        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(inicioActivity.this, RegisterActivity.class));
            }
        });
    }
}