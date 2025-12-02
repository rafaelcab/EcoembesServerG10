package com.ecoembes.api.factory;

import com.ecoembes.api.gateway.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GatewayFactory {
    @Value("${external.plassb.url}")
    private String plassbUrl;

    @Value("${external.contsocket.host}")
    private String socketHost;

    @Value("${external.contsocket.port}")
    private int socketPort;

    public RecyclingPlantGateway createGateway(String type) {
        if ("PLASSB".equalsIgnoreCase(type)) {
            return new PlassbGateway(plassbUrl);
        } else if ("CONTSOCKET".equalsIgnoreCase(type)) {
            return new ContSocketGateway(socketHost, socketPort);
        }
        throw new IllegalArgumentException("Tipo desconocido: " + type);
    }
}