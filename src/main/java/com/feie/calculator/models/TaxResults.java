package com.feie.calculator.models;

public class TaxResults {
    private double incomeAfterFEIE;
    private double federalTax;
    private double incomeAfterTax;

    // Getters and setters
    public double getIncomeAfterFEIE() {
        return incomeAfterFEIE;
    }

    public void setIncomeAfterFEIE(double incomeAfterFEIE) {
        this.incomeAfterFEIE = incomeAfterFEIE;
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
