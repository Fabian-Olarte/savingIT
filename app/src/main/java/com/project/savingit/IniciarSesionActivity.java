package com.project.savingit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class IniciarSesionActivity extends AppCompatActivity {

    Button buttonCrearCuenta;
    Button buttonIniciarSesion;
    EditText inputCorreo;
    EditText inputContra;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        buttonCrearCuenta = findViewById(R.id.buttonCrearCuenta2);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion2);
        inputCorreo = findViewById(R.id.inputCorreo);
        inputContra = findViewById(R.id.inputContraseña);

        auth = FirebaseAuth.getInstance();

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

                login();

            }
        });
    }

    private void login(){
        String email = inputCorreo.getText().toString().trim();
        String contra = inputContra.getText().toString().trim();

        if (email.isEmpty()){
            inputCorreo.setError("Ingrese su correo");
            inputCorreo.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputCorreo.setError("Por favor ingrese un correo valido");
            inputCorreo.requestFocus();
            return;
        }

        if (contra.isEmpty()){
            inputContra.setError("Ingrese su contraseña");
            inputContra.requestFocus();
            return;
        }

        if (contra.length() < 6){
            inputContra.setError("La contraseña debe tener minimo 6 caracteres");
            inputContra.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(email,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(IniciarSesionActivity.this, MainActivity.class));
                    finish();
                }

                else {
                    Toast.makeText(getApplicationContext(),"Ocurrio un error", Toast.LENGTH_LONG);
                }
            }
        });

    }
    
}