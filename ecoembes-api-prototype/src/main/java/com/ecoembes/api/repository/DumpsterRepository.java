package com.ecoembes.api.repository;

import com.ecoembes.api.domain.Dumpster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DumpsterRepository extends JpaRepository<Dumpster, String> {}