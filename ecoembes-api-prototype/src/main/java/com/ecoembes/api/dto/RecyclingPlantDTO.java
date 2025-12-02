package com.ecoembes.api.dto;

public record RecyclingPlantDTO(Long id, String name, double capacity, double availableCapacity, String location) {
}