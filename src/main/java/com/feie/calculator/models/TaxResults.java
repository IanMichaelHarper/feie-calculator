package com.feie.calculator.models;

import java.math.BigDecimal;

public class TaxResults {
    private BigDecimal incomeAfterFEIE;
    private BigDecimal federalTax;
    private BigDecimal incomeAfterTax;

    // Getters and setters
    public BigDecimal getIncomeAfterFEIE() {
        return incomeAfterFEIE;
    }

    public void setIncomeAfterFEIE(BigDecimal incomeAfterFEIE) {
        this.incomeAfterFEIE = incomeAfterFEIE;
    }

    public BigDecimal getFederalTax() {
        return federalTax;
    }

    public void setFederalTax(BigDecimal federalTax) {
        this.federalTax = federalTax;
    }

    public BigDecimal getIncomeAfterTax() {
        return incomeAfterTax;
    }

    public void setIncomeAfterTax(BigDecimal incomeAfterTax) {
        this.incomeAfterTax = incomeAfterTax;
    }
}
