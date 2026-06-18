package com.college.controller;

import com.college.entity.Book;
import com.college.service.BookService;
import com.college.dto.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private BookService bookService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void createAndGetBook() throws Exception {
        Book b = new Book("1","Effective Java","Joshua Bloch","9780134685991");
        Mockito.when(bookService.addBook(any(Book.class))).thenReturn(b);
        Mockito.when(bookService.getBook("1")).thenReturn(b);

        BookDto dto = new BookDto();
        dto.setTitle("Effective Java");
        dto.setAuthor("Joshua Bloch");
        dto.setIsbn("9780134685991");

        mockMvc.perform(post("/api/books").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Effective Java"));

        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"));
    }
}
