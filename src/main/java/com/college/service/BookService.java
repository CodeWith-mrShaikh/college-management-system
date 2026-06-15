package com.college.service;

import com.college.entity.Book;
import com.college.exception.ResourceNotFoundException;
import com.college.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repo;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookService(BookRepository repo) { this.repo = repo; }

    public Book addBook(Book b) { Book saved = repo.save(b); logger.info("Book added: {}", saved.getId()); return saved; }
    public Book getBook(String id) {
        Objects.requireNonNull(id, "Book id must not be null");
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID " + id));
    }

    public List<Book> getAll() { return repo.findAll(); }

    public Book updateBook(Book b) {
        Objects.requireNonNull(b, "Book must not be null");
        Objects.requireNonNull(b.getId(), "Book id must not be null");
        if (!repo.existsById(b.getId())) throw new ResourceNotFoundException("Book not found with ID " + b.getId());
        Book updated = repo.save(b);
        logger.info("Book updated: {}", updated.getId());
        return updated;
    }

    public void deleteBook(String id) {
        Objects.requireNonNull(id, "Book id must not be null");
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Book not found with ID " + id);
        repo.deleteById(id);
        logger.info("Book deleted: {}", id);
    }
}
