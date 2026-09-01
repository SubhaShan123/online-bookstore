package com.bookstore;

import com.bookstore.controller.BookController;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest {

    @Test
    void getAvailableBooksReturnsBooksFromService() throws Exception {
        BookService bookService = mock(BookService.class);
        when(bookService.getAllAvailableBook()).thenReturn(List.of(
                new Book("Clean Code", "Robert C. Martin", 29.99),
                new Book("Design Patterns", "Gang of Four", 39.99)
        ));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();

        mockMvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[1].author").value("Gang of Four"));

        verify(bookService).getAllAvailableBook();
    }

}
