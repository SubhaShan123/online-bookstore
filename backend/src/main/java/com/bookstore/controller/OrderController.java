package com.bookstore.controller;
import com.bookstore.model.Order;
import com.bookstore.service.OrderService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam @NotBlank String username, @RequestBody @NotEmpty List<@NotNull Long> bookIds){
        Order createdOrder = orderService.processOrder(username, bookIds);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }


}
