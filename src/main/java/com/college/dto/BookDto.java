package com.college.dto;

import jakarta.validation.constraints.NotBlank;

public class BookDto {
    private String id;

    @NotBlank(message = "title is required")
    private String title;

    private String author;
    private String isbn;

    public BookDto() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}
