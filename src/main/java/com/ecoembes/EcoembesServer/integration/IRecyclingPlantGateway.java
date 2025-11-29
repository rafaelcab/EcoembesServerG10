package com.ecoembes.EcoembesServer.integration;

import java.time.LocalDate;

public interface IRecyclingPlantGateway {
    double getCapacity(LocalDate fecha);
    void notifyAllocation(LocalDate fecha, int numContenedores, int totalEnvases);
}