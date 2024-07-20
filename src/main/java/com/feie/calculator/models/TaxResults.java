package com.feie.calculator.models;

public class TaxResults {
    private double incomeAfterTaxWithFEIE;
    private double federalTax;
    private double incomeAfterTax;

    // Getters and setters
    public double getIncomeAfterTaxWithFEIE() {
        return incomeAfterTaxWithFEIE;
    }

    public void setIncomeAfterTaxWithFEIE(double incomeAfterTaxWithFEIE) {
        this.incomeAfterTaxWithFEIE = incomeAfterTaxWithFEIE;
    }

    public double getFederalTax() {
        return federalTax;
    }

    public void setFederalTax(double federalTax) {
        this.federalTax = federalTax;
    }

    public double getIncomeAfterTax() {
        return incomeAfterTax;
    }

    public void setIncomeAfterTax(double incomeAfterTax) {
        this.incomeAfterTax = incomeAfterTax;
    }
}
