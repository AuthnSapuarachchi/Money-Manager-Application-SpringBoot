package com.authnMoneyManager.moneyManager.controller;

import com.authnMoneyManager.moneyManager.dto.AuthDTO;
import com.authnMoneyManager.moneyManager.dto.ProfileDTO;
import com.authnMoneyManager.moneyManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO registeredProfile = profileService.registerProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token) {
        boolean isActivated = profileService.activateProfile(token);
        if (isActivated) {
            return ResponseEntity.ok("Profile activated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found or already used");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDTO) {
        try {
            if (!profileService.isAccountActive(authDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "message", "Account is not active.Please activate your account first."));
            }
            Map<String, Object> response = profileService.authenticateAndGenerateToken(authDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", e.getMessage()));
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Test Successful";
    }

    // Development only: Get activation token by email
    @GetMapping("/dev/activation-token")
    public ResponseEntity<Map<String, String>> getActivationToken(@RequestParam String email) {
        return profileService.getActivationTokenForDev(email)
                .map(token -> ResponseEntity.ok(Map.of(
                        "email", email,
                        "activationToken", token,
                        "activationLink", "http://localhost:8080/api/v1.0/activate?token=" + token)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found or already activated")));
    }

}
