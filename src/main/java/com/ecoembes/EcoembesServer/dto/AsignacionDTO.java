package com.ecoembes.EcoembesServer.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ecoembes.EcoembesServer.entity.Usuario;

public class AsignacionDTO {

	private long id;
	private List<ContenedorDTO> contenedores = new ArrayList<>();
	private PlantaReciclajeDTO planta;
	private LocalDate fecha;
	
	private Usuario asignadorPlanta;// Empleado que asigna los contenedores a la planta en cada jornada.
	private double totalCapacidad; // Total de la capacidad que va a tener una planta en una jornada (en toneladas).

	
	// Constructor without parameters
	public AsignacionDTO() { }
	
	// Constructor with parameters
	public AsignacionDTO(long id,
						 List<ContenedorDTO> contenedores,
						 PlantaReciclajeDTO planta,
						 LocalDate fecha) {
	    this.id = id;
	    this.contenedores = contenedores;
	    this.planta = planta;
	    this.fecha = fecha;
	}
	
	// Getters and setters
	public long getId() {
	    return id;
	}
	
	public List<ContenedorDTO> getContenedores() {
	    return contenedores;
	}
	
	public void setContenedores(List<ContenedorDTO> contenedores) {
	    this.contenedores = contenedores;
	}
	
	public PlantaReciclajeDTO getPlanta() {
	    return planta;
	}
	
	public void setPlanta(PlantaReciclajeDTO planta) {
	    this.planta = planta;
	}
	
	public LocalDate getFecha() {
	    return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
	    this.fecha = fecha;
	}
}
