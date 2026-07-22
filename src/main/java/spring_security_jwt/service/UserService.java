package spring_security_jwt.service;

import spring_security_jwt.dto.RegisterRequest;
import spring_security_jwt.entity.User;

public interface UserService {

    User register(RegisterRequest request);

}
