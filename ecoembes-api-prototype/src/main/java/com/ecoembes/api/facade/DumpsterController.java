package com.ecoembes.api.facade;

import com.ecoembes.api.dto.*;
import com.ecoembes.api.service.*;
import com.ecoembes.api.domain.*;
import com.ecoembes.api.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DumpsterController {
    
    private final AuthService authService;
    private final DumpsterService dumpsterService;
    private final RecyclingPlantService plantService;
    private final RecyclingPlantRepository plantRepo;
    private final AssignmentRepository assignmentRepo;
    private final EmployeeRepository employeeRepo;
    private final DumpsterRepository dumpsterRepo;

    public DumpsterController(AuthService a, DumpsterService d, RecyclingPlantService ps, 
                              RecyclingPlantRepository pr, AssignmentRepository ar, 
                              EmployeeRepository er, DumpsterRepository dr) {
        this.authService = a;
        this.dumpsterService = d;
        this.plantService = ps;
        this.plantRepo = pr;
        this.assignmentRepo = ar;
        this.employeeRepo = er;
        this.dumpsterRepo = dr;
    }

    private void validateToken(String auth) {
        if (auth == null || auth.isBlank()) throw new RuntimeException("Token requerido");
        authService.validateToken(auth.replace("Bearer ", "").trim());
    }

    // --- DUMPSTERS ---
    @Operation(summary = "List all dumpsters")
    @GetMapping("/dumpsters")
    public ResponseEntity<List<DumpsterDTO>> listDumpsters(@RequestHeader("Authorization") String auth) {
        validateToken(auth);
        return ResponseEntity.ok(dumpsterService.list());
    }

    @GetMapping("/dumpsters/{id}")
    public ResponseEntity<DumpsterDTO> getDumpster(@RequestHeader("Authorization") String auth, @PathVariable("id") String id) {
        validateToken(auth);
        return ResponseEntity.ok(dumpsterService.get(id));
    }

    @PostMapping("/dumpsters")
    public ResponseEntity<DumpsterDTO> createDumpster(@RequestHeader("Authorization") String auth, @RequestBody DumpsterDTO dto) {
        validateToken(auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(dumpsterService.create(dto));
    }

    @PutMapping("/dumpsters/{id}")
    public ResponseEntity<DumpsterDTO> updateDumpster(@RequestHeader("Authorization") String auth, @PathVariable("id") String id, @RequestBody DumpsterDTO dto) {
        validateToken(auth);
        return ResponseEntity.ok(dumpsterService.update(id, dto));
    }

    @GetMapping("/dumpsters/status")
    public ResponseEntity<List<DumpsterDTO>> checkDumpsterStatus(@RequestHeader("Authorization") String auth, 
            @RequestParam("postalCode") String postalCode,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        validateToken(auth);
        return ResponseEntity.ok(dumpsterService.status(postalCode, date != null ? date : LocalDate.now()));
    }

    @GetMapping("/dumpsters/{id}/usage")
    public ResponseEntity<List<DumpsterUsageDTO>> getDumpsterUsage(@RequestHeader("Authorization") String auth, @PathVariable("id") String id) {
        validateToken(auth);
        return ResponseEntity.ok(dumpsterService.getUsageHistory(id));
    }

    @DeleteMapping("/dumpsters/{id}")
    public ResponseEntity<Void> deleteDumpster(@RequestHeader("Authorization") String auth, @PathVariable("id") String id) {
        validateToken(auth);
        dumpsterService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- PLANTS & TW2 ARCHITECTURE ---
    
    @Operation(summary = "List plants (DB Info)")
    @GetMapping("/plants")
    public ResponseEntity<List<RecyclingPlantDTO>> listPlants(@RequestHeader("Authorization") String auth) {
        validateToken(auth);
        List<RecyclingPlantDTO> list = plantRepo.findAll().stream()
            .map(p -> new RecyclingPlantDTO(p.getId(), p.getName(), p.getCapacity(), p.getAvailableCapacity(), p.getLocation()))
            .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get Real-Time Capacity from External Server (TW2)")
    @GetMapping("/plants/{id}/capacity")
    public ResponseEntity<Double> getPlantCapacity(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        validateToken(auth);
        // Esto llama a la Factory -> Gateway -> PlasSB/ContSocket
        return ResponseEntity.ok(plantService.getPlantCapacity(id));
    }

    // --- ASSIGNMENTS ---
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDTO>> listAssignments(@RequestHeader("Authorization") String auth) {
        validateToken(auth);
        List<AssignmentDTO> list = assignmentRepo.findAll().stream().map(this::toAssignmentDTO).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/assignments")
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestHeader("Authorization") String auth, @RequestBody AssignmentDTO dto) {
        validateToken(auth);
        
        Employee e = employeeRepo.findById(dto.employeeId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        RecyclingPlant p = plantRepo.findById(dto.recyclingPlantId())
                .orElseThrow(() -> new RuntimeException("Planta no encontrada"));
        Dumpster d = dumpsterRepo.findById(dto.dumpsterId())
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado"));

        Assignment a = new Assignment(null, dto.date(), e, p, d);
        assignmentRepo.save(a);
        return ResponseEntity.ok(toAssignmentDTO(a));
    }

    private AssignmentDTO toAssignmentDTO(Assignment a) {
        return new AssignmentDTO(a.getAssignmentID(), a.getEmployeeID().getId(), a.getDumpstersAssigned().getId(), a.getRecyclingPlant().getId(), a.getDate());
    }
}