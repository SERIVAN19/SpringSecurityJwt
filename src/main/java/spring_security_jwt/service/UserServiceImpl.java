package spring_security_jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_security_jwt.dto.RegisterRequest;
import spring_security_jwt.entity.User;
import spring_security_jwt.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User register(RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        return userRepository.save(user);
    }
}
