package com.ecoembes.api.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dumpsters")
public class Dumpster {
    @Id
    private String id;
    private String location;
    private int numContainer;
    
    @Enumerated(EnumType.STRING)
    private FillLevel fillLevel;
    private String status;
    private LocalDateTime lastUpdate;

    public Dumpster() {}

    public Dumpster(String id, String location, int numContainer, FillLevel fillLevel) {
        this.id = id;
        this.location = location;
        this.numContainer = numContainer;
        this.fillLevel = fillLevel;
        this.status = fillLevel.name();
        this.lastUpdate = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getNumContainer() { return numContainer; }
    public void setNumContainer(int numContainer) { this.numContainer = numContainer; }
    public FillLevel getFillLevel() { return fillLevel; }
    public void setFillLevel(FillLevel fillLevel) { this.fillLevel = fillLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }
}