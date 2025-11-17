package com.ecoembes.EcoembesServer.Assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecoembes.EcoembesServer.dto.AsignacionDTO;
import com.ecoembes.EcoembesServer.dto.ContenedorDTO;
import com.ecoembes.EcoembesServer.dto.PlantaReciclajeDTO;
import com.ecoembes.EcoembesServer.entity.Asignacion;

@Component
public class AsignacionAssembler {

    private final ContenedorAssembler contenedorAssembler;
    private final PlantaReciclajeAssembler plantaAssembler;

    public AsignacionAssembler(
            ContenedorAssembler contenedorAssembler,
            PlantaReciclajeAssembler plantaAssembler) {
        this.contenedorAssembler = contenedorAssembler;
        this.plantaAssembler = plantaAssembler;
    }

    public AsignacionDTO createAsignacionDTO(Asignacion asignacion) {

        if (asignacion == null) return null;

        List<ContenedorDTO> contDTOs = new ArrayList<>();
        asignacion.getContenedores()
                .forEach(c -> contDTOs.add(contenedorAssembler.createContenedorDTO(c)));

        PlantaReciclajeDTO plantaDTO = plantaAssembler.createPlantaReciclajeDTO(asignacion.getPlanta());

        return new AsignacionDTO(
                asignacion.getId(),
                contDTOs,
                plantaDTO,
                asignacion.getFecha()
        );
    }

    public Asignacion updateAsignacion(AsignacionDTO dto) {
        if (dto == null) return null;

        Asignacion asignacion = new Asignacion();
        asignacion.setFecha(dto.getFecha());
        // contenedores y planta se asignan en AsignacionesService

        return asignacion;
    }
}
