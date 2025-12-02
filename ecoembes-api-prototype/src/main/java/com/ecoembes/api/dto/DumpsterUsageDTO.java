package com.ecoembes.api.dto;

import java.time.LocalDateTime;

import com.ecoembes.api.domain.Dumpster;
import com.ecoembes.api.domain.Employee;

public record DumpsterUsageDTO(Long id, Dumpster dumpster, LocalDateTime timestamp, String fillLevelBefore,
		String fillLevelAfter, String action, Employee employee) {
}