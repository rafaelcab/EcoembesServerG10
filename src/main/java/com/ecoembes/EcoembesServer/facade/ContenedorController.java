package com.ecoembes.EcoembesServer.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import com.ecoembes.EcoembesServer.dto.ContenedorDTO;
import com.ecoembes.EcoembesServer.dto.ContenedorEstadoDTO;
import com.ecoembes.EcoembesServer.dto.LecturaDTO;
import com.ecoembes.EcoembesServer.entity.Contenedor;
import com.ecoembes.EcoembesServer.entity.NivelLlenado;
import com.ecoembes.EcoembesServer.service.ContenedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/ecoembes/contenedor")
@Tag(name = "Contenedor Controller", description = "Operaciones sobre contenedores de Ecoembes")
public class ContenedorController {

    private final ContenedorService contenedorService;

    public ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    // =========================================================================
    // 1. CREAR CONTENEDOR
    // POST /ecoembes/contenedor/crear
    // =========================================================================
    @Operation(summary = "Crear un nuevo contenedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contenedor creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/crear")
    public ResponseEntity<ContenedorDTO> crearContenedor(
            @RequestParam("ubicacion") String ubicacion,
            @RequestParam("codigoPostal") int codigoPostal,
            @RequestParam("capacidadMaxima") double capacidadMaxima) {

        if (ubicacion == null || ubicacion.isBlank() || capacidadMaxima <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        contenedorService.crearContenedor(ubicacion, codigoPostal, capacidadMaxima);

        List<Contenedor> lista = contenedorService.getContenedores();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Contenedor creado = lista.get(lista.size() - 1);

        ContenedorDTO respuesta = new ContenedorDTO(
                creado.getId(),
                creado.getUbicacion(),
                NivelLlenado.VERDE,
                (int) creado.getCapacidad()
        );

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // =========================================================================
    // 2. OBTENER LECTURAS DE UN CONTENEDOR EN UN RANGO
    // GET /ecoembes/contenedor/{id}/lecturas?desde=&hasta=
    // =========================================================================
    @Operation(
        summary = "Consultar lecturas de un contenedor",
        description = "Devuelve todas las lecturas entre dos fechas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lecturas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay lecturas en ese rango"),
        @ApiResponse(responseCode = "404", description = "Contenedor no encontrado")
    })
    @GetMapping("/{id}/lecturas")
    public ResponseEntity<List<LecturaDTO>> obtenerLecturas(
            @PathVariable("id") Long idContenedor,
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        try {
            List<LecturaDTO> lecturas =
                    contenedorService.getContenedoresFecha(idContenedor, desde, hasta);

            if (lecturas == null || lecturas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lecturas, HttpStatus.OK);

        } catch (RuntimeException e) {
            if ("Container not found".equals(e.getMessage())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // =========================================================================
    // 3. ESTADO DE LOS CONTENEDORES EN UNA ZONA
    // GET /ecoembes/contenedor/zona/{codigoPostal}/estado?fecha=
    // =========================================================================
    @Operation(
        summary = "Consultar estado de contenedores por zona",
        description = "Devuelve el estado de los contenedores de un código postal en una fecha"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estados encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay contenedores o lecturas")
    })
    @GetMapping("/zona/{codigoPostal}/estado")
    public ResponseEntity<List<ContenedorEstadoDTO>> obtenerEstadoPorZona(
            @PathVariable("codigoPostal") String codigoPostal,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<ContenedorEstadoDTO> estados =
                contenedorService.getContainersStatusByZone(codigoPostal, fecha);

        if (estados == null || estados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    // =========================================================================
    // 4. ALERTA DE SATURACIÓN
    // GET /ecoembes/contenedor/alertas/saturacion
    // =========================================================================
    @Operation(summary = "Lanzar alerta de saturación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Revisión completada")
    })
    @GetMapping("/alertas/saturacion")
    public ResponseEntity<Void> alertaSaturacion() {
        contenedorService.alertaSaturacion();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
 // =========================================================================
    // 5. ACTUALIZAR INFORMACIÓN DE ENVASES (Endpoint 1)
    // URL: /api/containers/info (Método PUT)
    // =========================================================================
    @Operation(summary = "Actualizar información de envases y estado del contenedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información actualizada"),
        @ApiResponse(responseCode = "404", description = "Contenedor no encontrado")
    })
    @PutMapping("/info") // Mapeado relativo a /ecoembes/contenedor, resultando en /ecoembes/contenedor/info
    public ResponseEntity<Void> actualizarInformacion(
            @RequestParam("id") Long id,
            @RequestParam(value = "ubicacion", required = false) String ubicacion,
            @RequestParam("contenedores_necesarios") int contenedoresNecesarios, // Mapeado a numeroEstimadoEnvases
            @RequestParam("nivel_llenado") NivelLlenado nivelLlenado,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        try {
            // 1. Actualizar datos estáticos si se envían
            if (ubicacion != null) {
                // Lógica simple para actualizar ubicación si fuera necesario, 
                // podrías añadir un método setUbicacion en el servicio.
                Contenedor c = contenedorService.getContenedorById(id);
                if (c != null) c.setUbicacion(ubicacion);
            }

            // 2. Actualizar lectura (nivel y envases)
            contenedorService.actualizarLecturaContenedor(
                id, 
                contenedoresNecesarios, 
                nivelLlenado, 
                fecha.atStartOfDay() // Convertir LocalDate a LocalDateTime
            );
            
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

