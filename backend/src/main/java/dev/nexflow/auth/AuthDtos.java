package dev.nexflow.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final class AuthDtos {
    private AuthDtos() {
    }

    public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {
    }

    public record RefreshRequest(@NotBlank String refreshToken) {
    }

    public record UserView(String name, String email, String role) {
    }

    public record AuthResponse(String accessToken, String refreshToken, UserView user) {
    }
}
