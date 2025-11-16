package com.ecoembes.EcoembesServer.dto;

public class CredencialesDTO {

    private String email;
    private String contrasenya;

    // Constructor without parameters
    public CredencialesDTO() { }

    // Constructor with parameters
    public CredencialesDTO(String email, String contrasenya) {
        this.email = email;
        this.contrasenya = contrasenya;
    }

    // Getters and setters
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
}
