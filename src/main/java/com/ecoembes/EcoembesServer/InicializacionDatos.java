package com.ecoembes.EcoembesServer;

import java.time.LocalDateTime;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecoembes.EcoembesServer.entity.PlantaReciclaje;
import com.ecoembes.EcoembesServer.entity.Usuario;
import com.ecoembes.EcoembesServer.entity.NivelLlenado;
import com.ecoembes.EcoembesServer.entity.LecturaContenedor;
import com.ecoembes.EcoembesServer.entity.Contenedor;

import com.ecoembes.EcoembesServer.service.AuthService;
import com.ecoembes.EcoembesServer.service.PlantaReciclajeService;
import com.ecoembes.EcoembesServer.service.ContenedorService;
import com.ecoembes.EcoembesServer.service.AsignacionService;

@Configuration
public class InicializacionDatos {

    private static final Logger logger = LoggerFactory.getLogger(InicializacionDatos.class);

    @Bean
    CommandLineRunner initData(
            AuthService authService,
            PlantaReciclajeService plantaService,
            ContenedorService contenedorService,
            AsignacionService asignacionesService) {

        return args -> {

            logger.info("===== Inicializando datos Ecoembes... =====");

            // -------------------------------------------------------------
            // 1. USUARIOS
            // -------------------------------------------------------------
            Usuario admin = new Usuario("admin@ecoembes.com", "admin123", "Administrador");
            Usuario operador1 = new Usuario("operador1@ecoembes.com", "op123", "Operador 1");
            Usuario operador2 = new Usuario("operador2@ecoembes.com", "op456", "Operador 2");

            authService.registrarUsuario(admin);
            authService.registrarUsuario(operador1);
            authService.registrarUsuario(operador2);

            logger.info("Usuarios inicializados");


            // -------------------------------------------------------------
            // 2. PLANTAS DE RECICLAJE
            // -------------------------------------------------------------
            plantaService.crearPlantaReciclaje("Planta Norte", 15000);
            plantaService.crearPlantaReciclaje("Planta Sur", 20000);

            PlantaReciclaje plantaNorte = plantaService.getPlantasReciclaje().get(0);
            PlantaReciclaje plantaSur   = plantaService.getPlantasReciclaje().get(1);

            logger.info("Plantas creadas");


            // -------------------------------------------------------------
            // 3. CONTENEDORES
            // -------------------------------------------------------------
            contenedorService.crearContenedor("Calle Mayor 12", 28001, 1000);
            contenedorService.crearContenedor("Avenida Libertad 45", 28002, 1500);
            contenedorService.crearContenedor("Plaza España 7", 28003, 2000);

            Contenedor c1 = contenedorService.getContenedores().get(0);
            Contenedor c2 = contenedorService.getContenedores().get(1);
            Contenedor c3 = contenedorService.getContenedores().get(2);

            logger.info("Contenedores creados");


            // -------------------------------------------------------------
            // 4. LECTURAS CONTENEDOR
            // -------------------------------------------------------------
            LecturaContenedor lec1 = new LecturaContenedor(50, NivelLlenado.VERDE);
            LecturaContenedor lec2 = new LecturaContenedor(900, NivelLlenado.NARANJA);
            LecturaContenedor lec3 = new LecturaContenedor(2000, NivelLlenado.ROJO);

            c1.registrarLecturaContenedor(LocalDate.now(), lec1);
            c2.registrarLecturaContenedor(LocalDate.now(), lec2);
            c3.registrarLecturaContenedor(LocalDate.now(), lec3);

            logger.info("Lecturas de contenedores registradas");


            // -------------------------------------------------------------
            // 5. ASIGNACIONES
            // -------------------------------------------------------------
/*            asignacionesService.generarAsignacion(
                    java.util.List.of(c1.getId(), c2.getId()),
                    LocalDateTime.now(),
                    plantaNorte,
                    operador1.getId()
            );

            asignacionesService.generarAsignacion(
                    java.util.List.of(c3.getId()),
                    LocalDateTime.now().minusHours(5),
                    plantaSur,
                    operador2.getId()
            );

            logger.info("Asignaciones creadas");*/


            // -------------------------------------------------------------
            // FIN
            // -------------------------------------------------------------
            logger.info("===== Carga de datos Ecoembes completada =====");
        };
    }
}
	

