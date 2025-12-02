package com.revature.ExpenseReport.Controller;

import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Service.ExpenseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    // Fields
    private final ExpenseService service;

    // Constructor
    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    // Methods
    @GetMapping
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses(); // all of the expenses!
    }

    @GetMapping("/search")
    public List<Expense> search (@RequestParam String merchant) {
        return service.searchByExpenseMerchant(merchant); // all expenses for a merchant
    }
}
