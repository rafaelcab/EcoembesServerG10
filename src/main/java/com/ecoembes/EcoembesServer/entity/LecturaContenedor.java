package com.ecoembes.EcoembesServer.entity;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
public class LecturaContenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int numeroEstimadoEnvases;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelLlenado nivelLlenado;

    @ManyToOne
    @JoinColumn(name = "contenedor_id", nullable = false)
    private Contenedor contenedor;

    @Column(nullable = false)
    private LocalDate fecha;

	
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