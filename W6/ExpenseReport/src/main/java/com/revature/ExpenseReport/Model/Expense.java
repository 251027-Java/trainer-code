package com.revature.ExpenseReport.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Expense {
    // Fields

    @Id
    @GeneratedValue
    private String expenseId;

    private String expenseMerchant;
    private Date expenseDate;
    private double expenseValue;

    // Constructor
    public Expense() {}

    public Expense(Date date, double value, String merchant){
        this.expenseDate = date;
        this.expenseValue = value;
        this.expenseMerchant = merchant;
    }

    // Methods
    public String getId() { return expenseId; }
    public Date getDate() { return expenseDate; }
    public double getValue() { return expenseValue; }
    public String getMerchant() { return expenseMerchant; }

    public void setId(String id) { this.expenseId = id; }
    public void setDate(Date date) { this.expenseDate = date; }
    public void setValue(double value) { this.expenseValue = value; }
    public void setMerchant(String merchant) { this.expenseMerchant = merchant; }

}
