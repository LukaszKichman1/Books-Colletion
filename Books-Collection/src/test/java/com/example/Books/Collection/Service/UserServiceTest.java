package com.example.Books.Collection.Service;

import com.example.Books.Collection.Entity.Token;
import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Exception.InvalidTokenException;
import com.example.Books.Collection.Exception.UserCanNotBeActivationException;
import com.example.Books.Collection.Exception.UserCanNotBeCreateException;
import com.example.Books.Collection.Exception.UserNotFoundException;
import com.example.Books.Collection.Repository.TokenRepository;
import com.example.Books.Collection.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private MailService mailService;
    @Captor
    private ArgumentCaptor<User> argumentCaptor;
    private UserService underTest;

    @BeforeEach
    @Deprecated
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new UserService(userRepository, passwordEncoder, tokenRepository, mailService);
    }

    @Test
    @Deprecated
    void itShouldSaveNewUser() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        given(userRepository.findByNickName("nick")).willReturn(Optional.empty());

        //when
        underTest.save(user);

        //then
        then(userRepository).should().save(argumentCaptor.capture());
        User argumentCaptorValue = argumentCaptor.getValue();
        assertThat(argumentCaptorValue.getNickName()).isEqualTo("nick");
        assertThat(argumentCaptorValue.getLogin()).isEqualTo("login");
        assertThat(argumentCaptorValue.getEmail()).isEqualTo("email@gmail.com");
        assertThat(argumentCaptorValue.getRoles()).isEqualTo("ROLE_USER");
        assertThat(argumentCaptorValue.isEnabled()).isFalse();
    }

    @Test
    void itShouldNotSaveUserBecauseThisUserAlreadyExist() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();


        given(userRepository.findByNickName("nick")).willReturn(Optional.of(user));

        //when
        //then
        assertThatThrownBy(() -> underTest.save(user))
                .isExactlyInstanceOf(UserCanNotBeCreateException.class)
                .hasMessageContaining("User have empty fields or nickname or login already taken");
    }

    @Test
    void itShouldActivateUser() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        Token token = new Token(20);
        user.setToken(token);
        tokenRepository.save(token);

        given(userRepository.findByLogin(user.getLogin())).willReturn(Optional.of(user));

        //when
        underTest.activationUser(user.getLogin(), user.getToken().getValue());

        //then
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    void itShouldNotActivateUserBecauseThisUserDoNotExist() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        Token token = new Token(20);
        user.setToken(token);
        tokenRepository.save(token);

        given(userRepository.findByLogin(user.getLogin())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.activationUser(user.getLogin(), user.getToken().getValue()))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("We cant find user with that login");
    }

    @Test
    void itShouldNotActivatieUserBecauseValueOfTokenIsInvalid() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();


        Token token = new Token(20);
        user.setToken(token);
        tokenRepository.save(token);

        given(userRepository.findByLogin(user.getLogin())).willReturn(Optional.of(user));


        //when
        //then
        assertThatThrownBy(() -> underTest.activationUser(user.getLogin(), 70))
                .isExactlyInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("Wrong value of token");
    }

    @Test
    void itShouldReturnUserById() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        given(userRepository.findById(user.getId_user())).willReturn(Optional.of(user));

        //when
        //then
        Optional<User> userOptional = underTest.findById(user.getId_user());
        assertThat(userOptional.isPresent()).isTrue();
        assertThat(userOptional.get().getNickName()).isEqualTo("nick");
        assertThat(userOptional.get().getLogin()).isEqualTo("login");
        assertThat(userOptional.get().getEmail()).isEqualTo("email@gmail.com");

    }

    @Test
    void itShouldNotReturnUserByIdBecauseThisUserDoNotExist() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        given(userRepository.findById(user.getId_user())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(user.getId_user()))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("We cant find user with that id");
    }

    @Test
    @Deprecated
    void itShouldReturnUserByLogin() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        given(userRepository.findByLogin(user.getLogin())).willReturn(Optional.of(user));

        //when
        //then
        Optional<User> userOptional = underTest.findByLogin(user.getLogin());
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualToComparingFieldByField(user);
                });
    }

    @Test
    void itShouldNotReturnUserByLoginBecauseThisUserDoNotExist() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        given(userRepository.findByLogin(user.getLogin())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.findByLogin(user.getLogin()))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("We cant find user with that login");
    }

    @Test
    @Deprecated
    void itShouldReturnUserByNickname() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        given(userRepository.findByNickName(user.getNickName())).willReturn(Optional.of(user));

        //when
        //then
        Optional<User> userOptional = underTest.findByNickName(user.getNickName());
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualToComparingFieldByField(user);
                });
    }

    @Test
    void itShouldNotReturnUserByNicknameBecauseThisUserDoNotExist() {
        //given
        User user = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        given(userRepository.findByNickName(user.getNickName())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.findByNickName(user.getNickName()))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("We cant find user with that nickname");
    }

    @Test
    void itShouldReturnListOfAllUsers() {
        //given
        User user1 = new User.Builder()
                .nickName("nick")
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        User user2 = new User.Builder()
                .nickName("nick2")
                .login("login2")
                .password("password2")
                .email("email2@gmail.com")
                .roles("ROLE_USER")
                .isEnabled(false)
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        //when
        when(userRepository.findAll()).thenReturn(userList);

        //then
        List<User> userList1 = underTest.findAll();
        assertThat(userList).isSameAs(userList1);
    }
}
