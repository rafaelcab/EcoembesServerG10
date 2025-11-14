package com.ecoembes.EcoembesServer.entity;

import java.util.Objects;

public class Usuario {
	private long id;
	private String email;
	private String contrasenya;
	private String nombre;
	
	public Usuario() {
		this.id = System.currentTimeMillis();
	}
	
	public Usuario( String email, String contrasenya, String nombre) {
		this.id = System.currentTimeMillis();
		this.email = email;
		this.contrasenya = contrasenya;
		this.nombre = nombre;
	}
	
	// Getters and setters
	public long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContrasenya() {
		return contrasenya;
	}
	
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		Usuario other = (Usuario) obj;
		return id == other.id;
	}
}