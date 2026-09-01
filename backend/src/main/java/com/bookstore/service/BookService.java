package com.bookstore.service;

import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository ){
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllAvailableBook(){
        return bookRepository.findAll();
    }
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not found with id:"+id));
    }

    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

}
