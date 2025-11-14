package com.ecoembes.EcoembesServer.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Token {
	private String token;//El token es inmutable una vez creado y debe ser generado por el sistema
	private LocalDateTime fechaCreacion;
	private long idUsuario;
	
	public Token() {
	}
	
	public Token(long idUsuario) {
		this.token = java.util.UUID.randomUUID().toString(); // Genera un token único (Copilot)
		this.fechaCreacion = LocalDateTime.now(); 
		this.idUsuario = idUsuario;
	}
	
	public String getToken() { 
		return token;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	// hashCode and equals
	@Override
	public int hashCode() {
		return Objects.hash(token);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		return Objects.equals(token, other.token);
	}
}