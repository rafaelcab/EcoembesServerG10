package com.ecoembes.api.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

public record AssignmentDTO(
    @Schema(description = "ID de la asignación (ignorar al crear)", example = "1")
    Long id, 
    
    @Schema(description = "ID del empleado", example = "1")
    Long employeeId, 
    
    @Schema(description = "ID del contenedor", example = "D001")
    String dumpsterId, 
    
    @Schema(description = "ID de la planta", example = "1")
    Long recyclingPlantId, 
    
    @Schema(description = "Fecha de asignación")
    LocalDate date
) {}