package com.project.savingit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GastoIngresoActivity extends AppCompatActivity {

    TextView textViewTitulo, textViewNombreIG, textViewValorIG, textViewCuentaIG;
    Button buttonAgregar;
    EditText inputNombreIG, inputValorIG, inputCuentaIG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasto_ingreso);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewNombreIG = findViewById(R.id.textViewNombreIG);
        textViewValorIG = findViewById(R.id.textViewValorIG);
        textViewCuentaIG = findViewById(R.id.textViewCuentaIG);
        inputNombreIG = findViewById(R.id.inputNombreIG);
        inputValorIG = findViewById(R.id.inputValorIG);
        inputCuentaIG = findViewById(R.id.inputCuentaIG);
        buttonAgregar = findViewById(R.id.buttonAgregar);


        int opcion = getIntent().getIntExtra("opcion", 0);

        if(opcion == 1){
            textViewTitulo.setText("Agregar Ingreso");
            textViewTitulo.setTextColor(Color.parseColor("#105E1D"));
            textViewNombreIG.setText("Nombre del ingreso");
            textViewValorIG.setText("Valor del ingreso");
            textViewCuentaIG.setText("Cuenta usada");
            buttonAgregar.setText("Agregar ingreso");
            buttonAgregar.setBackgroundColor(Color.parseColor("#105E1D"));






        }

        else if(opcion == 2){
            textViewTitulo.setText("Agregar Gasto");
            textViewTitulo.setTextColor(Color.parseColor("#952828"));
            textViewNombreIG.setText("Nombre del gasto");
            textViewValorIG.setText("Valor del gasto");
            textViewCuentaIG.setText("Cuenta usada");
            buttonAgregar.setText("Agregar gasto");
            buttonAgregar.setBackgroundColor(Color.parseColor("#952828"));





        }

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputNombreIG.getText().toString().isEmpty()){
                    if(!inputValorIG.getText().toString().isEmpty()){
                        if(!inputCuentaIG.getText().toString().isEmpty()){
                            //firebase



                        }
                    }
                }

            }
        });
    }
}