package com.ecoembes.api.repository;

import com.ecoembes.api.domain.RecyclingPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingPlantRepository extends JpaRepository<RecyclingPlant, Long> {}