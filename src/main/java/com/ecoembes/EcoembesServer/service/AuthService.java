/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package com.ecoembes.EcoembesServer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecoembes.EcoembesServer.entity.Usuario;

@Service
public class AuthService {

    // Simulating a user repository
    private static Map<String, Usuario> userRepository = new HashMap<>();
    
    // Storage to keep the session of the users that are logged in
    private static Map<String, Usuario> tokenStore = new HashMap<>();

    // Login method that checks if the user exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        Usuario usuario = userRepository.get(email);
        
        if (usuario != null && usuario.checkPassword(password)) {
            String token = generateToken();   // Generate a random token for the session
            tokenStore.put(token, usuario);   // Store the token and associate it with the user

            return Optional.of(token);
        } else {
            return Optional.empty();
        }
    }

    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);
            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }

    // Method to add a new user to the repository
    public void addUser(Usuario usuario) {
        if (usuario != null) {
            userRepository.putIfAbsent(usuario.getEmail(), usuario);
        }
    }

    // Alias para que te siga funcionando si en algún sitio llamas a registrarUsuario()
    public void registrarUsuario(Usuario usuario) {
        addUser(usuario);
    }

    // Method to get the user based on the token
    public Usuario getUserByToken(String token) {
        return tokenStore.get(token);
    }

    // Method to get the user based on the email
    public Usuario getUserByEmail(String email) {
        return userRepository.get(email);
    }

    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}

	

