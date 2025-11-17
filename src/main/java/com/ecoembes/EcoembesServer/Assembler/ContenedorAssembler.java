package com.ecoembes.EcoembesServer.Assembler;

import org.springframework.stereotype.Component;
import java.time.LocalDate;

import com.ecoembes.EcoembesServer.dto.ContenedorDTO;
import com.ecoembes.EcoembesServer.dto.ContenedorEstadoDTO;
import com.ecoembes.EcoembesServer.dto.LecturaDTO;
import com.ecoembes.EcoembesServer.entity.Contenedor;
import com.ecoembes.EcoembesServer.entity.LecturaContenedor;
import com.ecoembes.EcoembesServer.entity.NivelLlenado;
@Component
public class ContenedorAssembler {

    public ContenedorDTO createContenedorDTO(Contenedor contenedor) {
        if (contenedor == null) return null;

        return new ContenedorDTO(
                contenedor.getId(),
                contenedor.getUbicacion(),
                contenedor.getNivelLlenadoActual(),   // ← Asegúrate de tener este método
                (int) contenedor.getCapacidad()
        );
    }

    public Contenedor updateContenedor(ContenedorDTO dto) {
        if (dto == null) return null;

        Contenedor contenedor = new Contenedor();
        contenedor.setUbicacion(dto.getUbicacion());
        contenedor.setCapacidad(dto.getCapacidad());

        // OJO: nivel de llenado depende de Lecturas → se setea en ContenedorService

        return contenedor;
    }

	public ContenedorEstadoDTO toContenedorEstadoDTO(Contenedor contenedor, LecturaContenedor lectura) {
	    if (contenedor == null) {
	        return null;
	    }
	
	    long id = contenedor.getId();
	    String ubicacion = contenedor.getUbicacion();
	    double capacidad = contenedor.getCapacidad();
	
	    int numeroEstimadoEnvases = 0;
	    NivelLlenado nivelLlenado = null;
	
	    if (lectura != null) {
	        numeroEstimadoEnvases = lectura.getNumeroEstimadoEnvases();
	        nivelLlenado = lectura.getNivelLlenado();
	    } else {
	        nivelLlenado = NivelLlenado.VERDE;
	    }
	
	    return new ContenedorEstadoDTO(
	            id,
	            ubicacion,
	            capacidad,
	            numeroEstimadoEnvases,
	            nivelLlenado
	    );
	}
	
	public LecturaDTO toLecturaDTO(LocalDate fecha, LecturaContenedor lectura) {
        if (lectura == null) {
            return null;
        }

        return new LecturaDTO(
                fecha,
                lectura.getNumeroEstimadoEnvases(),
                lectura.getNivelLlenado()
        );
    }
}
