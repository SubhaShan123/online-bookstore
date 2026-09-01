package com.bookstore.model;
@jakarta.persistence.Entity
public class OrderItem {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String bookTitle;
    private Integer quantity;
    private Double price;

    public OrderItem(){}

    public OrderItem(String bookTitle, Integer quantity, Double price) {
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
