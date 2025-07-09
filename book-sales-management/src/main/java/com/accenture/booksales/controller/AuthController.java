package com.accenture.booksales.controller;

import com.accenture.booksales.model.AppUser;
import com.accenture.booksales.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AppUserRepository userRepository;

    // Show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute AppUser appUser, Model model) {
        appUser.setRole("user"); // Default role is 'user'

        // Check if username already exists
        if (userRepository.findByUsername(appUser.getUsername()) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        userRepository.save(appUser); // Save user to DB
        model.addAttribute("success", "Registration successful. Please login.");
        return "login";
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Handle login logic
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        AppUser user = userRepository.findByUsername(username);

        // Validate credentials
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // Redirect based on role
            return "admin".equals(user.getRole()) ? "redirect:/books/view" : "redirect:/books/user";
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    // Handle logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear session
        return "redirect:/login";
    }
}
