package com.feie.calculator.controllers;

import com.feie.calculator.FeieCalculatorService;
import com.feie.calculator.models.IncomeForm;
import com.feie.calculator.models.TaxResults;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.feie.calculator.ViewTemplateName.HOME;

@Controller
public class HomeController {

    @Autowired
    private FeieCalculatorService feieCalculatorService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("incomeForm", new IncomeForm());
        return HOME;
    }

    @PostMapping("/submit")
    public String submitForm(@Valid @ModelAttribute IncomeForm incomeForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return HOME;
        }

        TaxResults taxResults = feieCalculatorService.calculateIncomeAfterTax(incomeForm.getIncome());

        // Process the form data (e.g., save to the database)
        model.addAttribute("taxResults", taxResults);
        model.addAttribute("message", "Form submitted successfully!");
        model.addAttribute("submitted", true);
        return HOME;
    }

}