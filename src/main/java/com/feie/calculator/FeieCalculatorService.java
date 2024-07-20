package com.feie.calculator;

import com.feie.calculator.models.TaxResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeieCalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeieCalculatorService.class);

    public TaxResults calculateIncomeAfterTax(String inc) {
        // most code here is from Venice.ai
        if (inc == null) {
            return null;
        }
        double income = Double.parseDouble(inc);

        if (income <= 0) {
            // TODO: throw popup
            System.out.println("Please enter a valid income.");
            return null;
        }

        double FEIEAmount = 126_500; // Maximum exclusion amount for 2024
        double standardDeduction =  14_600;  // for single people TODO: add input for married etc.
        double taxableIncome = income - standardDeduction;
        double[][] federalTaxBrackets = {
                {0.10, 11_600},
                {0.12, 47_150},
                {0.22, 100_525},
                {0.24, 191_950},
                {0.32, 243_725},
                {0.35, 609_350},
                {0.37, Double.MAX_VALUE}
        };

        double remainder = 0;
        double federalTax = 0;
        double feieRebate = 0;
        // TODO: Clean the logic here to make code cleaner - Implement strategy pattern?
        for (int i = 0; i < federalTaxBrackets.length; i++) {
            double thisFederalTax;
            if (taxableIncome < federalTaxBrackets[0][1]){
                thisFederalTax = federalTaxBrackets[0][0] * taxableIncome;
               federalTax += thisFederalTax;
               feieRebate += thisFederalTax;
               break;
            }
            else {
                if (i == 0){
                    thisFederalTax = federalTaxBrackets[i][0] * federalTaxBrackets[i][1];
                    federalTax += thisFederalTax;
                    feieRebate += thisFederalTax;
                }
                else if (taxableIncome < federalTaxBrackets[i][1]) {
                    remainder = taxableIncome - federalTaxBrackets[i-1][1];
                    thisFederalTax = federalTaxBrackets[i][0] * remainder;
                    federalTax += thisFederalTax;
                    if (federalTaxBrackets[i][1] <= FEIEAmount){
                        feieRebate += thisFederalTax;
                    }
                    // else if FEIEamount in bracket
                    else if (i == 3) { // TODO: make dynamic
                        feieRebate += federalTaxBrackets[i][0] * (FEIEAmount - federalTaxBrackets[i-1][1]);
                    }
                    break;
                }
                else {
                    thisFederalTax = federalTaxBrackets[i][0] * (taxableIncome - federalTaxBrackets[i-1][1]);
                    federalTax += thisFederalTax;
                    if (i < 3){
                        feieRebate += thisFederalTax;
                    }
                    else if (i == 3) {
                        feieRebate += federalTaxBrackets[i][0] * (FEIEAmount - federalTaxBrackets[i-1][1]);
                    }
                }
            }
        }

        double incomeAfterTax = taxableIncome - federalTax;
        LOGGER.info("Income after federal tax: {}", incomeAfterTax);
        LOGGER.info("Federal tax: {}", federalTax);
        LOGGER.info("Tax with FEIE: {}", federalTax - feieRebate);

        TaxResults taxResults = new TaxResults();
        taxResults.setFederalTax(federalTax);
        taxResults.setIncomeAfterTax(incomeAfterTax);
        taxResults.setIncomeAfterTaxWithFEIE(incomeAfterTax + feieRebate);

        return taxResults;
    }


//    double[] federalTaxRates = new double[federalTaxBrackets.length];
//    double[] federalTaxThresholds = new double[federalTaxBrackets.length];
//
//        for (int i = 0; i < federalTaxBrackets.length; i++) {
//        federalTaxRates[i] = federalTaxBrackets[i][0];
//        federalTaxThresholds[i] = federalTaxBrackets[i][1];
//    }
//
//    //        double taxableIncome = Math.max(income - FEIEAmount, 0);
//    double taxableIncome = income;
//    double federalTax = 0;
//    double feieRebate = 0;
//        for (int i = 0; i < federalTaxBrackets.length; i++) {
//        double[] bracket = federalTaxBrackets[i];
//        double thisBracketDollarThreshold = bracket[1];
//        double thisBracketRate = bracket[0];
//        if (income <= thisBracketDollarThreshold) {
//            log.info("taxableIncome {} * bracket[0] {} ", taxableIncome, bracket[0]);
//            double thisRemainingTax = thisBracketRate * taxableIncome;
//            federalTax += thisRemainingTax;
//            if (income <= FEIEAmount) {
//                feieRebate += thisRemainingTax;
//            }
//            else {
//                feieRebate += thisBracketRate * (FEIEAmount - federalTaxBrackets[i-1][1]);
//            }
//
//            break;
//        }
//        log.info("bracket[1] {} * bracket[0] {}", bracket[1], bracket[0]);
//        double thisTax = thisBracketRate * thisBracketDollarThreshold;
//        federalTax += thisTax;
//        if (thisBracketDollarThreshold <= FEIEAmount) {
//            feieRebate += thisTax;
//        }
//        else {} // no rebate beyond FEIE amount
//
//        if (i == 0) {
//            taxableIncome -= thisBracketDollarThreshold;
//        }
//        else {
//            taxableIncome -= federalTaxBrackets[i][1] - federalTaxBrackets[i - 1][1];
//        }
//    }
//
//    //        double incomeAfterFEIE = Math.min(income, FEIEAmount);
//    double incomeAfterTax = income - federalTax;
}
