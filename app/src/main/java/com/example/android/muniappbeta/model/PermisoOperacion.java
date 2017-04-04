package com.example.android.muniappbeta.model;

/**
 * Created by peral on 1/04/2017.
 */

public class PermisoOperacion {
    private String numplaca;
    private String numpadron;
    private int numtarjeta;
    private String per_dni;
    private String per_apellidos;
    private String per_nombres;
    private String razonsocial;
    private String tiposervicioempresa;
    private String clasevh;
    private String nummotor;
    private String numserie;
    private String color;
   // private Date fechinitarj;
   // private Date fechcaducatarj;
    private String fechaEmision;
    private String fechaCaducidad;
    private String fechaActual;

    public String getNumplaca() {
        return numplaca;
    }

    public void setNumplaca(String numplaca) {
        this.numplaca = numplaca;
    }

    public String getNumpadron() {
        return numpadron;
    }

    public void setNumpadron(String numpadron) {
        this.numpadron = numpadron;
    }

    public int getNumtarjeta() {
        return numtarjeta;
    }

    public void setNumtarjeta(int numtarjeta) {
        this.numtarjeta = numtarjeta;
    }

    public String getPer_dni() {
        return per_dni;
    }

    public void setPer_dni(String per_dni) {
        this.per_dni = per_dni;
    }

    public String getPer_apellidos() {
        return per_apellidos;
    }

    public void setPer_apellidos(String per_apellidos) {
        this.per_apellidos = per_apellidos;
    }

    public String getPer_nombres() {
        return per_nombres;
    }

    public void setPer_nombres(String per_nombres) {
        this.per_nombres = per_nombres;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getTiposervicioempresa() {
        return tiposervicioempresa;
    }

    public void setTiposervicioempresa(String tiposervicioempresa) {
        this.tiposervicioempresa = tiposervicioempresa;
    }

    public String getClasevh() {
        return clasevh;
    }

    public void setClasevh(String clasevh) {
        this.clasevh = clasevh;
    }

    public String getNummotor() {
        return nummotor;
    }

    public void setNummotor(String nummotor) {
        this.nummotor = nummotor;
    }

    public String getNumserie() {
        return numserie;
    }

    public void setNumserie(String numserie) {
        this.numserie = numserie;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }
}
