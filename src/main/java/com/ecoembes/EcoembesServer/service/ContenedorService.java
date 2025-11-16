package com.ecoembes.EcoembesServer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecoembes.EcoembesServer.dto.ContenedorDTO;
import com.ecoembes.EcoembesServer.entity.Contenedor;
import com.ecoembes.EcoembesServer.entity.LecturaContenedor;
import com.ecoembes.EcoembesServer.entity.NivelLlenado;

@Service
public class ContenedorService {

    // Del diagrama: contenedores : List<Contenedor>
    private List<Contenedor> contenedores;

    // EXTRA: almacenar el código postal de cada contenedor
    // (porque el Contenedor entity que me has pasado NO tiene ese atributo).
    private Map<Long, Integer> codigosPostalesPorContenedor;

    // Constructor without parameters
    public ContenedorService() {
        this.contenedores = new ArrayList<>();
        this.codigosPostalesPorContenedor = new HashMap<>();
    }

    public List<Contenedor> getContenedores() {
        return contenedores;
    }

    public Contenedor getContenedorById(long id) {
        return contenedores.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // +crearContenedor(ubicacion : String, codPostal : int, capMaxima : double) : void
    public void crearContenedor(String ubicacion, int codPostal, double capMaxima) {
        Contenedor contenedor = new Contenedor(ubicacion, capMaxima);
        contenedores.add(contenedor);
        codigosPostalesPorContenedor.put(contenedor.getId(), codPostal);
    }

    // Versión "lógica": indicando el contenedor
    public void crearLecturaContenedor(long idContenedor, int numEnvases, NivelLlenado nivelLlenado) {
        Contenedor contenedor = getContenedorById(idContenedor);

        if (contenedor == null) {
            throw new RuntimeException("Container not found");
        }

        LecturaContenedor lectura = new LecturaContenedor(numEnvases, nivelLlenado);
        LocalDate hoy = LocalDate.now();
        contenedor.registrarLecturaContenedor(hoy, lectura);
    }

    // Método del diagrama (sin id de contenedor):
    // +crearLecturaContenedor(numEnvases : int, nivelLlenado : NivelLlenado) : void
    // -> por compatibilidad, usa el primer contenedor si existe.
    public void crearLecturaContenedor(int numEnvases, NivelLlenado nivelLlenado) {
        if (contenedores.isEmpty()) {
            throw new RuntimeException("No containers available");
        }
        crearLecturaContenedor(contenedores.get(0).getId(), numEnvases, nivelLlenado);
    }

    // Versión "lógica": indicando contenedor y fecha
    public void actualizarLecturaContenedor(long idContenedor, int numEnvases,
                                            NivelLlenado nivelLlenado, LocalDateTime fecha) {
        Contenedor contenedor = getContenedorById(idContenedor);

        if (contenedor == null) {
            throw new RuntimeException("Container not found");
        }

        LocalDate dia = fecha.toLocalDate();
        LecturaContenedor lectura = contenedor.getLecturasContenedor().get(dia);

        if (lectura == null) {
            // Si no existe lectura en esa fecha, creamos una nueva
            lectura = new LecturaContenedor(numEnvases, nivelLlenado);
        } else {
            lectura.setNumeroEstimadoEnvases(numEnvases);
            lectura.setNivelLlenado(nivelLlenado);
        }

        contenedor.registrarLecturaContenedor(dia, lectura);
    }

    // Método del diagrama:
    // +actualizarLecturaContenedor(numEnvases : int, nivelLlenado : NivelLlenado, fecha : LocalDateTime) : void
    // -> usa el primer contenedor para mantener la firma original.
    public void actualizarLecturaContenedor(int numEnvases, NivelLlenado nivelLlenado, LocalDateTime fecha) {
        if (contenedores.isEmpty()) {
            throw new RuntimeException("No containers available");
        }
        actualizarLecturaContenedor(contenedores.get(0).getId(), numEnvases, nivelLlenado, fecha);
    }
/*
    // +consultarPorFecha(codPostal : int) : ArrayList<ContenedorDTO>
    public ArrayList<ContenedorDTO> consultarPorFecha(int codPostal) {
        // Interpretación: devuelve los contenedores de ese CP,
        // usando la lectura del día actual (si hay).
        LocalDate hoy = LocalDate.now();
        ArrayList<ContenedorDTO> resultado = new ArrayList<>();

        for (Contenedor cont : contenedores) {
            Integer cp = codigosPostalesPorContenedor.get(cont.getId());
            if (cp != null && cp == codPostal) {
                LecturaContenedor lectura = cont.getLecturasContenedor().get(hoy);
                resultado.add(crearContenedorDTO(cont, lectura));
            }
        }
        return resultado;
    }

    // +consultarPorZona(codPostal : int) : ArrayList<ContenedorDTO>
    public ArrayList<ContenedorDTO> consultarPorZona(int codPostal) {
        // Interpretación: "zona" = mismo CP por ahora.
        // Si después quieres jugar con rangos de CP (ej 28080±5) se cambia aquí.
        return consultarPorFecha(codPostal);
    }*/

    // +alertaSaturacion() : void
    public void alertaSaturacion() {
        // Ejemplo simple: sacar por consola los contenedores en ROJO
        LocalDate hoy = LocalDate.now();

        for (Contenedor cont : contenedores) {
            LecturaContenedor lectura = cont.getLecturasContenedor().get(hoy);
            if (lectura != null && lectura.getNivelLlenado() == NivelLlenado.ROJO) {
                System.out.println("[ALERTA] Contenedor " + cont.getId()
                        + " en " + cont.getUbicacion()
                        + " está en nivel ROJO");
            }
        }
    }
/*
    // ---- Conversión Contenedor -> ContenedorDTO (lo que haría el ContenedorAssembler) ----
    private ContenedorDTO crearContenedorDTO(Contenedor contenedor, LecturaContenedor lectura) {
        ContenedorDTO dto = new ContenedorDTO();

        dto.setId(contenedor.getId());
        dto.setUbicacion(contenedor.getUbicacion());
        dto.setCapacidad((int) contenedor.getCapacidad());

        if (lectura != null) {
            dto.setNivelLlenado(lectura.getNivelLlenado());
        } else {
            // Si no hay lectura, por defecto VERDE (decisión arbitraria).
            dto.setNivelLlenado(NivelLlenado.VERDE);
        }

        return dto;
    }*/
}

