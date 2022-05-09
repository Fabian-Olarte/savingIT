package com.project.savingit;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.List;


public class RegistroAdapter extends ArrayAdapter<Registro> {

    private List<Registro> mListRegistros;
    private Context mContext;
    private int resourceLayout;

    public RegistroAdapter(@NonNull Context context, int resource, List<Registro> objects) {
        super(context, resource, objects);
        this.mListRegistros = objects;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) view = LayoutInflater.from(mContext).inflate(R.layout.registro_row, null);

        Registro registro = mListRegistros.get(position);

        TextView nombrePago = view.findViewById(R.id.nombrePago);
        nombrePago.setText(registro.getNombre());

        TextView nombreCuenta = view.findViewById(R.id.nombreCuenta);
        nombreCuenta.setText(registro.getModoPago());

        TextView valorPago = view.findViewById(R.id.valorPago);
        valorPago.setText(String.valueOf(registro.getValor()));

        int tipo = registro.getTipo();

        ImageView iconoRegistro = view.findViewById(R.id.iconoRegistro);
        if(tipo == 1){
            iconoRegistro.setImageResource(R.drawable.ingreso_icono);
        }else{
            iconoRegistro.setImageResource(R.drawable.gasto_icono);
        }
        TextView fechaPago = view.findViewById(R.id.fechaPago);
        fechaPago.setText(registro.getFecha());

        return view;
    }
}