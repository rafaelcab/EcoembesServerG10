package com.ecoembes.EcoembesServer.dto;

import java.time.LocalDate;

import com.ecoembes.EcoembesServer.entity.NivelLlenado;

public class LecturaDTO {

    private LocalDate fecha;
    private int numeroEstimadoEnvases;
    private NivelLlenado nivelLlenado;

    public LecturaDTO() { }

    public LecturaDTO(LocalDate fecha, int numeroEstimadoEnvases, NivelLlenado nivelLlenado) {
        this.fecha = fecha;
        this.numeroEstimadoEnvases = numeroEstimadoEnvases;
        this.nivelLlenado = nivelLlenado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
