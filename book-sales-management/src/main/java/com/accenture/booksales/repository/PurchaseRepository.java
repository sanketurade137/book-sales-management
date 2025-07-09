package com.accenture.booksales.repository;

import com.accenture.booksales.model.Book;
import com.accenture.booksales.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository interface for handling Purchase-related database operations
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // Fetches all purchases made by a specific username
    List<Purchase> findByUsername(String username);

    // Returns the total number of purchases made for a specific book
    long countByBook(Book book);
}
