package com.ecoembes.EcoembesServer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecoembes.EcoembesServer.entity.CapacidadDiaria;
import com.ecoembes.EcoembesServer.entity.PlantaReciclaje;

@Service
public class PlantaReciclajeService {

    private List<PlantaReciclaje> plantasReciclaje;

    public PlantaReciclajeService() {
        this.plantasReciclaje = new ArrayList<>();
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

    // +consultarCapacidad(idPlanta : Long, fecha : LocalDateTime) : double
   /* public double consultarCapacidad(long idPlanta, LocalDateTime fecha) {
        PlantaReciclaje planta = getPlantaById(idPlanta);

        if (planta == null) {
            throw new RuntimeException("Plant not found");
        }

        // Buscamos la capacidad para el día de 'fecha'
        for (CapacidadDiaria capacidadDiaria : planta.getCapacidades()) {
            if (capacidadDiaria.getFecha().toLocalDate()
                    .isEqual(fecha.toLocalDate())) {
                return capacidadDiaria.capacidadDisponible();
            }
        }

        throw new RuntimeException("Capacity not found for given date");
    }*/

    // +enviarNotificacion(mensajeNotificacion : String) : void
    public void enviarNotificacion(String mensajeNotificacion) {
        // Por ahora simplemente lo sacamos por consola.
        // Aquí podrías integrar email, logs, etc.
        System.out.println("[NOTIFICACION] " + mensajeNotificacion);
    }
}
