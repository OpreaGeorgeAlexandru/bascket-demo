package goprea.software.backend.service;

import goprea.software.backend.dto.UserLoginDTO;
import goprea.software.backend.dto.UserRegisterDTO;
import goprea.software.backend.exception.ExceptionTranslationStatus;
import goprea.software.backend.exception.types.AlreadyExistsRestException;
import goprea.software.backend.jwt.JwtTokenProvider;
import goprea.software.backend.model.User;
import goprea.software.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = jwtTokenProvider;
    }

    public User register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new AlreadyExistsRestException(userRegisterDTO.getUsername());
        }
        User user = new User();
        // TODO user mapstruct instead
        user.setName(userRegisterDTO.getName());
        user.setUsername(userRegisterDTO.getUsername());
        user.setToken(null);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        return userRepository.save(user);
    }

    public User login(UserLoginDTO userLoginDTO){
        final Optional<User> userOptional = userRepository.findByUsername(userLoginDTO.getUsername());

        if (userOptional.isPresent() && passwordEncoder.matches(userLoginDTO.getPassword(), userOptional.get().getPassword())) {
            User user = userOptional.get();
            final String token = tokenProvider.generateToken(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
            user.setToken(token);
            user.setPassword(null);

            return user;
        } else {
            throw new BadCredentialsException(ExceptionTranslationStatus.INVALID_CREDENTIALS.name());
        }
    }
}
