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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText nombre, correo, contraseña;
    Button crearCuenta;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre = findViewById(R.id.inputNombreRegistro);
        correo = findViewById(R.id.inputCorreoRegistro);
        contraseña = findViewById(R.id.inputContraseñaRegistro);
        crearCuenta = findViewById(R.id.buttonIniciarSesion2);

        auth = FirebaseAuth.getInstance();

        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaCuenta();
            }
        });
    }

    private void nuevaCuenta(){

        String nomb, email, pass;

        nomb = nombre.getText().toString().trim();
        email = correo.getText().toString().trim();
        pass = contraseña.getText().toString().trim();

        if (nomb.isEmpty()){
            nombre.setError("Ingresa tu nombre");
            nombre.requestFocus();
            return;
        }

        if (email.isEmpty()){
            correo.setError("Ingresa tu correo");
            correo.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            correo.setError("Ingresa un correo valido");
            correo.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            contraseña.setError("Ingresa una contraseña");
            contraseña.requestFocus();
            return;
        }

        if (pass.length() <6){
            contraseña.setError("La contraseña debe ser de 6 caracteres");
            contraseña.requestFocus();
            return;
        }

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ocurrio un error", Toast.LENGTH_LONG);
                }
            }
        });
    }
}