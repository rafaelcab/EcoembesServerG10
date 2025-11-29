package com.ecoembes.EcoembesServer.integration;

import com.ecoembes.EcoembesServer.integration.impl.ContSocketGateway;
import com.ecoembes.EcoembesServer.integration.impl.PlasSBGateway;
import org.springframework.stereotype.Component;

@Component
public class RecyclingPlantGatewayFactory {

    public IRecyclingPlantGateway getGateway(String nombrePlanta) {
        // Estructura IF/SWITCH encapsulada en la factoría (Requisito clave)
        if (nombrePlanta.toLowerCase().contains("plassb")) {
            return new PlasSBGateway();
        } else if (nombrePlanta.toLowerCase().contains("contsocket")) {
            return new ContSocketGateway();
        } else {
            throw new IllegalArgumentException("Planta desconocida: " + nombrePlanta);
        }
    }
}