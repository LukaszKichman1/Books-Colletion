package com.example.Books.Collection.Service;

import com.example.Books.Collection.Entity.Token;
import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Exception.InvalidTokenException;
import com.example.Books.Collection.Exception.UserCanNotBeCreateException;
import com.example.Books.Collection.Exception.UserNotFoundException;
import com.example.Books.Collection.Repository.TokenRepository;
import com.example.Books.Collection.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenRepository tokenRepository;
    private MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public User save(User user) {
        Optional<User> userOptional = userRepository.findByLogin(user.getLogin());
        Optional<User> userOptional1 = userRepository.findByNickName(user.getNickName());
        if (userOptional.isPresent() ||
                userOptional1.isPresent() ||
                user.getLogin().isEmpty() ||
                user.getNickName().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getPassword().isEmpty()) {
            throw new UserCanNotBeCreateException("User have empty fields or nickname or login already taken");
        } else {
            User userBuilder = new User.Builder()
                    .nickName(user.getNickName())
                    .login(user.getLogin())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles("ROLE_USER")
                    .isEnabled(false)
                    .build();

            Token token = new Token(1 + (int) (Math.random() * 200));

            userBuilder.setToken(token);
            tokenRepository.save(token);
            mailService.SenderMail(user.getEmail(), "link : http://localhost:8080/user/activation   Your token=" + token.getValue(), "activation of your account");
            return userRepository.save(userBuilder);
        }
    }

    @Transactional
    public void activationUser(String login, int valueOfToken) {
        Optional<User> optionalUser = findByLogin(login);
        if (optionalUser.isPresent() && optionalUser.get().getToken().getValue() == valueOfToken) {
            optionalUser.get().setEnabled(true);
        } else {
            throw new InvalidTokenException("Wrong value of token");
        }
    }


    public Optional<User> findById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            throw new UserNotFoundException("We cant find user with that id");
        }
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            throw new UserNotFoundException("We cant find user with that login");
        }
    }

    public Optional<User> findByNickName(String nickName) {
        Optional<User> userOptional = userRepository.findByNickName(nickName);
        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            throw new UserNotFoundException("We cant find user with that nickname");
        }
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }


}



