package com.example.demo;

public class Estadisticas {

    private int estadistica_id;
    private int torneo_id;
    private  String nombre;
    private boolean calle; //drivin
    private boolean oob; //driving
    private boolean gir; // aproach
    private int putts; //putting



    public boolean isCalle() {
        return calle;
    }

    public void setCalle(boolean calle) {
        this.calle = calle;
    }

    public boolean isOob() {
        return oob;
    }

    public void setOob(boolean oob) {
        this.oob = oob;
    }

    public boolean isGir() {
        return gir;
    }

    public void setGir(boolean gir) {
        this.gir = gir;
    }

    public int getEstadistica_id() {
        return estadistica_id;
    }

    public void setEstadistica_id(int estadistica_id) {
        this.estadistica_id = estadistica_id;
    }

    public int getTorneo_id() {
        return torneo_id;
    }

    public void setTorneo_id(int torneo_id) {
        this.torneo_id = torneo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPutts() {
        return putts;
    }

    public void setPutts(int putts) {
        this.putts = putts;
    }
}
