package com.revature.ExpenseReport.Controller;

import com.revature.ExpenseReport.Model.Report;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDTO(String expenseID, LocalDate expenseDate, BigDecimal expenseValue,
                         String expenseMerchant, Report report)
{}
