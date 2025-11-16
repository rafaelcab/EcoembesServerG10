package com.ecoembes.EcoembesServer.dto;

import com.ecoembes.EcoembesServer.entity.NivelLlenado;

public class ContenedorDTO {

	private long id;
	private String ubicacion;
	private NivelLlenado nivelLlenado;
	private int capacidad;
	
	// Constructor without parameters
	public ContenedorDTO() { }
	
	// Constructor with parameters
	public ContenedorDTO(long id, String ubicacion,
	                     NivelLlenado nivelLlenado, int capacidad) {
	    this.id = id;
	    this.ubicacion = ubicacion;
	    this.nivelLlenado = nivelLlenado;
	    this.capacidad = capacidad;
	}
	
	// Getters and setters
	public long getId() {
	    return id;
	}
	
	public String getUbicacion() {
	    return ubicacion;
	}
	
	public void setUbicacion(String ubicacion) {
	    this.ubicacion = ubicacion;
	}
	
	public NivelLlenado getNivelLlenado() {
	    return nivelLlenado;
	}
	
	public void setNivelLlenado(NivelLlenado nivelLlenado) {
	    this.nivelLlenado = nivelLlenado;
	}
	
	public int getCapacidad() {
	    return capacidad;
	}
	
	public void setCapacidad(int capacidad) {
	    this.capacidad = capacidad;
	}
	}
