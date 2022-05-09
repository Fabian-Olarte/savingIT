package com.project.savingit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class inicioActivity extends AppCompatActivity {

    Button buttonIniciarSesion;
    Button buttonRegistrarse;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        buttonRegistrarse = findViewById(R.id.buttonRegistrarse);

        auth = FirebaseAuth.getInstance();

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

    @Override
    protected void onStart() {
        super.onStart();

        if  (auth.getCurrentUser() != null){
            startActivity(new Intent(inicioActivity.this, MainActivity.class));
            finish();
        }
    }

}