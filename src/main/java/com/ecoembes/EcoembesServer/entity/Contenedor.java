package com.ecoembes.EcoembesServer.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Contenedor {
	private long id;
	private String ubicacion;
	private double capacidad;
	private Map<LocalDate, LecturaContenedor> lecturasContenedor = new HashMap<>();
	
	// Constructor without parameters
	public Contenedor() {
		this.id = System.currentTimeMillis();
		this.lecturasContenedor = new HashMap<>();
	}
	
	// Constructor with parameters
	public Contenedor(String ubicacion, double capacidad) {
		this.id = System.currentTimeMillis(); // Simple unique ID generation
		this.ubicacion = ubicacion;
		this.capacidad = capacidad;
		this.lecturasContenedor = new HashMap<>();
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
	
	public double getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}
	
	public Map<LocalDate, LecturaContenedor> getLecturasContenedor() {
		return lecturasContenedor;
	}
	
	public void setLecturasContenedor(Map<LocalDate, LecturaContenedor> lecturasContenedor) {
		this.lecturasContenedor = lecturasContenedor;
	}
	
	public void registrarLecturaContenedor(LocalDate fecha, LecturaContenedor lecturaContenedor) {
		if(lecturaContenedor != null && !this.lecturasContenedor.containsKey(fecha)) {
			this.lecturasContenedor.put(fecha, lecturaContenedor);
		}
	}
	
	// hashCode and equals
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
		Contenedor other = (Contenedor) obj;
		return id == other.id;
	}
}