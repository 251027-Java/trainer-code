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

    //verifies service searches for expenses by merchant. should return only matching expenses as DTOs
    @Test
    void searchByExpenseMerchant_returnsMatchingExpenses() {
        // arrange
        String merchant = "Walmart";
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().minusDays(1);
        
        Expense expense1 = new Expense(date1, new BigDecimal("100.00"), merchant);
        expense1.setId("id1");
        
        Expense expense2 = new Expense(date2, new BigDecimal("75.50"), merchant);
        expense2.setId("id2");
        
        List<Expense> matchingExpenses = Arrays.asList(expense1, expense2);
        
        // configure mock to return expenses matching the merchant
        when(repo.findByExpenseMerchant(merchant)).thenReturn(matchingExpenses);

        // act
        List<ExpenseDTO> actual = service.searchByExpenseMerchant(merchant);

        // assert
        assertThat(actual).hasSize(2);
        assertThat(actual).allMatch(dto -> dto.expenseMerchant().equals(merchant));
    }
}













