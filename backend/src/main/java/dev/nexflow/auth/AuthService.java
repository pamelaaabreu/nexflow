package dev.nexflow.auth;

import dev.nexflow.common.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
                       UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            UserDetails details = userDetailsService.loadUserByUsername(request.email());
            return response(details);
        } catch (AuthenticationException ex) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }

    public AuthDtos.AuthResponse refresh(AuthDtos.RefreshRequest request) {
        try {
            String email = jwtService.extractUsername(request.refreshToken());
            if (!"refresh".equals(jwtService.extractType(request.refreshToken()))) throw new IllegalArgumentException();
            UserDetails details = userDetailsService.loadUserByUsername(email);
            if (!jwtService.isValid(request.refreshToken(), details)) throw new IllegalArgumentException();
            return response(details);
        } catch (Exception ex) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }
    }

    private AuthDtos.AuthResponse response(UserDetails details) {
        UserEntity user = userRepository.findByEmailIgnoreCase(details.getUsername()).orElseThrow();
        return new AuthDtos.AuthResponse(jwtService.accessToken(details), jwtService.refreshToken(details),
                new AuthDtos.UserView(user.getName(), user.getEmail(), user.getRole().name()));
    }
}
