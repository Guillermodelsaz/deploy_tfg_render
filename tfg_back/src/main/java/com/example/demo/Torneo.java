package com.example.demo;

public class Torneo {
    private int torneoId;
    private String name;
    private String club;

    private String patrocinador;
    private String image;
    private boolean modalidad;

    public int getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(int torneoId) {
        this.torneoId = torneoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isModalidad() {
        return modalidad;
    }

    public void setModalidad(boolean modalidad) {
        this.modalidad = modalidad;
    }
}
