package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Controller.ExpenseWOIDDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        // the repo method returns a list of expenses...
        // we need to convert every expense on the list to a DTO...
        // keep/put back in a list to return
        return repository.findAll().stream().map(this::ExpenseToDto).toList();
    }

    public List<ExpenseDTO> searchByExpenseMerchant(String merchant) {
        return repository.findByExpenseMerchant(merchant).stream().map(this::ExpenseToDto).toList();
    }

    public ExpenseDTO create( ExpenseWOIDDTO dto) {
        Expense entity = new Expense(dto.expenseDate(), dto.expenseValue(), dto.expenseMerchant());
        return ExpenseToDto(repository.save(entity));
    }

    public ExpenseDTO getById(String id) {

        Optional<Expense> res = repository.findById(id);

        return (res.isEmpty()) ? null : ExpenseToDto(res.get());
    }


    private ExpenseDTO ExpenseToDto( Expense expense ) {
        return new ExpenseDTO(expense.getId(), expense.getDate(), expense.getValue(), expense.getMerchant());
    }


}
