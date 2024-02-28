package com.example.demo27.controller;

import com.example.demo27.impl.StatementImpl;
import com.example.demo27.impl.UserImpl;
import com.example.demo27.model.Statement;
import com.example.demo27.model.StatusStatement;
import com.example.demo27.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StatementController {
    private final StatementImpl statementImpl;
    private final UserImpl userImpl;

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userImpl.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/statements/my")
    public String myStatements(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userImpl.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("statements", statementImpl.findByUser(user));
        return "mystatements";
    }

    @GetMapping("/statements/add")
    public String addStatementPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userImpl.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("statement", new Statement());
        return "add-statement";
    }

    @PostMapping("/statements/add")
    public String addStatement(@ModelAttribute("statement") Statement statement, Principal principal) {
        statementImpl.save(statement, principal);
        return "redirect:/statements/my";
    }

    @GetMapping("/admin/statements")
    public String adminStatements(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userImpl.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("statements", statementImpl.findAll());
        return "admin-statements";
    }

    @GetMapping("/admin/statements/edit/{id}")
    public String editStatementAdminPage(@AuthenticationPrincipal UserDetails userDetails, Model model, @PathVariable Long id) {
        User user = userImpl.findByUsername(userDetails.getUsername());
        Optional<Statement> statementCandidate = statementImpl.findById(id);

        if (statementCandidate.isPresent()) {
            Statement statement = statementCandidate.get();
            model.addAttribute("user", user);
            model.addAttribute("statement", statement);
            model.addAttribute("allStatus", StatusStatement.values());
            return "edit-statement";
        }
        return "redirect:/admin/statements";
    }

    @PostMapping("/admin/statements/edit/{id}")
    public String editStatement(@PathVariable Long id, @ModelAttribute Statement updatedStatement) {
        statementImpl.updateStatement(id, updatedStatement.getStatus());
        return "redirect:/admin/statements";
    }
}
