package ru.netology.cloudservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.netology.cloudservice.dto.AuthRequest;
import ru.netology.cloudservice.dto.AuthResponse;
import ru.netology.cloudservice.entity.AuthEntity;
import ru.netology.cloudservice.entity.UserEntity;
import ru.netology.cloudservice.exceptions.BadCredentialException;
import ru.netology.cloudservice.repository.TokenRepository;
import ru.netology.cloudservice.repository.UserRepository;

import java.sql.Timestamp;
import java.util.UUID;


@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthResponse login(AuthRequest authRequest) {
        final String loginFromRequest = authRequest.getLogin();
        final String passwordFromRequest = authRequest.getPassword();
        final UserEntity user = userRepository.findByLoginAndPassword(loginFromRequest, passwordFromRequest)
                .orElseThrow(() -> new BadCredentialException("Пользователь не найден"));

        final String authToken = getToken();
        tokenRepository.save(new AuthEntity(authToken, user.getId()));
        logger.info("Для пользователя " + loginFromRequest + " сгенерирован токен " + authToken);
        return new AuthResponse(authToken);
    }

    public static String getToken() {
        StringBuilder token = new StringBuilder();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long currentTimeInMilisecond = timestamp.getTime();
        return token.append(currentTimeInMilisecond).append("-")
                .append(UUID.randomUUID()).toString();
    }

    public void logout(String authToken) {
        tokenRepository.findById(authToken.split(" ")[1].trim())
                .map(AuthEntity::getToken).ifPresent(tokenRepository::deleteById);
    }

}