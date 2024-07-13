package com.feie.calculator.models;

import jakarta.validation.constraints.NotEmpty;

public class IncomeForm {

    @NotEmpty(message = "Income is required")
    private String income;

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

}