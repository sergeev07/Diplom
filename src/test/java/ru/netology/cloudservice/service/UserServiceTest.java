package ru.netology.cloudservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cloudservice.entity.AuthEntity;
import ru.netology.cloudservice.exceptions.AuthenticationException;
import ru.netology.cloudservice.repository.TokenRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    public static final String AUTH_TOKEN = "1715609701948-59257218-3c7a-40c6-9b88-7973ed93a21b";

    @Mock
    private TokenRepository mockTokenRepository;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockTokenRepository);
    }

    @Test
    void testGetUserIdByToken() {
        when(mockTokenRepository.findById(AUTH_TOKEN)).thenReturn(Optional.of(new AuthEntity(AUTH_TOKEN, 1L)));
        final long result = userServiceUnderTest.getUserIdByToken("Bearer " + AUTH_TOKEN);
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void testGetUserIdByToken_TokenRepositoryReturnsAbsent() {
        when(mockTokenRepository.findById(AUTH_TOKEN)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceUnderTest.getUserIdByToken("Bearer " + AUTH_TOKEN))
                .isInstanceOf(AuthenticationException.class);
    }
}
