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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    public ExpenseDTO getById(String id) {

        Optional<Expense> res = repository.findById(id);

        return (res.isEmpty()) ? null : ExpenseToDto(res.get());
    }
    */
    @Test
    void happyPath_getExpenseById_returnsExpenseDTO() {
        // Arrange
        // prep the value that should be in the db
        String id = "thisIsTheId";
        LocalDate date = LocalDate.now();
        Expense savedExpense = new Expense(date, new BigDecimal("50.00"), "Video Games" );
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
    void happyPath_update_returnsExpenseDTO() {
        // Arrange
        // prep the Expense value that should be in the db
        String id = "n6ic9e";
        LocalDate date = LocalDate.now();
        Expense savedExpense = new Expense(date, new BigDecimal("50.00"), "Video Games" );
        savedExpense.setId(id);

        // prep the new ExpenseDTO to update with
        ExpenseDTO updated = new ExpenseDTO(id, date, new BigDecimal("60.00"), "Video Games");

        // prep our expected value to compare with for the assert - should be the same as the ExpenseDTO we're sending
        ExpenseDTO expected = new ExpenseDTO(id, date, new BigDecimal("60.00"), "Video Games");

        // "put" the fake entry in the db
        when(repo.findById(id)).thenReturn(Optional.of(savedExpense));
        // "put" the updated entry in the db
        when(repo.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        ExpenseDTO actual = service.update(id, updated);

        // Assert
        // compare expected to actual
        assertThat(actual).isEqualTo(expected);
        //make sure the DTO we sent in wasn't changed
        assertThat(updated).isEqualTo(expected);
    }
}













