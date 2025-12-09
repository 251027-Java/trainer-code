package com.revature.ExpenseReport.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Repository.ExpenseRepository;


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
    void happyPath_updateExpense_returnsUpdatedExpenseDTO() {
        // Arrange
        // prep the value that should be in the db
        String id = "thisIsTheId";
        LocalDate date = LocalDate.now();
        Expense savedExpense = new Expense(date, new BigDecimal("50.00"), "Walmart" );
        savedExpense.setId(id);

        // prep our update DTO and expected value to compare
        ExpenseDTO updateDTO = new ExpenseDTO(id, date.plusDays(1), new BigDecimal("75.00"), "Whole Foods");
        ExpenseDTO expected = new ExpenseDTO(id, date.plusDays(1), new BigDecimal("75.00"), "Whole Foods");

        // "put" the fake entry in the db
        when(repo.findById(id)).thenReturn(Optional.of(savedExpense));
        when(repo.save(savedExpense)).thenReturn(savedExpense);

        // ACT
        ExpenseDTO actual = service.update(id, updateDTO);

        // Assert
        // compare expected to actual
        assertThat(actual).isEqualTo(expected);
    }

}













