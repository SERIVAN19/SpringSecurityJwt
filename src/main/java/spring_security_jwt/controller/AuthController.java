package spring_security_jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_security_jwt.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

        System.out.println("Entró al login");

        Authentication authentication =
                new UsernamePasswordAuthenticationToken( //Representa un usuario que quiere autenticarse
                        request.getEmail(),
                        request.getPassword()
                );

        Authentication authenticated =                   // Este metodo se encarga de autenticar al usuario, si el usuario no existe o la contraseña es incorrecta, lanzará una excepción
                authenticationManager.authenticate(
                        authentication
                );

        return "Usuario autenticado correctamente";
    }
}
