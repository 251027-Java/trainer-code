package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Controller.ExpenseWOIDDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public List<ExpenseDTO> getAllExpenses() {
        return repository.findAll().stream().map(this::ExpenseToDTO).toList();
    }

    public List<ExpenseDTO> searchByExpenseMerchant(String merchant) {
        return repository.findByExpenseMerchant(merchant).stream().map(this::ExpenseToDTO).toList();
    }

    public ExpenseDTO getByiD(String id) {
        Expense e = repository.findById(id).orElse(null);
        return (e == null) ? null : ExpenseToDTO(e);
    }

    public ExpenseDTO create(ExpenseWOIDDTO dto) {
        Expense e = new Expense(dto.expenseDate(), dto.expenseValue(),
                dto.expenseMerchant());

        return ExpenseToDTO( repository.save(e) );
    }

    //Update
    public ExpenseDTO update(String id, ExpenseDTO dto) {
        Expense expense =
                repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        expense.setExpenseDate(dto.expenseDate());
        expense.setExpenseValue(dto.expenseValue());
        expense.setExpenseMerchant(dto.expenseMerchant());

        return ExpenseToDTO(repository.save(expense));
    }

    //Delete
    public void delete(String id) {
        repository.deleteById(id);
    }

    private ExpenseDTO ExpenseToDTO(Expense expense) {
        return new ExpenseDTO(expense.getExpenseId(), expense.getExpenseDate(),
                expense.getExpenseValue(), expense.getExpenseMerchant(),
                expense.getReport());
    }
}
