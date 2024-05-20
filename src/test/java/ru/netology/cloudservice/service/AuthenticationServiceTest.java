package ru.netology.cloudservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cloudservice.dto.AuthRequest;
import ru.netology.cloudservice.dto.AuthResponse;
import ru.netology.cloudservice.entity.AuthEntity;
import ru.netology.cloudservice.entity.FileEntity;
import ru.netology.cloudservice.entity.UserEntity;
import ru.netology.cloudservice.exceptions.BadCredentialException;
import ru.netology.cloudservice.repository.TokenRepository;
import ru.netology.cloudservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    public static final String USER_NAME = "USER";
    public static final String PASSWORD = "123Qwe";
    public static final String AUTH_TOKEN = "1715609701948-59257218-3c7a-40c6-9b88-7973ed93a21b";
    public static final String NOT_EXISTING_USER = "USER2HACKER";

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private TokenRepository mockTokenRepository;

    private AuthenticationService authenticationServiceUnderTest;

    @BeforeEach
    void setUp() {
        authenticationServiceUnderTest = new AuthenticationService(mockUserRepository, mockTokenRepository);
    }

    @Test
    void testLogin() {
        final AuthRequest authRequest = new AuthRequest(USER_NAME, PASSWORD);
        final UserEntity user = new UserEntity();
        user.setId(1L);
        user.setLogin(authRequest.getLogin());
        user.setPassword(authRequest.getPassword());
        final FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);
        user.setFiles(List.of(fileEntity));
        final Optional<UserEntity> userEntity = Optional.of(user);
        when(mockUserRepository.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword())).thenReturn(userEntity);
        final AuthResponse result = authenticationServiceUnderTest.login(authRequest);
        verify(mockTokenRepository).save(new AuthEntity(result.getAuthToken(), user.getId()));
    }

    @Test
    void testLogin_UserRepositoryReturnsAbsent() {
        final AuthRequest authRequest = new AuthRequest(NOT_EXISTING_USER, PASSWORD);
        when(mockUserRepository.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authenticationServiceUnderTest.login(authRequest))
                .isInstanceOf(BadCredentialException.class);
    }

    @Test
    void testGetToken() {
        assertThat(AuthenticationService.getToken()).isNotEmpty();
    }

    @Test
    void testLogout() {
        final Optional<AuthEntity> authEntity = Optional.of(new AuthEntity(AUTH_TOKEN, 1L));
        when(mockTokenRepository.findById(AUTH_TOKEN)).thenReturn(authEntity);
        authenticationServiceUnderTest.logout("Bearer " + AUTH_TOKEN);
        verify(mockTokenRepository).deleteById(AUTH_TOKEN);
    }

}
