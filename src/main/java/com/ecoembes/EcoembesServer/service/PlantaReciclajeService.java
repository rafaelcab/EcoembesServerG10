package com.ecoembes.EcoembesServer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecoembes.EcoembesServer.Assembler.PlantaReciclajeAssembler;
import com.ecoembes.EcoembesServer.dto.PlantaCapacidadesDTO;
import com.ecoembes.EcoembesServer.entity.CapacidadDiaria;
import com.ecoembes.EcoembesServer.entity.PlantaReciclaje;

@Service
public class PlantaReciclajeService {

    private List<PlantaReciclaje> plantasReciclaje;
    private final PlantaReciclajeAssembler plantaReciclajeAssembler;

    public PlantaReciclajeService(PlantaReciclajeAssembler plantaReciclajeAssembler) {
        this.plantasReciclaje = new ArrayList<>();
        this.plantaReciclajeAssembler = plantaReciclajeAssembler;
    }

    public List<PlantaReciclaje> getPlantasReciclaje() {
        return plantasReciclaje;
    }

    public PlantaReciclaje getPlantaById(long idPlanta) {
        return plantasReciclaje.stream()
                .filter(p -> p.getId() == idPlanta)
                .findFirst()
                .orElse(null);
    }

    // +crearPlantaReciclaje(nombre : String, capacidad : double) : void
    public void crearPlantaReciclaje(String nombre, double capacidadTon) {
        PlantaReciclaje planta = new PlantaReciclaje(nombre, capacidadTon);
        this.plantasReciclaje.add(planta);
    }

    // Mantengo tu método original: UNA planta, LocalDateTime -> double
    public double consultarCapacidad(long idPlanta, LocalDateTime fecha) {
        PlantaReciclaje planta = getPlantaById(idPlanta);

        if (planta == null) {
            throw new RuntimeException("Plant not found");
        }

        for (CapacidadDiaria capacidadDiaria : planta.getCapacidades()) {
            if (capacidadDiaria.getFecha().isEqual(fecha.toLocalDate())) {
                return capacidadDiaria.getCapacidadDisponible();
            }
        }

        throw new RuntimeException("Capacity not found for given date");
    }

    // ✅ NUEVO: capacidad disponible de TODAS las plantas para una fecha dada
    public List<PlantaCapacidadesDTO> consultarCapacidadPorFecha(LocalDate fecha) {
        List<PlantaCapacidadesDTO> resultado = new ArrayList<>();

        for (PlantaReciclaje planta : plantasReciclaje) {

            // Buscar la capacidad diaria de esa planta para la fecha dada
            CapacidadDiaria capacidadDia = null;

            for (CapacidadDiaria cd : planta.getCapacidades()) {
                if (cd.getFecha().isEqual(fecha)) {
                    capacidadDia = cd;
                    break;
                }
            }

            // Si hay registro de capacidad para esa fecha → lo añadimos
            if (capacidadDia != null) {
                double disponible = capacidadDia.getCapacidadDisponible();

                PlantaCapacidadesDTO dto =
                        plantaReciclajeAssembler.toPlantaCapacidadDTO(planta, disponible);

                resultado.add(dto);
            }

            // Si quisieras que salgan todas las plantas aunque no tengan registro
            // ese día, podrías añadir un else aquí creando un dto con capacidadTon
        }

        return resultado;
    }

    // +enviarNotificacion(mensajeNotificacion : String) : void
    public void enviarNotificacion(String mensajeNotificacion) {
        System.out.println("[NOTIFICACION] " + mensajeNotificacion);
    }
}

