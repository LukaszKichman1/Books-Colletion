package com.example.Books.Collection.Service;

import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Repository.BookRepository;
import com.example.Books.Collection.Repository.TokenRepository;
import com.example.Books.Collection.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BookServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private BookRepository bookRepository;
    @Captor
    private ArgumentCaptor<User> argumentCaptor;
    private BookService underTest;


    @BeforeEach
    @Deprecated
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new BookService(bookRepository, userService);
    }
}
