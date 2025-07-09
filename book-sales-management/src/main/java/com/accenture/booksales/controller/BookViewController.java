package com.accenture.booksales.controller;

import java.util.List;
import com.accenture.booksales.model.Book;
import com.accenture.booksales.model.Purchase;
import com.accenture.booksales.repository.BookRepository;
import com.accenture.booksales.repository.PurchaseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookViewController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    // Admin: View all books or search by title
    @GetMapping("/books/view")
    public String viewBooks(@RequestParam(required = false) String keyword,
                            HttpSession session,
                            Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("admin")) return "redirect:/login";

        List<Book> books = (keyword != null && !keyword.isEmpty())
                ? bookRepository.findByTitleContainingIgnoreCase(keyword)
                : bookRepository.findAll();

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("role", role);
        return "book-list";
    }

    // User: View all books or search by title
    @GetMapping("/books/user")
    public String userBookView(@RequestParam(required = false) String keyword,
                               HttpSession session,
                               Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("user")) return "redirect:/login";

        List<Book> books = (keyword != null && !keyword.isEmpty())
                ? bookRepository.findByTitleContainingIgnoreCase(keyword)
                : bookRepository.findAll();

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("role", role);
        return "book-list";
    }

    // Admin: Show add book form
    @GetMapping("/books/add")
    public String showAddForm(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        model.addAttribute("book", new Book());
        return "book-form";
    }

    // Admin: Add book to repository
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        bookRepository.save(book);
        return "redirect:/books/view";
    }

    // Admin: Delete book after checking it's not purchased
    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        long purchaseCount = purchaseRepository.countByBook(book);
        if (purchaseCount > 0) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete. This book has been purchased.");
            return "redirect:/books/view";
        }

        bookRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Book deleted successfully.");
        return "redirect:/books/view";
    }

    // Admin: Show edit book form
    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        model.addAttribute("book", book);
        return "book-form";
    }

    // Admin: Update existing book
    @PostMapping("/books/update")
    public String updateBook(@ModelAttribute Book book, HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) return "redirect:/login";
        bookRepository.save(book);
        return "redirect:/books/view";
    }

    // User: Buy a book and save purchase record
    @GetMapping("/books/buy/{id}")
    public String buyBook(@PathVariable Long id, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (!"user".equals(role)) return "redirect:/login";

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        Purchase purchase = new Purchase();
        purchase.setBook(book);
        purchase.setUsername(username);
        purchase.setTimestamp(LocalDateTime.now());
        purchaseRepository.save(purchase);

        model.addAttribute("book", book);
        return "buy-success";
    }

    // User: View their purchase history
    @GetMapping("/books/purchases")
    public String viewMyPurchases(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");
        if (!"user".equals(role)) return "redirect:/login";

        List<Purchase> purchases = purchaseRepository.findByUsername(username);
        model.addAttribute("purchases", purchases);
        return "purchase-history";
    }

    // Admin: Dashboard showing total books and purchases
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model, HttpSession session) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("role", session.getAttribute("role"));

        model.addAttribute("totalBooks", bookRepository.count());
        model.addAttribute("totalPurchases", purchaseRepository.count());

        return "admin-dashboard";
    }

    // Admin: View all purchase records
    @GetMapping("/admin/purchases")
    public String viewPurchases(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) return "redirect:/login";

        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("purchases", purchases);
        return "purchase-list";
    }
}
