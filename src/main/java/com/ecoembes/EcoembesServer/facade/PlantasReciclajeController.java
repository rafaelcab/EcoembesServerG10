package com.ecoembes.EcoembesServer.facade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecoembes.EcoembesServer.dto.PlantaCapacidadesDTO;
import com.ecoembes.EcoembesServer.service.AsignacionService;
import com.ecoembes.EcoembesServer.service.PlantaReciclajeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/recycling-plants") // URL base ajustada según PDF
@Tag(name = "Plantas Reciclaje Controller", description = "Gestión de plantas y asignaciones")
public class PlantasReciclajeController {

    private final PlantaReciclajeService plantaService;
    private final AsignacionService asignacionService;

    public PlantasReciclajeController(PlantaReciclajeService plantaService, AsignacionService asignacionService) {
        this.plantaService = plantaService;
        this.asignacionService = asignacionService;
    }

    // -------------------------------------------------------------------------
    // 7. COMPROBAR CAPACIDAD DE PLANTA (PDF Endpoint 7)
    // URL: /api/recycling-plants/capacity?fecha=YYYY-MM-DD
    // -------------------------------------------------------------------------
    @Operation(summary = "Comprobar capacidad de plantas de reciclaje por fecha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Capacidades obtenidas correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay datos para esa fecha")
    })
    @GetMapping("/capacity")
    public ResponseEntity<List<PlantaCapacidadesDTO>> consultarCapacidad(
            @Parameter(description = "Fecha en formato YYYY-MM-DD") 
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        List<PlantaCapacidadesDTO> capacidades = plantaService.consultarCapacidadPorFecha(fecha);
        
        if (capacidades == null || capacidades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(capacidades, HttpStatus.OK);
    }

    // -------------------------------------------------------------------------
    // 8. ASIGNAR CONTENEDOR A PLANTA (PDF Endpoint 8)
    // URL: /api/recycling-plants/asignar
    // -------------------------------------------------------------------------
    @Operation(summary = "Asignar contenedores a una planta de reciclaje")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos de entrada o capacidad insuficiente"),
        @ApiResponse(responseCode = "404", description = "Planta o Contenedor no encontrados")
    })
    @PostMapping("/asignar")
    public ResponseEntity<Void> asignarContenedor(
            @RequestParam("idContenedor") Long idContenedor,
            @RequestParam("idPlanta") Long idPlanta,
            @RequestParam(value = "idUsuario", required = false, defaultValue = "0") Long idUsuario) {
        
        try {
            // Nota: El PDF no especifica usuario en los inputs, pero la lógica de negocio lo requiere.
            // Se asume un usuario por defecto (0 o admin) si no se envía, o se debería extraer del Token.
            asignacionService.generarAsignacion(
                List.of(idContenedor), 
                LocalDateTime.now(), 
                idPlanta, 
                idUsuario
            );
            return new ResponseEntity<>(HttpStatus.OK);
            
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
