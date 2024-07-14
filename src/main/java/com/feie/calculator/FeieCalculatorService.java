package com.feie.calculator;

import com.feie.calculator.models.TaxResults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FeieCalculatorService {

    public TaxResults calculateIncomeAfterTax(String income) {
        BigDecimal incomeAfterFEIE = BigDecimal.ZERO;
        BigDecimal federalTax = BigDecimal.ZERO;
        BigDecimal incomeAfterTax= BigDecimal.ZERO;

        TaxResults taxResults = new TaxResults();
        taxResults.setIncomeAfterFEIE(incomeAfterFEIE);
        taxResults.setFederalTax(federalTax);
        taxResults.setIncomeAfterTax(incomeAfterTax);

        return taxResults;
    }

}
