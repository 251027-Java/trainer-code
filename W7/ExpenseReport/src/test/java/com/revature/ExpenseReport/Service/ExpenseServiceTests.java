package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {

    // Fields
    @Mock
    private ExpenseRepository repo;

    @InjectMocks
    private ExpenseService service;

    // Constructor

    // Methods

    // Triple A
    // arrange - prepping any resources/objects we need to run our test
    // act - the action/function of executing the code/logic we're testing
    // assert - the final check to pass or fail

    /*
     * public ExpenseDTO getById(String id) {
     * 
     * Optional<Expense> res = repository.findById(id);
     * 
     * return (res.isEmpty()) ? null : ExpenseToDto(res.get());
     * }
     */
    @Test
    void happyPath_getExpenseById_returnsExpenseDTO() {
        // Arrange
        // prep the value that should be in the db
        String id = "thisIsTheId";
        LocalDate date = LocalDate.now();
        Expense savedExpense = new Expense(date, new BigDecimal("50.00"), "Video Games");
        savedExpense.setId(id);

        // prep our expected value to compare with for the assert
        ExpenseDTO expected = new ExpenseDTO(id, date, new BigDecimal("50.00"), "Video Games");

        // "put" the fake entry in the db
        when(repo.findById(id)).thenReturn(Optional.of(savedExpense));

        // ACT
        ExpenseDTO actual = service.getById(id);

        // Assert
        // compare expected to actual
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void happyPath_getAllExpenses_returnsListOfExpenseDTOs() {
        // Arrange
        // pre the value that should be in the db
        LocalDate date1 = LocalDate.of(2022, 4, 1);
        LocalDate date2 = LocalDate.of(2022, 4, 2);
        Expense expense1 = new Expense(date1, new BigDecimal("59.99"), "Best Buy");
        Expense expense2 = new Expense(date2, new BigDecimal("45.49"), "Wholefoods");

        List<Expense> expenses = Arrays.asList(null, expense1, expense2);

        // mock repo layer to return list of expenses
        when(repo.findAll()).thenReturn(expenses);

        List<ExpenseDTO> actual = service.getAllExpenses();

        // Assert
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).expenseDate()).isEqualTo(date1);
        assertThat(actual.get(0).expenseValue()).isEqualTo(new BigDecimal("59.99"));
        assertThat(actual.get(0).expenseMerchant()).isEqualTo("Best Buy");
        assertThat(actual.get(1).expenseDate()).isEqualTo(date2);
        assertThat(actual.get(1).expenseValue()).isEqualTo(new BigDecimal("45.49"));
        assertThat(actual.get(1).expenseMerchant()).isEqualTo("Wholefoods");

        // Completed

    }

}
