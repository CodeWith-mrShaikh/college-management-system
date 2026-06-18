package com.college.controller;

import com.college.dto.BookDto;
import com.college.entity.Book;
import com.college.mapper.DtoMapper;
import com.college.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) { this.bookService = bookService; }

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto dto) {
        Book saved = bookService.addBook(DtoMapper.toBookEntity(dto));
        BookDto out = DtoMapper.toBookDto(saved);
        return ResponseEntity.created(URI.create("/api/books/" + out.getId())).body(out);
    }

    @GetMapping
    public List<BookDto> list() { return bookService.getAll().stream().map(DtoMapper::toBookDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public BookDto get(@PathVariable String id) { return DtoMapper.toBookDto(bookService.getBook(id)); }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable String id, @Valid @RequestBody BookDto dto) {
        Book e = DtoMapper.toBookEntity(dto);
        e.setId(id);
        return DtoMapper.toBookDto(bookService.updateBook(e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
