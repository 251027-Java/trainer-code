package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Controller.ReportDTO;
import com.revature.ExpenseReport.Controller.ExpenseWOIDDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Model.Report;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import com.revature.ExpenseReport.Repository.ReportRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {

    // Fields
    @Mock
    private ExpenseRepository repo;

    @Mock
    private ReportRepository reportRepo;
    
    @InjectMocks
    private ReportService reportService;

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
    void happyPath_delete_returnsNull(){
        String id = "thisIsTheId";
        LocalDate date = LocalDate.now();
        Expense savedExpense = new Expense(date, new BigDecimal("50.00"), "Video Games" );
        savedExpense.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(savedExpense), Optional.empty());

        service.delete(id);

        ExpenseDTO expected = service.getById(id);

        assertNull(expected);
    }

    @Test
    public void deleteExpense_HappyPath() {
        // Arrange
        Mockito.doNothing().when(expenseRepository).deleteById(id);

        // ACT
        expenseService.deleteExpense(id);

        // Assert
        Mockito.verify(expenseRepository, Mockito.times(1))
                .deleteById(id);
    }

    @Test
    void happyPath_delete_deletesTheId() {
        String id = "somerandomid";

        service.delete(id);
        
        verify(repo, times(1)).deleteById(id);
    }

    /*
    public List<ExpenseDTO> searchByExpenseMerchant(String merchant) {
        return repository.findByExpenseMerchant(merchant).stream().map(this::ExpenseToDto).toList();
    }
     */

    @Test
    void happyPath_searchByExpenseMerchant_ShouldReturnListOfExpenseDTOs() {
        //Arrange
        String testMerchant = "Starbucks";
        LocalDate date = LocalDate.now();

        // Create mock Expense entities (The input to the Service method)
        Expense entity1 = new Expense(date, new BigDecimal("12.50"), testMerchant);
        entity1.setId("id1");
        Expense entity2 = new Expense(date.minusDays(4), new BigDecimal("5.70"), testMerchant);
        entity2.setId("id2");
        List<Expense> mockEntities = List.of(entity1, entity2);

        // Create the expected DTOs (The output you expect)
        ExpenseDTO expectedDto1 = new ExpenseDTO("id1", date, new BigDecimal("12.50"), testMerchant);
        ExpenseDTO expectedDto2 = new ExpenseDTO("id2", date.minusDays(4), new BigDecimal("5.70"), testMerchant);
        List<ExpenseDTO> expectedDTOs = List.of(expectedDto1, expectedDto2);

        // Mock the repository call to return our predefined list of Expense entities.
        when(repo.findByExpenseMerchant(testMerchant)).thenReturn(mockEntities);

        //Act
        List<ExpenseDTO> actualDTOs = service.searchByExpenseMerchant(testMerchant);

        //Assert

        //Use recursive comparator to compare the ExpenseDTO objects to ensure the test doesn't fail due to reference inequality
        assertThat(actualDTOs)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedDTOs);

        //Verify that the service method correctly called the repository exactly once with the expected merchant name.
        verify(repo, Mockito.times(1)).findByExpenseMerchant(testMerchant);
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
        List<ExpenseDTO> actual = service.searchByExpenseMerchant("Walmart");

        // Assert
        // compare expected to actual
        assertThat(actual).isEqualTo(expected);
    }
  
   @Test
        public void deleteExpense_HappyPath() {
            // Arrange
            Mockito.doNothing().when(expenseRepository).deleteById(id);

            // ACT
            expenseService.deleteExpense(id);

            // Assert
            Mockito.verify(expenseRepository, Mockito.times(1))
                    .deleteById(id);
        }

    @Test
    void happyPath_delete_deletesTheId() {
        String id = "somerandomid";

        service.delete(id);
        
        verify(repo, times(1)).deleteById(id);
    }

    /*
    public List<ExpenseDTO> searchByExpenseMerchant(String merchant) {
        return repository.findByExpenseMerchant(merchant).stream().map(this::ExpenseToDto).toList();
    }
     */

    @Test
    void happyPath_searchByExpenseMerchant_ShouldReturnListOfExpenseDTOs() {
        //Arrange
        String testMerchant = "Starbucks";
        LocalDate date = LocalDate.now();

        // Create mock Expense entities (The input to the Service method)
        Expense entity1 = new Expense(date, new BigDecimal("12.50"), testMerchant);
        entity1.setId("id1");
        Expense entity2 = new Expense(date.minusDays(4), new BigDecimal("5.70"), testMerchant);
        entity2.setId("id2");
        List<Expense> mockEntities = List.of(entity1, entity2);

        // Create the expected DTOs (The output you expect)
        ExpenseDTO expectedDto1 = new ExpenseDTO("id1", date, new BigDecimal("12.50"), testMerchant);
        ExpenseDTO expectedDto2 = new ExpenseDTO("id2", date.minusDays(4), new BigDecimal("5.70"), testMerchant);
        List<ExpenseDTO> expectedDTOs = List.of(expectedDto1, expectedDto2);

        // Mock the repository call to return our predefined list of Expense entities.
        when(repo.findByExpenseMerchant(testMerchant)).thenReturn(mockEntities);

        //Act
        List<ExpenseDTO> actualDTOs = service.searchByExpenseMerchant(testMerchant);

        //Assert
        //Use recursive comparator to compare the ExpenseDTO objects to ensure the test doesn't fail due to reference inequality
        assertThat(actualDTOs)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedDTOs);

        //Verify that the service method correctly called the repository exactly once with the expected merchant name.
        verify(repo, Mockito.times(1)).findByExpenseMerchant(testMerchant);
    }

    @Test
    void sadPath_getExpenseById_returnsNullWhenNotFound() {
        // Arrange
        String id = "thisIdDoesNotExist";

        // pretend the db has no entry for this id
        when(repo.findById(id)).thenReturn(Optional.empty());

        // Act
        ExpenseDTO actual = service.getById(id);

        // Assert
        assertThat(actual).isNull();
    }
  
    void happyPath_searchExpensesByMerchant_returnsExpenseDTOList() {
        // Arrange
        // prep the value that should be in the db
        LocalDate date = LocalDate.now();
        Expense savedExpense1 = new Expense(date, new BigDecimal("50.00"), "Video Games" );
        Expense savedExpense2 = new Expense(date, new BigDecimal("99.99"), "Walmart");
        Expense savedExpense3 = new Expense(date, new BigDecimal("2000.89"), "Walmart");
        savedExpense1.setId("expense-1");
        savedExpense2.setId("expense-2");
        savedExpense3.setId("expense-3");

        List<Expense> walmartExpenses = new ArrayList<>();
        walmartExpenses.add(savedExpense2);
        walmartExpenses.add(savedExpense3);

        // prep our expected value to compare with for the assert
        List<ExpenseDTO> expected = new ArrayList<>();
        expected.add(new ExpenseDTO("expense-2", date, new BigDecimal("99.99"), "Walmart"));
        expected.add(new ExpenseDTO("expense-3", date, new BigDecimal("2000.89"), "Walmart"));

        // "put" the fake entry in the db
        when(repo.findByExpenseMerchant("Walmart")).thenReturn(walmartExpenses);

        // ACT
        List<ExpenseDTO> actual = service.searchByExpenseMerchant("Walmart");

        // Assert
        // compare expected to actual
        assertThat(actual).isEqualTo(expected);
    }
}














