package com.example.libraryProject.controller;

import com.example.libraryProject.model.User;
import com.example.libraryProject.repository.UserRepository;
import com.example.libraryProject.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor


public class UserController {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtService jwt;


    //register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body("Kullanıcı adı zaten var");
        }

        //şifre hashleniyo
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Kayıt başarılı");

    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody User loginUser){
        return userRepository.findByUsername(loginUser.getUsername())

                .filter(user -> passwordEncoder.matches(loginUser.getPassword(), user.getPassword()))
                .map(user -> jwt.generateToken(user.getUsername()))
                .map(token -> ResponseEntity.ok(Map.of("token", token)))
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Hatalı giriş")));
    }


    //login


}
