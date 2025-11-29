package com.ecoembes.EcoembesServer.integration.impl;

import com.ecoembes.EcoembesServer.integration.IRecyclingPlantGateway;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDate;

public class PlasSBGateway implements IRecyclingPlantGateway {
    
    private final String baseUrl = "http://localhost:8081/api/plassb"; // Puerto del servidor PlasSB
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public double getCapacity(LocalDate fecha) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/capacity")
                .queryParam("fecha", fecha.toString())
                .toUriString();
        // Asumimos que devuelve un Double directamente o un objeto JSON simple
        Double capacidad = restTemplate.getForObject(url, Double.class);
        return (capacidad != null) ? capacidad : 0.0;
    }

    @Override
    public void notifyAllocation(LocalDate fecha, int numContenedores, int totalEnvases) {
        String url = baseUrl + "/allocation?fecha=" + fecha + "&num_contenedores=" + numContenedores + "&total_envases=" + totalEnvases;
        restTemplate.postForLocation(url, null);
    }
}