package com.revature.ExpenseReport.Repository;

import com.revature.ExpenseReport.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findByExpenseMerchant(String merchant);
}
