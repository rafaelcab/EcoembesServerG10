package com.ecoembes.EcoembesServer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecoembes.EcoembesServer.dto.ContenedorDTO;
import com.ecoembes.EcoembesServer.dto.ContenedorEstadoDTO;
import com.ecoembes.EcoembesServer.dto.LecturaDTO;
import com.ecoembes.EcoembesServer.entity.Contenedor;
import com.ecoembes.EcoembesServer.entity.LecturaContenedor;
import com.ecoembes.EcoembesServer.entity.NivelLlenado;
import com.ecoembes.EcoembesServer.Assembler.ContenedorAssembler;

@Service
public class ContenedorService {

    // Del diagrama: contenedores : List<Contenedor>
    private List<Contenedor> contenedores;
    private Map<Long, Integer> codigosPostalesPorContenedor;
    private final ContenedorAssembler contenedorAssembler;

    // Constructor without parameters
    public ContenedorService(ContenedorAssembler contenedorAssembler) {
        this.contenedores = new ArrayList<>();
        this.codigosPostalesPorContenedor = new HashMap<>();
        this.contenedorAssembler = contenedorAssembler;
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


    public List<LecturaDTO> getContenedoresFecha(Long containerId, LocalDate from, LocalDate to) {
        Contenedor contenedor = getContenedorById(containerId);

        if (contenedor == null) {
            throw new RuntimeException("Container not found");
        }

        List<LecturaDTO> result = new ArrayList<>();

        for (Map.Entry<LocalDate, LecturaContenedor> entry : contenedor.getLecturasContenedor().entrySet()) {
            LocalDate fecha = entry.getKey();
            LecturaContenedor lectura = entry.getValue();

            // Solo consideramos las lecturas dentro del intervalo [from, to]
            if ((fecha.isEqual(from) || fecha.isAfter(from)) &&
                (fecha.isEqual(to)   || fecha.isBefore(to))) {

                LecturaDTO dto = contenedorAssembler.toLecturaDTO(fecha, lectura);
                if (dto != null) {
                    result.add(dto);
                }
            }
        }
        return result;
    }

    public List<ContenedorEstadoDTO> getContainersStatusByZone(String postalCode, LocalDate date) {
        List<ContenedorEstadoDTO> result = new ArrayList<>();

        int cpBuscado;
        try {
            cpBuscado = Integer.parseInt(postalCode);
        } catch (NumberFormatException e) {
            return result;
        }

        for (Contenedor cont : contenedores) {

            Integer cp = codigosPostalesPorContenedor.get(cont.getId());

            // Ahora SIN continue → procesamos solo cuando coincide
            if (cp != null && cp == cpBuscado) {

                LecturaContenedor lectura = cont.getLecturasContenedor().get(date);

                int numeroEstimadoEnvases = 0;
                NivelLlenado nivelLlenado = NivelLlenado.VERDE;

                if (lectura != null) {
                    numeroEstimadoEnvases = lectura.getNumeroEstimadoEnvases();
                    nivelLlenado = lectura.getNivelLlenado();
                }

                result.add(new ContenedorEstadoDTO(
                        cont.getId(),
                        cont.getUbicacion(),
                        cont.getCapacidad(),
                        numeroEstimadoEnvases,
                        nivelLlenado
                ));
            }
        }

        return result;
    }

    // ----------------- MÉTODO AUXILIAR COMÚN -----------------

    private int calcularPorcentajeOcupacion(Contenedor contenedor, LecturaContenedor lectura) {
        if (contenedor.getCapacidad() <= 0) {
            return 0;
        }
        double ratio = lectura.getNumeroEstimadoEnvases() / contenedor.getCapacidad();
        int porcentaje = (int) Math.round(ratio * 100);
        if (porcentaje < 0) porcentaje = 0;
        if (porcentaje > 100) porcentaje = 100;
        return porcentaje;
    }


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
}

