package com.ecoembes.api.repository;

import com.ecoembes.api.domain.DumpsterUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DumpsterUsageRepository extends JpaRepository<DumpsterUsage, Long> {
    List<DumpsterUsage> findByDumpsterId(String dumpsterId);
}