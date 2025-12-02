package com.ecoembes.api;

import com.ecoembes.api.domain.*;
import com.ecoembes.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RecyclingPlantRepository plantRepo;
    private final DumpsterRepository dumpsterRepo;
    private final EmployeeRepository employeeRepo;
    private final AssignmentRepository assignmentRepo;

    // Inyección de dependencias
    public DataInitializer(RecyclingPlantRepository plantRepo, 
                           DumpsterRepository dumpsterRepo,
                           EmployeeRepository employeeRepo,
                           AssignmentRepository assignmentRepo) {
        this.plantRepo = plantRepo;
        this.dumpsterRepo = dumpsterRepo;
        this.employeeRepo = employeeRepo;
        this.assignmentRepo = assignmentRepo;
    }

    @Override
    public void run(String... args) {
        // 1. Crear Empleados
        Employee e1 = new Employee(null, "Juan Pérez", "juan@ecoembes.com", "1234");
        e1 = employeeRepo.save(e1); // Guardamos y recuperamos con ID generado

        // 2. Crear Plantas (TW2: Una HTTP y una Socket)
        // Planta 1: PlasSB (HTTP en puerto 8081)
        RecyclingPlant p1 = new RecyclingPlant("Planta Norte (HTTP)", 1000.0, "Bilbao", "PLASSB");
        p1 = plantRepo.save(p1);

        // Planta 2: ContSockets (TCP en puerto 9090)
        RecyclingPlant p2 = new RecyclingPlant("Planta Centro (Socket)", 2000.0, "Madrid", "CONTSOCKET");
        p2 = plantRepo.save(p2);

        // 3. Crear Contenedores
        Dumpster d1 = new Dumpster("D001", "Madrid Centro", 1, FillLevel.GREEN);
        dumpsterRepo.save(d1);

        Dumpster d2 = new Dumpster("D002", "Bilbao Casco Viejo", 2, FillLevel.RED);
        dumpsterRepo.save(d2);

        // 4. Crear Asignación
        Assignment a1 = new Assignment(null, LocalDate.now(), e1, p2, d1);
        assignmentRepo.save(a1);

        System.out.println("✅ Datos cargados en Base de Datos H2 correctamente.");
    }
}