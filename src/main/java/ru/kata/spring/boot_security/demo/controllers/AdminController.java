package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService service, RoleRepository roleRepository) {
        this.service = service;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getAll(Model model, Principal principal) {
        model.addAttribute("users", service.getUsersList());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("user", service.findByEmail(principal.getName()));
        return "admin";
    }

    @PostMapping
    public String createNewUser(@ModelAttribute("user") User user) {
        service.createNewUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, Long id) {
        service.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
        return "redirect:/admin";
    }
}