package com.project.savingit;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class Registro {

    private String nombre;
    private String modoPago;
    private long valor;
    private int tipo;
    private String fecha;

    public Registro(String nombre, String modoPago, long valor, int tipo, String fecha) {
        this.nombre = nombre;
        this.modoPago = modoPago;
        this.valor = valor;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModoPago() {
        return modoPago;
    }

    public void setModoPago(String modoPago) {
        this.modoPago = modoPago;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        try{
            obj.put("nombre", getNombre());
            obj.put("modopago", getModoPago());
            obj.put("valor", getValor());
            obj.put("tipo", getTipo());
            obj.put("fecha", getFecha());
        }catch(JSONException e){
            e.printStackTrace();
        }
        return obj;
    }
}
