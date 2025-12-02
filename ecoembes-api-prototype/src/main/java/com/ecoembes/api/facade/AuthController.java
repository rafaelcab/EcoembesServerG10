package com.ecoembes.api.facade;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecoembes.api.service.AuthService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService a) {
		this.authService = a;
	}

	@Operation(summary = "Login")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		return ResponseEntity.ok(authService.login(email, password));
	}

	@Operation(summary = "Logout")
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String auth) {
		authService.logout(auth.replace("Bearer ", ""));
		return ResponseEntity.noContent().build();
	}
}
