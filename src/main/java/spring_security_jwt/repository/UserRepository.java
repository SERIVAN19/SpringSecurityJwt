package spring_security_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_security_jwt.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Query method
    // Spring Data JPA genera automáticamente:
    //SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
}
