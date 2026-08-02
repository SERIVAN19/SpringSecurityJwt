package spring_security_jwt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring_security_jwt.entity.User;
import spring_security_jwt.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        if (user == null) {
            throw new UsernameNotFoundException(
                    "Usuario no encontrado"
            );
        }

        return org.springframework.security.core.userdetails.User //User de Spring Security
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

}