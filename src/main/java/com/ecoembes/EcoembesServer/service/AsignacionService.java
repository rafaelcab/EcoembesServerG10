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
  /*  public void generarAsignacion(List<Long> contenedoresIds,
                                  LocalDateTime fecha,
                                  PlantaReciclaje planta,
                                  long idUsuario) {

        Usuario usuario = authService.getUsuarioById(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("User not found");
        }

        // Por seguridad, refrescamos la planta por id (por si viene de fuera desactualizada)
        PlantaReciclaje plantaPersistida = plantaReciclajeService.getPlantaById(planta.getId());
        if (plantaPersistida == null) {
            throw new RuntimeException("Plant not found");
        }

        Asignacion asignacion = new Asignacion(fecha);
        asignacion.setPlanta(plantaPersistida);
        asignacion.setUsuario(usuario);

        for (Long idContenedor : contenedoresIds) {
            Contenedor cont = contenedorService.getContenedorById(idContenedor);
            if (cont == null) {
                throw new RuntimeException("Container not found with id: " + idContenedor);
            }
            asignacion.addContenedor(cont);
        }

        this.asignaciones.add(asignacion);
        usuario.addAsignacion(asignacion);
    }*/
}
