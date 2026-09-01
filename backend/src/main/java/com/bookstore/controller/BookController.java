package com.bookstore.controller;
import com.bookstore.model.*;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<List<Book>> getAvailableBooks(){
        List<Book> books = bookService.getAllAvailableBook();
        return ResponseEntity.ok(books);
    }

}
