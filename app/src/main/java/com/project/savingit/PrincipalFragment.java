package com.project.savingit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalFragment extends Fragment {

    ImageButton agregarGasto, agregarIngreso;
    LinearLayout layoutIngresos, layoutGastos;
    View root;
    TextView balanceText, ingresoText, gastoText;

    String jsonRegistros = null;
    JSONArray jsonArray = null;
    long ingresos = 0;
    long gastos = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrincipalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrincipalFragment newInstance(String param1, String param2) {
        PrincipalFragment fragment = new PrincipalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_principal, container, false);

        agregarIngreso = root.findViewById(R.id.agregarIngreso);
        agregarGasto = root.findViewById(R.id.agregarGasto);
        layoutGastos = root.findViewById(R.id.layoutGastos);
        layoutIngresos = root.findViewById(R.id.layoutIngresos);
        gastoText = root.findViewById(R.id.valorGastado);
        ingresoText = root.findViewById(R.id.ingresotext);
        balanceText = root.findViewById(R.id.balancetext);



        try{
            jsonRegistros = loadJSONFromAsset();
            jsonArray = new JSONArray(jsonRegistros);

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Registro reg = new Registro(jsonObj.getString("nombre"), jsonObj.getString("modopago"), jsonObj.getLong("valor"), jsonObj.getInt("tipo"), jsonObj.getString("fecha"));
                if(reg.getTipo() == 1){
                    ingresos += reg.getValor();
                }else{
                    gastos += reg.getValor();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        gastos = 0;
        ingresos = 0;

        updateNumbers();

        agregarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GastoIngresoActivity.class);
                intent.putExtra("opcion", 1);
                startActivity(intent);
            }
        });

        agregarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GastoIngresoActivity.class);
                intent.putExtra("opcion", 2);
                startActivity(intent);
            }
        });

        layoutIngresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListaResgistrosActivity.class);
                intent.putExtra("opcion", 1);
                startActivity(intent);
            }
        });

        layoutGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListaResgistrosActivity.class);
                intent.putExtra("opcion", 2);
                startActivity(intent);
            }
        });



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        gastos = 0;
        ingresos = 0;
        updateNumbers();
        ingresoText.setText("$ "+ String.valueOf(ingresos));
        gastoText.setText("$ "+String.valueOf(gastos));
        balanceText.setText("$ "+String.valueOf(ingresos-gastos));
    }

    public void updateNumbers(){
        try{
            jsonRegistros = loadJSONFromAsset();
            jsonArray = new JSONArray(jsonRegistros);

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Registro reg = new Registro(jsonObj.getString("nombre"), jsonObj.getString("modopago"), jsonObj.getLong("valor"), jsonObj.getInt("tipo"), jsonObj.getString("fecha"));
                if(reg.getTipo() == 1){
                    ingresos += reg.getValor();
                }else{
                    gastos += reg.getValor();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        FileReader fr = null;
        try {
            File file = new File(getActivity().getExternalFilesDir(null), "registros.json");
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