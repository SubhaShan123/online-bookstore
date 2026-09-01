package com.bookstore;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {


    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(final BookRepository repository){
        return args -> {
            repository.save(new Book("Clean Code", "Robert", 29.99));
            repository.save(new Book("Design Pattern", "Gamma", 39.99));
            repository.save(new Book("Refractoring", "Martin", 34.50));
        };
    }

}
