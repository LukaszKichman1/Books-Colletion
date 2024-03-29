package com.example.Books.Collection.Service;

import com.example.Books.Collection.Dto.BookDto;
import com.example.Books.Collection.Dto.BooksDto;
import com.example.Books.Collection.Entity.Book;
import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.lang.String.join;

@Service
public class BookService {

    private BookRepository bookRepository;
    private UserService userService;
    private RestTemplate restTemplate = new RestTemplate();
    Logger logger = LoggerFactory.getLogger(UserService.class);

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
            logger.trace("User    " + userOptional.get().getNickName() + "add book" + bookBuilder.getTitle());
            return bookRepository.save(bookBuilder);
        } else {
            return null;
        }

    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    public List<BooksDto> listOfBooks() throws JsonProcessingException {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userService.findByNickName(currentUser);
        if (userOptional.isPresent()) {
            String url = "https://wolnelektury.pl/api/books/";
            String result = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(result, new TypeReference<>() {
            });
        } else {
            return null;
        }

    }

    @Transactional
    public void deleteOneBookById(int id) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userService.findByNickName(currentUser);
        Optional<Book> bookOptional = findById(id);
        if(userOptional.isPresent() && bookOptional.isPresent()){
            logger.trace("User    " + userOptional.get().getNickName() + "delete book" + bookOptional.get().getTitle());
            userOptional.get().getBooks().remove(bookOptional.get());
        }
    }

    @Transactional
    public void deleteAllBooks() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userService.findByNickName(currentUser);
        userOptional.ifPresent(user -> user.getBooks().clear());
        logger.trace("User    " + userOptional.get().getNickName() + "delete all his books");
    }
}
