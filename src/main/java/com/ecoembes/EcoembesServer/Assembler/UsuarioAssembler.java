package com.ecoembes.EcoembesServer.Assembler;

import org.springframework.stereotype.Component;

import com.ecoembes.EcoembesServer.dto.CredencialesDTO;
import com.ecoembes.EcoembesServer.entity.Usuario;

@Component
public class UsuarioAssembler {

    public CredencialesDTO createCredencialesDTO(Usuario usuario) {
        if (usuario == null) return null;

        return new CredencialesDTO(
                usuario.getEmail(),
                usuario.getContrasenya()
        );
    }

    public Usuario updateUsuario(CredencialesDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenya(dto.getContrasenya());

        return usuario;
    }
}
