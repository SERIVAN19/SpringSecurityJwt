package spring_security_jwt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //Permite crear objetos de la clase User de manera más sencilla y legible
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) //unique = true para que no se repitan los correos
    private String email;

    @Column(nullable = false)
    private String password;
}
