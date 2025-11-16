package com.ecoembes.EcoembesServer.dto;

public class PlantaReciclajeDTO {

    private long id;
    private String nombre;
    private double capacidadTon;

    // Constructor without parameters
    public PlantaReciclajeDTO() { }

    // Constructor with parameters
    public PlantaReciclajeDTO(long id, String nombre, double capacidadTon) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadTon = capacidadTon;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCapacidadTon() {
        return capacidadTon;
    }

    public void setCapacidadTon(double capacidadTon) {
        this.capacidadTon = capacidadTon;
    }
}
