package com.bookstore.service;

import com.bookstore.model.Order;
import com.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DiscountCalculatorService discountCalculatorService;
    private final UserService userService;
    private final BookService bookService;

    public OrderService(OrderRepository orderRepository, DiscountCalculatorService discountCalculatorService, UserService userService, BookService bookService){
        this.orderRepository = orderRepository;
        this.discountCalculatorService = discountCalculatorService;
        this.userService = userService;
        this.bookService = bookService;
    }
    public Order processOrder(String username, List<Long> bookIds){
        userService.getUserByUsername(username);
        bookIds.forEach(bookService::getBookById);
        double calculatedTotal = discountCalculatorService.calculateBestTotalPrice(bookIds);
        Order order = new Order(username, calculatedTotal, bookIds);
        return orderRepository.save(order);

    }
}
