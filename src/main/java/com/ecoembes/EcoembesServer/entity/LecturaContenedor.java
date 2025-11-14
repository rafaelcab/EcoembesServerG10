package com.ecoembes.EcoembesServer.entity;

import java.util.Objects;


public class LecturaContenedor {
	private long id;
	private int numeroEstimadoEmbalses;
	private NivelLlenado nivelLlenado;
	
	public LecturaContenedor() {
	}
	
	public LecturaContenedor(int numeroEstimadoEmbalses, NivelLlenado nivelLlenado) {
		this.id = System.currentTimeMillis(); 
		this.numeroEstimadoEmbalses = numeroEstimadoEmbalses;
		this.nivelLlenado = nivelLlenado;
	}
	
	public long getId() {
		return id;
	}
	
	public int getNumeroEstimadoEmbalses() {
		return numeroEstimadoEmbalses;
	}
	
	public void setNumeroEstimadoEmbalses(int numeroEstimadoEmbalses) {
		this.numeroEstimadoEmbalses = numeroEstimadoEmbalses;
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