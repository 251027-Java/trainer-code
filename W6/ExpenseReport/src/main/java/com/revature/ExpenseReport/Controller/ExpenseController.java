package com.revature.ExpenseReport.Controller;

import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin    // domain:port/api/expenses
public class ExpenseController {
    // Fields
    private final ExpenseService service;

    // Constructor
    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    // Methods
    @GetMapping // domain:port/api/expenses
    public List<ExpenseDTO> getAllExpenses() {
        return service.getAllExpenses(); // all of the expenses!
    }

    @GetMapping("/search") // domain:port/api/expenses/search?merchant=Walmart
    public List<ExpenseDTO> search (@RequestParam String merchant) {
        return service.searchByExpenseMerchant(merchant); // all expenses for a merchant
    }

    @PostMapping
    public ExpenseDTO createExpense(@RequestBody ExpenseWOIDDTO dto){
        return service.createExpense(dto);
    }

    @PutMapping("/{id}")
    public ExpenseDTO updateExpense(
            @PathVariable String id,
            @RequestBody ExpenseWOIDDTO dto
    ){
        return service.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id){
        service.deleteExpense(id);
    }
}
