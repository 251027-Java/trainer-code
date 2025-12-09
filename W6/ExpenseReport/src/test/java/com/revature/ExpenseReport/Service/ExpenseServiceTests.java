package test.java.com.revature.ExpenseReport.Service;


// JUnit & Mochito (fakes the repo layer)
@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {

    // Fields
    @Mock // generate a fake version of the repository to test
    private ExpenseRepository repo;

    @InjectMock // service uses the fake repo (SpringBoot deals with injection)
    private ExpenseService service;

    // Constructor

    // Methods


    // Tripple A

    // Arrange - prepping any resources/objects/

    // Act - the action/function of executing the code/logic we're testing

    // Assert - the final check to pass or fail

    /*
    public ExpnseDTO getById(String id) {
        Optional<Expnse> res = repository.findById(id); // expect to return an expense

        return (res.isEmpty()) ? null : ExpenseToDto(res.get());
    }

    "HAPPY PATH" - no edgecase, odd behavior, everything goes according to plan
    */

    void happyPath_getExpenseById_returnsExpenseDTO() {
        // Arrange
        // prep the value that should be in the DB
        String id = "thisIsTheId";
        LocalDate date = LocalDate.now();
        Expense savedExpense = new Expense(new BigDecimal("50.00"), "Video Games" );
        savedExpnese.setId(id);

        // Expecting to return an ExpenseDTO (DTO = Data Transfer Object)
        // prep our expected value to compare with for the assert 
        ExpenseDTO expected = new ExpenseDTO(id, date, new BigDecimal("50.00"), "Video Games");

        // "put" the fake entry into the DB
        when(repo.findById(id)).thenReturn(Optional.of(savedExpnese));

        // Act
        ExpnseDTO actual = service.getById(id);

        // Assert
        // compare expected to actual
        assertThat(actual).isEqualTo(expected);
    }

    void happyPath_updateExpense_returnUpdatedExpenseDTO() {
        // Arrange - prep the fake scenario of the value that should be in the DB
        String id = "thisIsTheId";
        LocalDate originalDate = LocalDate.now().minusDays(1);
        LocalDate updatedDate = localDate.now();

        // Expense that exists within the DB before the update
        Expense existingExpense = new Expense(new BigDecimal("50.00"), "Video Games");
        existingExpense.setId(id);
        existingExpense.setDate(originalDate);

        // Update DTO with new values
        Expense updateDTO = new Expense(id, updatedDate, new BigDecimal("100.00"), "Write Offs");

        // New values to update DB with
        Expense updatedExpense = new Expense(id, updatedDate, new BigDecimal("100.00"), "Write Offs");
        updateExpense.setId(id);
        updateExpense.setDate(updatedDate);

        // "put" the fake entry into the DB when update() calls repo.findById()
        when(repo.findById(id)).thenReturn(Optional.of(existingExpense));

        // "replace" the old entry with the updated entry based on the id when update calls repo.save()
        when(repo.save(any(Expense.class))).thenReturn(updatedExpense);

        // Act
        ExpenseDTO actual = service.update(id, updateExpense);

        // Assert
        assertThat(actual).isEqualTo(expected);
        verify(repo).findById(id);
        verify(repo).save(any(Expense.class));
    }
}
