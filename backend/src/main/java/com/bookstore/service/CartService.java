package com.bookstore.service;

import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.CartItem;
import com.bookstore.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final BookService bookService;

    public CartService(CartItemRepository cartItemRepository, UserService userService, BookService bookService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<CartItem> getCart(String username) {
        userService.getUserByUsername(username);
        return cartItemRepository.findByUsername(username);
    }

    public CartItem addItem(String username, Long bookId, Integer quantity) {
        userService.getUserByUsername(username);
        bookService.getBookById(bookId);
        CartItem item = cartItemRepository.findByUsernameAndBookId(username, bookId)
                .orElse(new CartItem(username, bookId, 0));
        item.setQuantity(item.getQuantity() + quantity);
        return cartItemRepository.save(item);
    }

    public CartItem updateQuantity(String username, Long bookId, Integer quantity) {
        CartItem item = findItem(username, bookId);
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public void removeItem(String username, Long bookId) {
        cartItemRepository.delete(findItem(username, bookId));
    }

    private CartItem findItem(String username, Long bookId) {
        userService.getUserByUsername(username);
        return cartItemRepository.findByUsernameAndBookId(username, bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found for book id: " + bookId));
    }
}
