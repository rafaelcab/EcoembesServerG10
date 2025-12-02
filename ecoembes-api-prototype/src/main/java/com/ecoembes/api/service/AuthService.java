package com.ecoembes.api.service;

import com.ecoembes.api.domain.Employee;
import com.ecoembes.api.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepo;
    // Cache simple de tokens en memoria para esta demo
    private final Map<String, Long> activeTokens = new HashMap<>();

    public AuthService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Map<String, Object> login(String email, String password) {
        Employee e = employeeRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas (Usuario no encontrado)"));

        if (!e.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales inválidas (Password incorrecto)");
        }

        // Generar Token
        String token = "TOKEN-" + UUID.randomUUID().toString();
        activeTokens.put(token, e.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("id", e.getId());
        response.put("name", e.getName());
        response.put("email", e.getEmail());
        response.put("token", token);
        return response;
    }

    public void logout(String token) {
        activeTokens.remove(token);
    }

    public Long validateToken(String token) {
        if (token == null || !activeTokens.containsKey(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }
        return activeTokens.get(token);
    }
}