package com.ecoembes.EcoembesServer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecoembes.EcoembesServer.entity.Contenedor;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Long> {
}