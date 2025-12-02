package com.ecoembes.api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "recycling_plants")
public class RecyclingPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double capacity;
    private Double availableCapacity;
    private String location;
    
    // "PLASSB" (HTTP) o "CONTSOCKET" (Socket)
    private String implementationType;

    public RecyclingPlant() {}

    public RecyclingPlant(String name, Double capacity, String location, String implementationType) {
        this.name = name;
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.location = location;
        this.implementationType = implementationType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getCapacity() { return capacity; }
    public void setCapacity(Double capacity) { this.capacity = capacity; }
    public Double getAvailableCapacity() { return availableCapacity; }
    public void setAvailableCapacity(Double availableCapacity) { this.availableCapacity = availableCapacity; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getImplementationType() { return implementationType; }
    public void setImplementationType(String implementationType) { this.implementationType = implementationType; }
}