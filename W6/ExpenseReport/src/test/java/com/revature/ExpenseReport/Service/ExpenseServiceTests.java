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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {
    // Fields
    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseService service;

    // Constructor

    // Methods

        // Test getById()
    @Test
    void happyPath_getExpenseById_returnsExpenseDTO () {
        // Arrange
        String id = "ThisIsId";
        LocalDate d = LocalDate.now();
        Expense savedExpense = new Expense(d, new BigDecimal("50.00"),
                "GameStop");
        savedExpense.setId(id);

        ExpenseDTO expected = new ExpenseDTO(id, d, new BigDecimal("50.00"),"GameStop");

        when(repository.findById(id)).thenReturn(Optional.of(savedExpense));

        // Act
        ExpenseDTO actual = service.getById(id);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
