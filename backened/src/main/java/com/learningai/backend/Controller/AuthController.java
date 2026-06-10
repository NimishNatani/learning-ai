package com.learningai.backend.controller;

import com.learningai.backend.dto.request.LoginRequest;
import com.learningai.backend.dto.request.RefreshTokenRequest;
import com.learningai.backend.dto.request.RegisterRequest;
import com.learningai.backend.dto.response.ApiResponse;
import com.learningai.backend.dto.response.AuthResponse;
import com.learningai.backend.entity.User;
import com.learningai.backend.exception.AppException;
import com.learningai.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login, refresh, logout")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("User registered successfully", response));
    }

    @GetMapping("/email-available")
    @Operation(summary = "Check whether an email can be used for registration")
    public ResponseEntity<ApiResponse<Boolean>> emailAvailable(@RequestParam String email) {
        if (!StringUtils.hasText(email)) {
            throw AppException.badRequest("Email is required");
        }

        boolean available = authService.isEmailAvailable(email);
        String message = available ? "Email is available" : "Email already registered";
        return ResponseEntity.ok(ApiResponse.ok(message, available));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.ok("Token refreshed", response));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout current user")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal User user) {
        if (user != null) {
            authService.logout(user.getEmail());
        }
        return ResponseEntity.ok(ApiResponse.ok("Logged out successfully", null));
    }
}
