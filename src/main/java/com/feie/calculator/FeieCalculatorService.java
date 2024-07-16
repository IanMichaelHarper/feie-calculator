package com.feie.calculator;

import com.feie.calculator.models.TaxResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeieCalculatorService {

    private static final Logger log = LoggerFactory.getLogger(FeieCalculatorService.class);

    public TaxResults calculateIncomeAfterTax(String inc) {
        // most code here is from Venice.ai
        if (inc == null) {
            return null;
        }
        double income = Double.parseDouble(inc);

        if (income <= 0) {
            System.out.println("Please enter a valid income.");
            return null;
        }

        double FEIEAmount = 108700; // TODO: Maximum exclusion amount for 2022
        double[][] federalTaxBrackets = {
//                {0.10, 0},
                {0.10, 40000},
                {0.12, 80000},
                {0.22, 170000},
                {0.24, 215000},
                {0.32, 500000},
                {0.35, 1000000},
                {0.37, 1000000}
                // TODO: remainder
        };

        double[] federalTaxRates = new double[federalTaxBrackets.length];
        double[] federalTaxThresholds = new double[federalTaxBrackets.length];

        for (int i = 0; i < federalTaxBrackets.length; i++) {
            federalTaxRates[i] = federalTaxBrackets[i][0];
            federalTaxThresholds[i] = federalTaxBrackets[i][1];
        }

//        double taxableIncome = Math.max(income - FEIEAmount, 0);
        double taxableIncome = income;
        double federalTax = 0;
        double feieRebate = 0;
        for (int i = 0; i < federalTaxBrackets.length; i++) {
            double[] bracket = federalTaxBrackets[i];
            if (taxableIncome <= bracket[1]) {

//                log.info(String.format("taxableIncome %,.2f * bracket[0] %,.2f "), taxableIncome, bracket[0]);
                double thisRemainingTax = taxableIncome * bracket[0];
                federalTax += thisRemainingTax;
//                if (taxableIncome < FEIEAmount) {
//                    feieRebate += thisRemainingTax;
//                }
                break;
            }
//            log.info(String.format("bracket[1] %,.2f * bracket[0] %,.2f"), bracket[1], bracket[0]);
            double thisTax = bracket[1] * bracket[0];
            federalTax += thisTax;
            if (bracket[1] < FEIEAmount) {
                feieRebate += thisTax;
            }

            taxableIncome -= bracket[1];
        }

//        double incomeAfterFEIE = Math.min(income, FEIEAmount);
        double incomeAfterTax = income - federalTax;

        System.out.printf("Income after FEIE and federal tax: $%.2f%n", incomeAfterTax);
        System.out.printf("Federal tax: $%.2f%n", federalTax);
//        System.out.printf("Tax without FEIE: $%.2f%n", income * 0.24);

        TaxResults taxResults = new TaxResults();
        taxResults.setFederalTax(federalTax);
        taxResults.setIncomeAfterTax(incomeAfterTax);
        taxResults.setIncomeAfterFEIE(incomeAfterTax + feieRebate);

        return taxResults;
    }

}
