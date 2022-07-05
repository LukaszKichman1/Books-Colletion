package com.example.Books.Collection.Controller;

import com.example.Books.Collection.Entity.Book;
import com.example.Books.Collection.Service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService=bookService;
    }


    @PostMapping("/addbook")
    public ResponseEntity<Book> addBook(@RequestParam String title, int rate) throws JsonProcessingException {
        return ResponseEntity.ok(bookService.addBook(title,rate));
    }

    @GetMapping("/one")
    public ResponseEntity<Optional> oneBook(@RequestParam int id){
        return ResponseEntity.ok(bookService.findById(id));
    }
}