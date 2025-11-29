package com.ecoembes.EcoembesServer.entity;

import java.time.LocalDate;
import java.util.Objects;

public class CapacidadDiaria {
	private Long id;
	private LocalDate fecha;
	private double capacidadTotal;
	private double capacidadOcupada;
	
	// Constructor without parameters
	public CapacidadDiaria() {
	}
	
	public CapacidadDiaria(LocalDate fecha, double capacidadTotal, 
	                       double capacidadOcupada) {
		this.id = System.currentTimeMillis(); // Simple unique ID generation
		this.fecha = fecha;
		this.capacidadTotal = capacidadTotal;
		this.capacidadOcupada = capacidadOcupada;
	}
	
	public long getId() {
		return id;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public double getCapacidadTotal() {
		return capacidadTotal;
	}
	
	public void setCapacidadTotal(double capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}
	
	public double getCapacidadOcupada() {
		return capacidadOcupada;
	}
	
	public void setCapacidadOcupada(double capacidadOcupada) {
		this.capacidadOcupada = capacidadOcupada;
	}
	

	public double getCapacidadDisponible() {
		return capacidadTotal - capacidadOcupada;
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
		CapacidadDiaria other = (CapacidadDiaria) obj;
		return id == other.id;
	}
}
