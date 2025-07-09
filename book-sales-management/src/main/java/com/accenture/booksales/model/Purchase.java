package com.accenture.booksales.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Marks this class as a JPA entity mapped to a "purchase" table
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for each purchase record

    private String username; // Username of the user who purchased the book

    @ManyToOne // Many purchases can be linked to one book
    @JoinColumn(name = "book_id") // Defines the foreign key column for the Book entity
    private Book book;

    private LocalDateTime timestamp; // Date and time when the purchase occurred

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
