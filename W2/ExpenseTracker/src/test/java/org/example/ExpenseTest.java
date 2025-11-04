package org.example;

import java.util.Date;

public class ExpenseTest {
    public static void main(String[] args){
        testExpenseCreation();
    }

    public static void testExpenseCreation() {
        // Arrange the conditions for your test
        Expense expense = new Expense(1, new Date(), 100, "DummyMerchant");

        // Act - what functionality are you trying to validate
        int possibleID = expense.getId();
        double possibleValue = expense.getValue();

        // Assert - compare the expected to the actual
        if (possibleID != 1) {System.out.println("Failed id");}
        else if (possibleValue != 100) {System.out.println("Failed value");}
        else { System.out.println("Passed expense creation!");}

    }

}
