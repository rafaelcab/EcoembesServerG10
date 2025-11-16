package com.ecoembes.EcoembesServer.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecoembes.EcoembesServer.entity.Token;
import com.ecoembes.EcoembesServer.entity.Usuario;

@Service
public class AuthService {

    private Map<Long, Usuario> usuarios;
    private Map<String, Token> tokensActivos;

    public AuthService() {
        this.usuarios = new HashMap<>();
        this.tokensActivos = new HashMap<>();
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuario != null) {
            usuarios.put(usuario.getId(), usuario);
        }
    }

    public Usuario getUsuarioById(long idUsuario) {
        return usuarios.get(idUsuario);
    }

    public Usuario getUsuarioByEmail(String email) {
        for (Usuario u : usuarios.values()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public String login(String email, String password) {

        Usuario usuario = getUsuarioByEmail(email);

        if (usuario == null) {
            throw new RuntimeException("User not found");
        }

        if (!usuario.getContrasenya().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        // Crear token único asociado al usuario
        Token token = new Token(usuario.getId());
        String tokenValor = token.getTokenValor();

        agregarToken(token);

        return tokenValor;
    }

    public void logout(String tokenValor) {
        eliminarToken(tokenValor);
    }


    public boolean validarToken(String tokenValor) {
        Usuario usuario = obtenerUsuarioByToken(tokenValor);
        return usuario != null;
    }

    private void agregarToken(Token token) {

        if (token == null || token.getTokenValor() == null) {
            throw new IllegalArgumentException("Token inválido.");
        }

        if (tokensActivos.containsKey(token.getTokenValor())) {
            throw new IllegalArgumentException("Token ya existente.");
        }

        tokensActivos.put(token.getTokenValor(), token);
    }

    private void eliminarToken(String tokenValor) {
        if (tokenValor != null) {
            tokensActivos.remove(tokenValor);
        }
    }

    private Usuario obtenerUsuarioByToken(String tokenValor) {

        if (tokenValor == null) {
            return null;
        }

        Token token = tokensActivos.get(tokenValor);

        if (token == null) {
            return null;
        }

        // Con el idUsuario guardado en el token, buscamos el usuario real
        return usuarios.get(token.getIdUsuario());
    }
}

	

