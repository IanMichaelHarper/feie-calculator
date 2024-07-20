package com.feie.calculator.models;

public class TaxResults {
    private double incomeAfterTaxWithFEIE;
    private double federalTax;
    private double incomeAfterTaxWithoutFEIE;

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

    public double getIncomeAfterTaxWithoutFEIE() {
        return incomeAfterTaxWithoutFEIE;
    }

    public void setIncomeAfterTaxWithoutFEIE(double incomeAfterTax) {
        this.incomeAfterTaxWithoutFEIE = incomeAfterTax;
    }
}
