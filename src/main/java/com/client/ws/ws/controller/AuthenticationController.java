package com.client.ws.ws.controller;

import com.client.ws.ws.Service.AuthenticationService;
import com.client.ws.ws.Service.UserDetailsService;
import com.client.ws.ws.dto.LoginDto;
import com.client.ws.ws.dto.TokenDto;
import com.client.ws.ws.dto.UserDetailsDto;
import com.client.ws.ws.model.redis.UserRecoveryCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.auth(dto));
    }

    @PostMapping(value = "/recovery-code/send",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendRecoveryCode(@RequestBody @Valid UserRecoveryCode dto) {
        userDetailsService.sendRecoveryCode(dto.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping(value = "/recovery-code/")
    public ResponseEntity<?> recoveryCodeIsValid(@RequestParam("recoveryCode") String recoveryCode,
                                                 @RequestParam("email") String email) {
        return ResponseEntity.status(HttpStatus.OK).body( userDetailsService.recoveryCodeIsValid(recoveryCode, email));
    }

    @PatchMapping(value = "/recovery-code/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePasswordByRecoveryCode(@RequestBody @Valid UserDetailsDto dto) {
        userDetailsService.updatePasswordByRecoveryCode(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
