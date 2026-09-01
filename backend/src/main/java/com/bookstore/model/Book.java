package com.bookstore.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Book title cannot be blank")
    private String title;
    @NotBlank(message = "Book author cannot be blank")
    private String author;

    @NotNull(message = "Price is required")
    @Min(value=0, message = "Price must be non-negative")
    private Double price;


    public  Book(){}
    public Book(String title, String author, Double price) {

        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Double getPrice() {
        return price;
    }

}
