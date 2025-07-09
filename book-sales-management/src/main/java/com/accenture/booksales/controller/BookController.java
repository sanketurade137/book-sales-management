package com.accenture.booksales.controller;

import com.accenture.booksales.model.Book;
import com.accenture.booksales.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*") // Allow cross-origin requests from any domain
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Add a new book (used via REST API)
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Search books by title (case-insensitive)
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Delete book by ID
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
