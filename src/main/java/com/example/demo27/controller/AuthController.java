package com.example.demo27.controller;

import com.example.demo27.impl.UserImpl;
import com.example.demo27.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserImpl userImpl;

    @GetMapping("/registration")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @GetMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("loginError", "Неверно введен логин или пароль");
        }
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        userImpl.logout(request, response);
        return "redirect:/login";
    }

    @PostMapping("/registration/save")
    public String register(@ModelAttribute("user") User user, BindingResult result, Model model) {
        try {
            userImpl.register(user);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Пользователь с таким никнеймом уже существует")) {
                result.rejectValue("username", "", "Данный логин уже используется");
            }
            model.addAttribute("user", user);
            return "auth/register";
        }
    }
}
