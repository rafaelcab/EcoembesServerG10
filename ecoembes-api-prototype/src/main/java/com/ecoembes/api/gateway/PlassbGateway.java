package com.ecoembes.api.gateway;

import org.springframework.web.client.RestTemplate;

public class PlassbGateway implements RecyclingPlantGateway {
    private final String url;
    private final RestTemplate restTemplate;

    public PlassbGateway(String url) {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public double checkCapacity() {
        try {
            return restTemplate.getForObject(url + "/api/capacity", Double.class);
        } catch (Exception e) {
            System.err.println("Error conectando a PlasSB: " + e.getMessage());
            return -1.0;
        }
    }
}