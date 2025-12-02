package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    // Fields
    private final ExpenseRepository repository;

    // Constructor
    public ExpenseService (ExpenseRepository repository){
        this.repository = repository;
    }

    // Methods
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public List<Expense> searchByExpenseMerchant(String merchant) {
        return repository.findByExpenseMerchant(merchant);
    }
}
