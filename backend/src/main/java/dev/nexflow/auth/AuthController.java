package dev.nexflow.auth;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }
    @PostMapping("/login") public AuthDtos.AuthResponse login(@Valid @RequestBody AuthDtos.LoginRequest request) { return service.login(request); }
    @PostMapping("/refresh") public AuthDtos.AuthResponse refresh(@Valid @RequestBody AuthDtos.RefreshRequest request) { return service.refresh(request); }
}
