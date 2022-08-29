package com.angel.tours.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AngelTourRequest {

    String startingCountry;
    double budgetPerCountry;
    double totalBudget;
    String inputCurrency;

    @JsonCreator
    public AngelTourRequest(@JsonProperty("startingCountry") String startingCountry,
                            @JsonProperty("budgetPerCountry") double budgetPerCountry,
                            @JsonProperty("totalBudget") double totalBudget,
                            @JsonProperty("inputCurrency") String inputCurrency) {
        this.startingCountry = startingCountry;
        this.budgetPerCountry = budgetPerCountry;
        this.totalBudget = totalBudget;
        this.inputCurrency = inputCurrency;
    }

    public String getStartingCountry() {
        return startingCountry;
    }

    public void setStartingCountry(String startingCountry) {
        this.startingCountry = startingCountry;
    }

    public double getBudgetPerCountry() {
        return budgetPerCountry;
    }

    public void setBudgetPerCountry(double budgetPerCountry) {
        this.budgetPerCountry = budgetPerCountry;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public String getInputCurrency() {
        return inputCurrency;
    }

    public void setInputCurrency(String inputCurrency) {
        this.inputCurrency = inputCurrency;
    }
}
