package com.project.savingit;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ListaResgistrosActivity extends AppCompatActivity {

    ConstraintLayout constraintRegistros;
    ListView listaRegistros;
    TextView titulo;

    ActivityResultLauncher<String> WritePermision= registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if(result) loadJSONFromAsset();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_resgistros);

        titulo = findViewById(R.id.tituloLista);
        listaRegistros = findViewById(R.id.listaRegistros);
        constraintRegistros = findViewById(R.id.constraintRegistros);

        int opcion = getIntent().getIntExtra("opcion", 0);

        ArrayList<Registro> registros = new ArrayList<>();

        if(opcion == 1){
            constraintRegistros.setBackground(getDrawable(R.drawable.recursos_gradiente_verde));
            titulo.setText("Últimos ingresos");

        }else if(opcion == 2){
            constraintRegistros.setBackground(getDrawable(R.drawable.recursos_gradiente_rojo));
            titulo.setText("Últimos gastos");
        }

        String jsonRegistros = null;

        JSONArray jsonArray = null;

        Date actual = new Date(System.currentTimeMillis());
        String fechaActual = actual.toString();

        try{
            jsonRegistros = loadJSONFromAsset();
            jsonArray = new JSONArray(jsonRegistros);

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Registro reg = new Registro(jsonObj.getString("nombre"), jsonObj.getString("modopago"), jsonObj.getLong("valor"), jsonObj.getInt("tipo"), jsonObj.getString("fecha"));
                if(reg.getTipo()==opcion){
                    registros.add(reg);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        RegistroAdapter registroAdapter = new RegistroAdapter(this, R.layout.registro_row, registros);
        listaRegistros.setAdapter(registroAdapter);

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