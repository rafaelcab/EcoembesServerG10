package com.ecoembes.EcoembesServer.facade;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoembes.EcoembesServer.dto.CredencialesDTO;
import com.ecoembes.EcoembesServer.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Operaciones relacionadas con autenticación y gestión de sesiones")
public class AuthController {

    private final AuthService authService;

    // Constructor with parameters
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // -------------------- LOGIN --------------------

    @Operation(
        summary = "Login de usuario",
        description = "Recibe las credenciales de un usuario y devuelve un token si son correctas",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Login correcto, se devuelve el token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Credenciales incorrectas o usuario no encontrado")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User's credentials",
            required = true
        )
        @RequestBody CredencialesDTO credentials) {

        Optional<String> token = authService.login(
            credentials.getEmail(),
            credentials.getContrasenya()
        );

        if (token.isPresent()) {
            return new ResponseEntity<>(token.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // -------------------- LOGOUT --------------------

    @Operation(
            summary = "Logout de usuario",
            description = "Invalida un token de sesión y cierra la sesión del usuario",
            responses = {
                @ApiResponse(responseCode = "204", description = "No Content: Logout correcto"),
                @ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido")
            }
        )
        @PostMapping("/logout")
    public ResponseEntity<Void> logout(
	    @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Token de autenticación en texto plano",
	        required = true
	    )
	    @RequestBody String tokenBody) {

	    // Limpieza por si Swagger u otro cliente manda el token con comillas o espacios
	    String token = tokenBody.replace("\"", "").trim();

	    Optional<Boolean> result = authService.logout(token);

	    if (result.isPresent() && result.get()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	}
}

