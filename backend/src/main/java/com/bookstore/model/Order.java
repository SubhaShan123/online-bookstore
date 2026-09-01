package com.bookstore.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerUsername;
    private Double totalAmount;
    @ElementCollection
    private List<Long> bookIds;

    public Order(){}

    public Order(String customerUsername, Double totalAmount, List<Long> bookIds) {
        this.customerUsername = customerUsername;
        this.totalAmount = totalAmount;
        this.bookIds = bookIds;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }
}
