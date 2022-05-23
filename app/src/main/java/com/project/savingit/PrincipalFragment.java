package com.project.savingit;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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
    TextView balanceText, ingresoText, gastoText, textCate;
    ImageView imagenCat;

    String jsonRegistros = null;
    JSONArray jsonArray = null;
    long ingresos = 0;
    long gastos = 0;
    float promedioI = 0 , promedioG = 0, tasaI = 0, tasaG = 0;

    FirebaseAuth auth;

    Interpreter interpreter;

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
        textCate = root.findViewById(R.id.mensaje_categoria);
        imagenCat = root.findViewById(R.id.imagen_categoria);

        try {
            interpreter = new Interpreter(loadModelFile());
        }catch (Exception x){
            x.printStackTrace();
        }

        auth = FirebaseAuth.getInstance();


        updateNumbers();
        categoria();


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

    private void categoria(){
        float cate;
        cate = doInference(promedioI, promedioG, tasaI, tasaG);

        if (cate == 0){
            imagenCat.setImageDrawable(ContextCompat.getDrawable(this.getActivity(), R.drawable.calavera));
            textCate.setText("Si sigues asi, tendras problemas en tu futuro");
            textCate.setTextColor(Color.RED);
            Toast.makeText(this.getActivity(),"ingreso "+tasaI,Toast.LENGTH_LONG).show();
            Toast.makeText(this.getActivity(),"gasto "+tasaG,Toast.LENGTH_LONG).show();
        }
        else if (cate == 1){
            imagenCat.setImageDrawable(ContextCompat.getDrawable(this.getActivity(), R.drawable.normal));
            textCate.setText("No estas mal, pero cuida mejor tus finanzas!!");
            textCate.setTextColor(Color.YELLOW);
        }
        else{
            imagenCat.setImageDrawable(ContextCompat.getDrawable(this.getActivity(), R.drawable.dinero));
            textCate.setText("Felicitaciones, vas a ser rico en un futuro!!");
            textCate.setTextColor(Color.GREEN);
            Toast.makeText(this.getActivity(),"ingreso "+tasaI,Toast.LENGTH_LONG).show();
            Toast.makeText(this.getActivity(),"gasto "+tasaG,Toast.LENGTH_LONG).show();
        }

    }
    private MappedByteBuffer loadModelFile() throws  IOException{

        AssetFileDescriptor fileDescriptor = this.getActivity().getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float doInference(float promI, float promG, float tI, float tG){

        float[] inputVal = new float[4];
        inputVal[0] = promI;
        inputVal[1] = promG;
        inputVal[2] = tI;
        inputVal[3] = tG;

        float[][] outputVal = new float[1][3];

        interpreter.run(inputVal, outputVal);

        float res = -1;

        for(int i = 0; i<3; i++){
            if(outputVal[0][i] == 1){
                res = i;
            }
        }

        return res;
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
        categoria();

    }

    public void updateNumbers(){
        try{
            jsonRegistros = loadJSONFromAsset();
            jsonArray = new JSONArray(jsonRegistros);
            float auxI = 0, auxg = 0;
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Registro reg = new Registro(jsonObj.getString("nombre"), jsonObj.getString("modopago"), jsonObj.getLong("valor"), jsonObj.getInt("tipo"), jsonObj.getString("fecha"));
                if(reg.getTipo() == 1){
                    if (i != 0 ){
                        if (auxI!= 0)
                            tasaI +=((reg.getValor()-auxI)/auxI);
                        Log.e("ingreso ", Float.toString(tasaI));
                    }
                    ingresos += reg.getValor();
                    auxI = reg.getValor();
                }else{

                    if (i != 0 ){
                        if (auxg!= 0)
                            tasaG +=((reg.getValor()-auxg)/auxg);
                        Log.e("gasto ", Float.toString(tasaG));
                    }
                    gastos += reg.getValor();
                    auxg = reg.getValor();
                }
            }

            promedioI = ingresos/jsonArray.length();
            promedioG = gastos/ jsonArray.length();
            tasaG = tasaG/jsonArray.length();
            tasaI = tasaI/jsonArray.length();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        FileReader fr = null;
        try {
            File file = new File(getActivity().getExternalFilesDir(auth.getCurrentUser().getUid()), "registros.json");
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