package com.ecoembes.api.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

public record DumpsterDTO(
    @Schema(description = "ID del contenedor", example = "D001") 
    String id, 
    
    @Schema(description = "Ubicación", example = "Madrid Centro") 
    String location, 
    
    @Schema(description = "Número", example = "1") 
    int numContainer, 
    
    @Schema(description = "Nivel", example = "GREEN", allowableValues = {"GREEN", "ORANGE", "RED"}) 
    String fillLevel, 
    
    @Schema(description = "Estado", example = "OPERATIONAL") 
    String status,
    
    @Schema(hidden = true) 
    LocalDateTime lastUpdate
) {}