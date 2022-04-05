package romikusumabakti.romigram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import romikusumabakti.romigram.dto.LoginDto;
import romikusumabakti.romigram.model.Account;
import romikusumabakti.romigram.service.AccountService;
import romikusumabakti.romigram.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthenticationManager authenticationManager;
    AccountService accountService;
    JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AccountService accountService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            UserDetails userDetails = accountService.loadUserByUsername(loginDto.getUsername());
            String token = jwtService.create(userDetails.getUsername());
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/account")
    public Account account() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/logout")
    public Boolean logout() {
        return true;
    }

}