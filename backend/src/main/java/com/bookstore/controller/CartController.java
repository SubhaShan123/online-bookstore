package com.bookstore.controller;

import com.bookstore.dto.CartItemRequest;
import com.bookstore.model.CartItem;
import com.bookstore.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/cart/{username}/items")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@PathVariable @NotBlank String username) {
        return ResponseEntity.ok(cartService.getCart(username));
    }

    @PostMapping
    public ResponseEntity<CartItem> addItem(@PathVariable @NotBlank String username, @Valid @RequestBody CartItemRequest request) {
        return new ResponseEntity<>(cartService.addItem(username, request.getBookId(), request.getQuantity()), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<CartItem> updateQuantity(@PathVariable @NotBlank String username, @PathVariable Long bookId, @Valid @RequestBody CartItemRequest request) {
        if (!bookId.equals(request.getBookId())) {
            throw new IllegalArgumentException("Book id in the URL must match the request body");
        }
        return ResponseEntity.ok(cartService.updateQuantity(username, bookId, request.getQuantity()));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> removeItem(@PathVariable @NotBlank String username, @PathVariable Long bookId) {
        cartService.removeItem(username, bookId);
        return ResponseEntity.noContent().build();
    }
}
