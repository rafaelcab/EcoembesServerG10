package com.ecoembes.api.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "recycling_plant_id")
    private RecyclingPlant recyclingPlant;

    @ManyToOne
    @JoinColumn(name = "dumpster_id")
    private Dumpster dumpsterAssigned;

    public Assignment() {}

    public Assignment(Long id, LocalDate date, Employee employee, RecyclingPlant p, Dumpster d) {
        this.id = id;
        this.date = date;
        this.employee = employee;
        this.recyclingPlant = p;
        this.dumpsterAssigned = d;
    }

    // Getters y Setters
    public Long getAssignmentID() { return id; }
    public void setAssignmentID(Long id) { this.id = id; }
    
    // Nota: Es importante usar getId para JPA, aunque tu DTO use getAssignmentID
    public Long getId() { return id; } 

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Employee getEmployeeID() { return employee; }
    public void setEmployeeID(Employee employee) { this.employee = employee; }

    public RecyclingPlant getRecyclingPlant() { return recyclingPlant; }
    public void setRecyclingPlant(RecyclingPlant recyclingPlant) { this.recyclingPlant = recyclingPlant; }

    public Dumpster getDumpstersAssigned() { return dumpsterAssigned; }
    public void setDumpstersAssigned(Dumpster dumpsterAssigned) { this.dumpsterAssigned = dumpsterAssigned; }
}