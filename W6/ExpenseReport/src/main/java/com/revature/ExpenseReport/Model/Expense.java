package com.revature.ExpenseReport.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity // telling JPA that this needs to be a table in the database
@Table(name = "expenses") // naming the table in the database
@Data
public class Expense {
    // Fields
    @Id
    @GeneratedValue
    private String expenseId;
    @Column(name = "expenseMerchant")
    private String expenseMerchant; // manage column information for this variable
    private LocalDate expenseDate;
    private BigDecimal expenseValue;

    @ManyToOne()
    @JoinColumn(name = "reportId")
    @ToString.Exclude
    @JsonBackReference
    Report report; // Foreign key

    // Constructor
    public Expense() {
    }

    public Expense(LocalDate date, BigDecimal value, String merchant) {
        this.expenseDate = date;
        this.expenseValue = value;
        this.expenseMerchant = merchant;
    }
}
