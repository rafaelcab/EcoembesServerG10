package com.ecoembes.api.service;

import com.ecoembes.api.domain.*;
import com.ecoembes.api.dto.*;
import com.ecoembes.api.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DumpsterService {

    private final DumpsterRepository dumpsterRepo;
    private final DumpsterUsageRepository usageRepo;

    public DumpsterService(DumpsterRepository dumpsterRepo, DumpsterUsageRepository usageRepo) {
        this.dumpsterRepo = dumpsterRepo;
        this.usageRepo = usageRepo;
    }

    public List<DumpsterDTO> list() {
        return dumpsterRepo.findAll().stream().map(this::toDTO).toList();
    }

    public DumpsterDTO get(String id) {
        return dumpsterRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado: " + id));
    }

    public DumpsterDTO create(DumpsterDTO dto) {
        FillLevel level = FillLevel.valueOf(dto.fillLevel());
        Dumpster d = new Dumpster(dto.id(), dto.location(), dto.numContainer(), level);
        // Si no mandan status, ponemos el nombre del nivel
        d.setStatus(dto.status() != null ? dto.status() : level.name());
        d.setLastUpdate(LocalDateTime.now());
        
        dumpsterRepo.save(d);
        return toDTO(d);
    }

    public DumpsterDTO update(String id, DumpsterDTO dto) {
        Dumpster d = dumpsterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado"));

        FillLevel oldLevel = d.getFillLevel();
        FillLevel newLevel = FillLevel.valueOf(dto.fillLevel());

        // Actualizamos campos
        d.setLocation(dto.location());
        d.setNumContainer(dto.numContainer());
        d.setFillLevel(newLevel);
        d.setStatus(dto.status() != null ? dto.status() : newLevel.name());
        d.setLastUpdate(LocalDateTime.now());

        // Historial de uso si cambia el nivel
        if (oldLevel != newLevel) {
            String action = getAction(oldLevel, newLevel);
            // El empleado es null por simplicidad, en un caso real lo sacaríamos del contexto de seguridad
            DumpsterUsage usage = new DumpsterUsage(null, d, LocalDateTime.now(), oldLevel, newLevel, action, null);
            usageRepo.save(usage);
        }

        dumpsterRepo.save(d);
        return toDTO(d);
    }

    public void delete(String id) {
        dumpsterRepo.deleteById(id);
    }

    public List<DumpsterDTO> status(String postal, LocalDate date) {
        // Usamos stream filter aquí por simplicidad, idealmente sería una query JPA personalizada
        return dumpsterRepo.findAll().stream()
                .filter(d -> d.getLocation() != null && d.getLocation().contains(postal))
                .map(this::toDTO)
                .toList();
    }

    public List<DumpsterUsageDTO> getUsageHistory(String dumpsterId) {
        return usageRepo.findByDumpsterId(dumpsterId).stream()
                .map(this::toUsageDTO)
                .toList();
    }

    private String getAction(FillLevel oldLevel, FillLevel newLevel) {
        if (newLevel.ordinal() < oldLevel.ordinal()) return "EMPTIED";
        if (newLevel.ordinal() > oldLevel.ordinal()) return "FILLED";
        return "UPDATED";
    }

    private DumpsterDTO toDTO(Dumpster d) {
        return new DumpsterDTO(d.getId(), d.getLocation(), d.getNumContainer(), d.getFillLevel().name(), d.getStatus(), d.getLastUpdate());
    }

    private DumpsterUsageDTO toUsageDTO(DumpsterUsage u) {
        return new DumpsterUsageDTO(u.getId(), u.getDumpster(), u.getTimestamp(), u.getFillLevelBefore().name(), u.getFillLevelAfter().name(), u.getAction(), u.getEmployeeId());
    }
}