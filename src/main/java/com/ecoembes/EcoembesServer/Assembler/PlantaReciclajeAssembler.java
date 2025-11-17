package com.ecoembes.EcoembesServer.Assembler;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import com.ecoembes.EcoembesServer.dto.PlantaCapacidadesDTO;
import com.ecoembes.EcoembesServer.dto.PlantaReciclajeDTO;
import com.ecoembes.EcoembesServer.entity.PlantaReciclaje;

@Component
public class PlantaReciclajeAssembler {

    public PlantaReciclajeDTO createPlantaReciclajeDTO(PlantaReciclaje planta) {
        if (planta == null) return null;
        return new PlantaReciclajeDTO(
                planta.getId(),
                planta.getNombre(),
                planta.getCapacidadTon()
        );
    }

    public PlantaReciclaje updatePlantaReciclaje(PlantaReciclajeDTO dto) {
        if (dto == null) return null;

        PlantaReciclaje planta = new PlantaReciclaje();
        planta.setNombre(dto.getNombre());
        planta.setCapacidadTon(dto.getCapacidadTon());

        return planta;
    }
    
    public PlantaCapacidadesDTO toPlantaCapacidadDTO(PlantaReciclaje planta,
            double capacidadDisponibleTon) {
	if (planta == null) {
		return null;
	}
	return new PlantaCapacidadesDTO(
		planta.getNombre(),
		capacidadDisponibleTon
	);
}
}
