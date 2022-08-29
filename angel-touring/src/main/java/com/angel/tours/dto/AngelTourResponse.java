package com.angel.tours.dto;

import java.util.List;

public class AngelTourResponse {
    int roundTripsPossible;
    double balanceLeftOver;
    List<BudgetPerCountry> budgetBreakDown;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BudgetPerCountry> getBudgetBreakDown() {
        return budgetBreakDown;
    }

    public void setBudgetBreakDown(List<BudgetPerCountry> budgetBreakDown) {
        this.budgetBreakDown = budgetBreakDown;
    }


    public AngelTourResponse(int roundTripsPossible, double balanceLeftOver, List<BudgetPerCountry> budgetPerCountry, String message) {
        this.roundTripsPossible = roundTripsPossible;
        this.balanceLeftOver = balanceLeftOver;
        this.budgetBreakDown = budgetPerCountry;
        this.message = message;
    }

    public int getRoundTripsPossible() {
        return roundTripsPossible;
    }

    public void setRoundTripsPossible(int roundTripsPossible) {
        this.roundTripsPossible = roundTripsPossible;
    }

    public double getBalanceLeftOver() {
        return balanceLeftOver;
    }

    public void setBalanceLeftOver(double balanceLeftOver) {
        this.balanceLeftOver = balanceLeftOver;
    }


}
