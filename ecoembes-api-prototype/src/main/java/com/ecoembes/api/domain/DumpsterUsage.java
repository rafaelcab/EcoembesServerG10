package com.ecoembes.api.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dumpster_usages")
public class DumpsterUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dumpster_id")
    private Dumpster dumpster;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private FillLevel fillLevelBefore;

    @Enumerated(EnumType.STRING)
    private FillLevel fillLevelAfter;

    private String action; // "EMPTIED", "FILLED", "UPDATED"

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public DumpsterUsage() {}

    public DumpsterUsage(Long id, Dumpster dumpster, LocalDateTime timestamp, FillLevel fillLevelBefore,
            FillLevel fillLevelAfter, String action, Employee employee) {
        this.id = id;
        this.dumpster = dumpster;
        this.timestamp = timestamp;
        this.fillLevelBefore = fillLevelBefore;
        this.fillLevelAfter = fillLevelAfter;
        this.action = action;
        this.employee = employee;
    }

    // Getters y Setters estándar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Dumpster getDumpster() { return dumpster; }
    public void setDumpster(Dumpster dumpster) { this.dumpster = dumpster; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public FillLevel getFillLevelBefore() { return fillLevelBefore; }
    public void setFillLevelBefore(FillLevel fillLevelBefore) { this.fillLevelBefore = fillLevelBefore; }
    public FillLevel getFillLevelAfter() { return fillLevelAfter; }
    public void setFillLevelAfter(FillLevel fillLevelAfter) { this.fillLevelAfter = fillLevelAfter; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Employee getEmployeeId() { return employee; }
    public void setEmployeeId(Employee employee) { this.employee = employee; }
}