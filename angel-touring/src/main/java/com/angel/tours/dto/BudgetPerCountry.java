package com.angel.tours.dto;

public class BudgetPerCountry {

    double amount;
    String currency;
    String countryName;

    public BudgetPerCountry(double amount, String currency, String countryName) {
        this.amount = amount;
        this.currency = currency;
        this.countryName = countryName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
