package com.bookstore;

import com.bookstore.service.DiscountCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountCalculatorServiceTest {

    private DiscountCalculatorService discountCalculatorService;

    @BeforeEach
    void setUp() {
        discountCalculatorService = new DiscountCalculatorService();
    }

    @Test
    void calculateBestTotalPriceReturnsZeroForNullOrEmptyLists() {
        assertEquals(0.0, discountCalculatorService.calculateBestTotalPrice(null));
        assertEquals(0.0, discountCalculatorService.calculateBestTotalPrice(List.of()));
    }

    @Test
    void calculateBestTotalPriceAppliesDiscountForDistinctBooks() {
        assertEquals(95.0, discountCalculatorService.calculateBestTotalPrice(List.of(1L, 2L)));
        assertEquals(135.0, discountCalculatorService.calculateBestTotalPrice(List.of(1L, 2L, 3L)));
    }

    @Test
    void calculateBestTotalPriceFindsBestGroupingForDuplicateBooks() {
        assertEquals(145.0, discountCalculatorService.calculateBestTotalPrice(List.of(1L, 1L, 2L)));
        assertEquals(190.0, discountCalculatorService.calculateBestTotalPrice(List.of(1L, 1L, 2L, 2L)));
    }
}
