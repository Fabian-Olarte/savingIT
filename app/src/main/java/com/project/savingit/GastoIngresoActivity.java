package com.project.savingit;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;

public class GastoIngresoActivity extends AppCompatActivity {

    TextView textViewTitulo, textViewNombreIG, textViewValorIG, textViewCuentaIG;
    Button buttonAgregar;
    EditText inputNombreIG, inputValorIG, inputCuentaIG;
    JSONArray registros = new JSONArray();
    String jsonRegistros = null;


    ActivityResultLauncher<String> WritePermision= registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {

        }
    });

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

        WritePermision.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputNombreIG.getText().toString().isEmpty()){
                    if(!inputValorIG.getText().toString().isEmpty()){
                        if(!inputCuentaIG.getText().toString().isEmpty()){
                            Date actual = new Date(System.currentTimeMillis());
                            String fechaActual = actual.toString();
                            Registro registro = new Registro(inputNombreIG.getText().toString(), inputCuentaIG.getText().toString(), Long.parseLong(inputValorIG.getText().toString()), opcion, fechaActual);
                            writeJSONObject(registro);
                            finish();
                        }
                    }
                }

            }
        });
    }

    public void writeJSONObject(Registro registro){


        jsonRegistros = loadJSONFromAsset();
        Writer output = null;
        String filename = "registros.json";

        try {
            if(jsonRegistros!=null) {
                registros = new JSONArray(jsonRegistros);
            }

            registros.put(registro.toJSON());

            File file = new File(getBaseContext().getExternalFilesDir(null), filename);
            output = new BufferedWriter(new FileWriter(file));

            output.write(registros.toString());
            output.close();
            Toast.makeText(this, "Registro guardado correctamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String loadJSONFromAsset() {
        String json = null;
        FileReader fr = null;
        try {
            File file = new File(getExternalFilesDir(null), "registros.json");
            StringBuilder stringBuilder = new StringBuilder();

            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while(line != null){
                stringBuilder.append(line).append('\n');
                line = br.readLine();
            }

            json = stringBuilder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}