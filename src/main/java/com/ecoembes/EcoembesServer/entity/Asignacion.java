package com.ecoembes.EcoembesServer.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "planta_id", nullable = false)
    private PlantaReciclaje planta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
        name = "asignacion_contenedor",
        joinColumns = @JoinColumn(name = "asignacion_id"),
        inverseJoinColumns = @JoinColumn(name = "contenedor_id")
    )
    private List<Contenedor> contenedores = new ArrayList<>();

	
	// Constructor without parameters
	public Asignacion() {
		this.id = System.currentTimeMillis(); 
		this.contenedores = new ArrayList<>();
	}
	
	// Constructor with parameters
	public Asignacion(LocalDate fecha, PlantaReciclaje planta, Usuario usuario) {
		this.id = System.currentTimeMillis(); // Simple unique ID generation
		this.fecha = fecha;
		this.planta = planta;
		this.usuario = usuario;
		this.contenedores = new ArrayList<>();
	}
	
	// Getters and setters
	public long getId() {
		return id;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public List<Contenedor> getContenedores() {
		return contenedores;
	}
	
	public void setContenedores(List<Contenedor> contenedores) {
		this.contenedores = contenedores;
	}
	
	public PlantaReciclaje getPlanta() {
		return planta;
	}
	
	public void setPlanta(PlantaReciclaje planta) {
		this.planta = planta;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void agregarContenedor(Contenedor contenedor) {
		if (contenedor != null && !contenedores.contains(contenedor)) {
		this.contenedores.add(contenedor);
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
		Asignacion other = (Asignacion) obj;
		return id == other.id;
	}
}
