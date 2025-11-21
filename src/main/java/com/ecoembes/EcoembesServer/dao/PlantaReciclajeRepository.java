package com.ecoembes.EcoembesServer.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecoembes.EcoembesServer.entity.PlantaReciclaje;

@Repository
public interface PlantaReciclajeRepository extends JpaRepository<PlantaReciclaje, Long> {
}