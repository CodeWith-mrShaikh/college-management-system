package com.college.service;

import com.college.entity.Book;
import com.college.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceTest {

    @Test
    void addAndGetBook() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        BookService service = new BookService(repo);

        Book b = new Book("1", "Title", "Author", "ISBN1");
        Mockito.when(repo.save(b)).thenReturn(b);
        Mockito.when(repo.findById("1")).thenReturn(Optional.of(b));

        Book saved = service.addBook(b);
        assertEquals("1", saved.getId());

        Book fetched = service.getBook("1");
        assertEquals("Title", fetched.getTitle());
    }
}
