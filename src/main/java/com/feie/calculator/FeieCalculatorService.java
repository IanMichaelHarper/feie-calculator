package com.feie.calculator;

import com.feie.calculator.models.TaxResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeieCalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeieCalculatorService.class);

    private static final double STANDARD_DEDUCTION = 14600.0;  // for single people TODO: add input for married etc.
    private static final double FEIE = 126500.0;  // Maximum exclusion amount for 2024

    private static final double[] RATES = {0.10, 0.12, 0.22, 0.24, 0.32, 0.35, 0.37};
    private static final double[] BRACKETS = {11600, 47150, 100_525, 191950, 243725, 609350};

    public TaxResults calculateIncomeAfterTax(String inc) {
        if (inc == null) {
            return null;
        }
        double income = Double.parseDouble(inc);

        if (income <= 0) {
            // TODO: throw popup
            System.out.println("Please enter a valid income.");
            return null;
        }

        double taxWithoutFEIE = calculateTaxWithoutFEIE(income);
        double taxWithFEIE = calculateTaxWithFEIE(income);

        LOGGER.info("Federal tax without FEIE: {}", taxWithoutFEIE);
        LOGGER.info("Federal Tax with FEIE: {}", taxWithFEIE);

        TaxResults taxResults = new TaxResults();
        taxResults.setIncomeAfterTaxWithoutFEIE(income - taxWithoutFEIE);
        taxResults.setIncomeAfterTaxWithFEIE(income - taxWithFEIE);

        return taxResults;
    }

    public double calculateTaxWithoutFEIE(double income) {
        double taxableIncome = Math.max(income - STANDARD_DEDUCTION, 0);

        return calculateBracketTax(taxableIncome);
    }

    public double calculateTaxWithFEIE(double income) {
        double remainingIncome = Math.max(income - FEIE, 0);

        remainingIncome = Math.max(remainingIncome - STANDARD_DEDUCTION, 0);

        // Calculate Taxable Income for Bracket Determination
        double bracketIncome = income - FEIE;

        // Calculate the tax as if there is no FEIE to get the correct marginal rate
        double totalTaxWithoutFEIE = calculateBracketTax(bracketIncome);

        // Calculate the effective rate based on income including FEIE
        double effectiveRate = totalTaxWithoutFEIE / bracketIncome;

        // Apply the effective rate to the remaining income
        return calculateBracketTaxWithRate(remainingIncome, effectiveRate);
    }

    private double calculateBracketTax(double income) {
        double tax = 0.0;
        double previousBracket = 0.0;

        for (int i = 0; i < BRACKETS.length; i++) {
            if (income > BRACKETS[i]) {
                tax += (BRACKETS[i] - previousBracket) * RATES[i];
                previousBracket = BRACKETS[i];
            } else {
                tax += (income - previousBracket) * RATES[i];
                return tax;
            }
        }

        // If income exceeds the highest bracket
        tax += (income - previousBracket) * RATES[RATES.length - 1];
        return tax;
    }

    private double calculateBracketTaxWithRate(double income, double effectiveRate) {
        return income * effectiveRate;
    }

}
