package com.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Long bookId;
    private Integer quantity;

    public CartItem() { }

    public CartItem(String username, Long bookId, Integer quantity) {
        this.username = username;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Long getBookId() { return bookId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
