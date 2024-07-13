package com.feie.calculator.controllers;

import com.feie.calculator.models.ContactForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import static com.feie.calculator.ViewTemplateName.HOME;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return HOME;
    }

    @PostMapping("/submit")
    public String submitForm(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return HOME;
        }
        // Process the form data (e.g., save to the database)
        model.addAttribute("message", "Form submitted successfully!");
        model.addAttribute("submitted", true);
        return HOME;
    }

}