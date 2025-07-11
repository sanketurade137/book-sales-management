package com.accenture.booksales.repository;

import com.accenture.booksales.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Finds books by title (case-insensitive search)
    List<Book> findByTitleContainingIgnoreCase(String title);
}

