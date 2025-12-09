package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Controller.ExpenseWOIDDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    void happyPath_updateExpense_correctlyUpdatesAndSaves() {

        // Arrange
        String id = "thisIsTheUpdateId";
        LocalDate date = LocalDate.now();
        Expense originalExpense = new Expense(date, new BigDecimal("50.00"), "Video Games");
        originalExpense.setId(id);

        //DTO that will come with update (is passed, should update by id)
        ExpenseDTO updateDto = new ExpenseDTO(id, date, new BigDecimal("75.00"), "Movies");


        // Expected DTO object to be returned (Must add Id)
        Expense expectedSavedExpense = new Expense(date, new BigDecimal("75.00"), "Movies");
        expectedSavedExpense.setId(id);
        ExpenseDTO expectedOutput = new ExpenseDTO(id, date, new BigDecimal("75.00"), "Movies");

        // Two repo calls are done, define them and what they should return after.

        // Repo can find original expense record
        when(repo.findById(id)).thenReturn(Optional.of(originalExpense));

        // When saved called on any expense object, return the expected savedExpense.
        when(repo.save(any(Expense.class))).thenReturn(expectedSavedExpense);


        // ACT
        // Do updated
        ExpenseDTO updateActual = service.update(id, updateDto);

        // Assert
        // Verify the returned DTO matches the expected Expense
        assertThat(updateActual).isEqualTo(expectedOutput);
    }
}













