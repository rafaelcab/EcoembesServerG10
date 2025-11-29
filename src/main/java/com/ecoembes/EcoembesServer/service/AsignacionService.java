package com.ecoembes.EcoembesServer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecoembes.EcoembesServer.entity.Asignacion;
import com.ecoembes.EcoembesServer.entity.Contenedor;
import com.ecoembes.EcoembesServer.entity.PlantaReciclaje;
import com.ecoembes.EcoembesServer.entity.Usuario;

@Service
public class AsignacionService {

    // Del diagrama: asignaciones : List<Asignacion>
    private List<Asignacion> asignaciones;

    private final ContenedorService contenedorService;
    private final AuthService authService;
    private final PlantaReciclajeService plantaReciclajeService;

    // Constructor with parameters (inyección de dependencias)
    public AsignacionService(ContenedorService contenedorService,
                               AuthService authService,
                               PlantaReciclajeService plantaReciclajeService) {
        this.contenedorService = contenedorService;
        this.authService = authService;
        this.plantaReciclajeService = plantaReciclajeService;
        this.asignaciones = new ArrayList<>();
    }

    public List<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    // +generarAsignacion(contenedores : List<Long>, fecha : LocalDateTime,
    //                    planta : PlantaReciclaje, usuario : idUsuario) : void
    public void generarAsignacion(List<Long> contenedoresIds,
            LocalDateTime fecha,
            long idPlanta,   // <--- Cambiado a long para coincidir con la llamada del Controller
            long idUsuario) {

		// 1. Buscar usuario (ahora funcionará tras actualizar AuthService)
		Usuario usuario = authService.getUsuarioById(idUsuario);
		
		// Si no existe el usuario (o id es 0), podríamos asignar null o lanzar error.
		// Para este ejemplo, validamos:
		if (usuario == null) {
		throw new RuntimeException("User not found with id: " + idUsuario);
		}
		
		// 2. Buscar la planta por ID
		PlantaReciclaje plantaPersistida = plantaReciclajeService.getPlantaById(idPlanta);
		if (plantaPersistida == null) {
		throw new RuntimeException("Plant not found with id: " + idPlanta);
		}
		
		// 3. Crear la Asignación
		// Error corregido: El constructor de Asignacion pide (LocalDate, PlantaReciclaje, Usuario)
		Asignacion asignacion = new Asignacion(fecha.toLocalDate(), plantaPersistida, usuario);
		
		// 4. Añadir contenedores
		for (Long idContenedor : contenedoresIds) {
		Contenedor cont = contenedorService.getContenedorById(idContenedor);
		if (cont == null) {
		throw new RuntimeException("Container not found with id: " + idContenedor);
		}
		// Error corregido: el método en la entidad se llama 'agregarContenedor'
		asignacion.agregarContenedor(cont);
		}
		
		// Guardar en la lista local del servicio
		this.asignaciones.add(asignacion);
		
		// Error corregido: Eliminamos 'usuario.addAsignacion(asignacion)' 
		// porque la clase Usuario no tiene lista de asignaciones.
		}
}
