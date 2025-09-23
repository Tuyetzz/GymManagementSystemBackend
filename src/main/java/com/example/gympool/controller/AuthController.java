package com.example.gympool.controller;

import com.example.gympool.dto.LoginRequest;
import com.example.gympool.dto.LoginResponse;
import com.example.gympool.dto.RegisterRequest;
import com.example.gympool.entity.RefreshToken;
import com.example.gympool.repository.RefreshTokenRepository;
import com.example.gympool.security.JwtTokenProvider;
import com.example.gympool.service.AuthService;
import com.example.gympool.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email của user
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // ROLE_MEMBER / ROLE_STAFF...

        return ResponseEntity.ok("User: " + email + ", Role: " + role);
    }

    @PostMapping("/register-employee")
    public ResponseEntity<String> registerEmployee(@RequestBody RegisterRequest request) {
        authService.registerEmployee(request);
        return ResponseEntity.ok("Employee registered successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");

        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        String newAccessToken = jwtTokenProvider.generateToken(
                refreshToken.getUser().getEmail(),
                refreshToken.getUser().getRole()
        );

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }
}
