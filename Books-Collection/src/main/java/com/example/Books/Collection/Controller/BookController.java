package com.example.Books.Collection.Controller;

import com.example.Books.Collection.Entity.Book;
import com.example.Books.Collection.Service.BookService;
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


    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.save(book));
    }

    @GetMapping("/one")
    public ResponseEntity<Optional> oneBook(@RequestParam int id){
        return ResponseEntity.ok(bookService.findById(id));
    }
}