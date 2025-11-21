
package com.ecoembes.EcoembesServer.entity;

import java.util.ArrayList;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class PlantaReciclaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private double capacidadTon;

    @OneToMany(mappedBy = "plantaReciclaje", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CapacidadDiaria> capacidades = new ArrayList<>();

	
	// Constructor without parameters
	public PlantaReciclaje() {
		this.id = System.currentTimeMillis();
		this.capacidades = new ArrayList<>();
	}
	
	// Constructor with parameters
	public PlantaReciclaje(String nombre, double capacidadTon) {
		this.id = System.currentTimeMillis(); 
		this.nombre = nombre;
		this.capacidadTon = capacidadTon;
		this.capacidades = new ArrayList<>();
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
	
	public List<CapacidadDiaria> getCapacidades() {
		return capacidades;
	}
	
	public void setCapacidades(List<CapacidadDiaria> capacidades) {
		this.capacidades = capacidades;
	}
	
	public void agragarCapacidadDiaria(CapacidadDiaria capacidadDiaria) {
	    if (capacidadDiaria != null && !capacidades.contains(capacidadDiaria)) {
	        capacidades.add(capacidadDiaria);
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
		PlantaReciclaje other = (PlantaReciclaje) obj;
		return id == other.id;
	}
}