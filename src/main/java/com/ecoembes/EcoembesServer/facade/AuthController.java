package com.ecoembes.EcoembesServer.facade;

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
			@ApiResponse(responseCode = "401", description = "Unauthorized: Credenciales incorrectas o usuario no encontrado"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
		}
	)
	@PostMapping("/login")
	public ResponseEntity<String> login(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Credenciales del usuario (email y contraseña/hash)",
			required = true
		)
		@RequestBody CredencialesDTO credenciales) {

		try {
			String token = authService.login(credenciales.getEmail(), credenciales.getContrasenya());	
			return new ResponseEntity<>(token, HttpStatus.OK);
			
		} catch (RuntimeException e) {
			switch (e.getMessage()) {
				case "User not found":
				case "Invalid credentials":
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				default:
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	// -------------------- LOGOUT --------------------

	@Operation(
		summary = "Logout de usuario",
		description = "Invalida un token de sesión y cierra la sesión del usuario",
		responses = {
			@ApiResponse(responseCode = "204", description = "No Content: Logout correcto"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
		}
	)
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Token de autenticación en texto plano",
			required = true
		)
		@RequestBody String token) {

		try {
			boolean valido = authService.validarToken(token);

			if (!valido) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			authService.logout(token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
