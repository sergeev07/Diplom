package ru.netology.cloudservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.netology.cloudservice.entity.AuthEntity;
import ru.netology.cloudservice.exceptions.AuthenticationException;
import ru.netology.cloudservice.repository.TokenRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final TokenRepository tokenRepository;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    public long getUserIdByToken(String authToken) {
        final Optional<AuthEntity> token = tokenRepository.findById(authToken.split(" ")[1].trim());
        if (token.isEmpty()) {
            logger.error("Не удалось получить USER_ID по токену {}", authToken);
            throw new AuthenticationException();
        }
        return token.get().getUserId();
    }

}
