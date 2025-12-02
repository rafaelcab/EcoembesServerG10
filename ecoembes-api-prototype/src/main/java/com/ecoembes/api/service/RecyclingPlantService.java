package com.ecoembes.api.service;

import com.ecoembes.api.domain.RecyclingPlant;
import com.ecoembes.api.factory.GatewayFactory;
import com.ecoembes.api.gateway.RecyclingPlantGateway;
import com.ecoembes.api.repository.RecyclingPlantRepository;
import org.springframework.stereotype.Service;

@Service
public class RecyclingPlantService {
    private final RecyclingPlantRepository repository;
    private final GatewayFactory gatewayFactory;

    public RecyclingPlantService(RecyclingPlantRepository repository, GatewayFactory gatewayFactory) {
        this.repository = repository;
        this.gatewayFactory = gatewayFactory;
    }

    public double getPlantCapacity(Long id) {
        RecyclingPlant plant = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Planta no encontrada"));
        
        RecyclingPlantGateway gateway = gatewayFactory.createGateway(plant.getImplementationType());
        return gateway.checkCapacity();
    }
}