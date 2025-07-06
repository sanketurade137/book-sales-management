package com.accenture.booksales.repository;

import com.accenture.booksales.model.Book;
import com.accenture.booksales.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUsername(String username);

    long countByBook(Book book);
}
