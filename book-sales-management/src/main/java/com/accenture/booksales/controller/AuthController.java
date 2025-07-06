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

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute AppUser appUser, Model model) {
        appUser.setRole("user");

        if (userRepository.findByUsername(appUser.getUsername()) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        userRepository.save(appUser);
        model.addAttribute("success", "Registration successful. Please login.");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        AppUser user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            return "admin".equals(user.getRole()) ? "redirect:/books/view" : "redirect:/books/user";
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
