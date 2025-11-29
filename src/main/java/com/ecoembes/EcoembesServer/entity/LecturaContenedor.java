package com.ecoembes.EcoembesServer.entity;

import java.util.Objects;


public class LecturaContenedor {
	private long id;
	private int numeroEstimadoEnvases;
	private NivelLlenado nivelLlenado;
	
	public LecturaContenedor() {
	}
	
	public LecturaContenedor(int numeroEstimadoEnvases, NivelLlenado nivelLlenado) {
		this.id = System.currentTimeMillis(); 
		this.numeroEstimadoEnvases= numeroEstimadoEnvases;
		this.nivelLlenado = nivelLlenado;
	}
	
	public long getId() {
		return id;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LecturaContenedor other = (LecturaContenedor) obj;
		return id == other.id;
	}
}