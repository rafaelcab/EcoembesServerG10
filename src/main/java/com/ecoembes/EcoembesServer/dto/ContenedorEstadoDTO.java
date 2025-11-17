package com.ecoembes.EcoembesServer.dto;

import com.ecoembes.EcoembesServer.entity.NivelLlenado;

public class ContenedorEstadoDTO {


    private long id;
    private String ubicacion;
    private double capacidad;

    private int numeroEstimadoEnvases;
    private NivelLlenado nivelLlenado;

    // Constructor vacío
    public ContenedorEstadoDTO() {}

    // Constructor completo
    public ContenedorEstadoDTO(long id, String ubicacion, double capacidad,
                               int numeroEstimadoEnvases, NivelLlenado nivelLlenado) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.numeroEstimadoEnvases = numeroEstimadoEnvases;
        this.nivelLlenado = nivelLlenado;
    }

    // ----------- Getters & Setters -----------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public int getNumeroEstimadoEnvases() {
        return numeroEstimadoEnvases;
    }

    public void setNumeroEstimadoEnvases(int numeroEstimadoEnvases) {
        this.numeroEstimadoEnvases = numeroEstimadoEnvases;
    }

    public NivelLlenado getNivelLlenado() {
        return nivelLlenado;
    }

    public void setNivelLlenado(NivelLlenado nivelLlenado) {
        this.nivelLlenado = nivelLlenado;
    }
}
