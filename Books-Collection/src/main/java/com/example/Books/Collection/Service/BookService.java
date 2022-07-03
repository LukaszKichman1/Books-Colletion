package com.example.Books.Collection.Service;

import com.example.Books.Collection.Entity.Book;
import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private UserService userService;

    @Autowired
    public BookService(BookRepository bookRepository,UserService userService){
        this.bookRepository=bookRepository;
        this.userService=userService;
    }
    //testowe
    public Book save(Book book){
        Optional<User> userOptional=userService.findById(1);
        userOptional.get().addBook(book);
        book.setUser(userOptional.get());
        return bookRepository.save(book);
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }
}
