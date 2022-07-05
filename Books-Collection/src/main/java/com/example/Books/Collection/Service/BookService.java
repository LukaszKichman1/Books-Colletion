package com.example.Books.Collection.Service;

import com.example.Books.Collection.Dto.BookDto;
import com.example.Books.Collection.Entity.Book;
import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.lang.String.join;

@Service
public class BookService {

    private BookRepository bookRepository;
    private UserService userService;
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public Book addBook(String title, int rate) throws JsonProcessingException {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userService.findByNickName(currentUser);
        if (userOptional.isPresent()) {
            String url = "https://wolnelektury.pl/api/books/" + title;
            BookDto bookDto = restTemplate.getForObject(url, BookDto.class);
            Book bookBuilder = new Book.Builder()
                    .author(bookDto.getAuthors().stream().findFirst().get().getName())
                    .kind(bookDto.getKinds().stream().findFirst().get().getName())
                    .user(userOptional.get())
                    .title(title)
                    .rate(rate)
                    .build();
            userOptional.get().getBooks().add(bookBuilder);
            bookBuilder.setUser(userOptional.get());
            return bookRepository.save(bookBuilder);
        } else {
            return null;
        }

    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }
}
