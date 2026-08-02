package spring_security_jwt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring_security_jwt.dto.RegisterRequest;
import spring_security_jwt.entity.User;
import spring_security_jwt.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping("/saludo")
    public String saludo() {
        return "¡Hola, mundo!";
    }
}
