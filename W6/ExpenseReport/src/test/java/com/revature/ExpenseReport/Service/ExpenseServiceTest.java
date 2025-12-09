package com.revature.ExpenseReport.Service;


@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest{

    //Fields
    @Mock
    private ExpenseRepository repo;

    @InjectMocks
    private ExpenseService



    //Constructors


    //Methods




        //Arrange - prepping any resources/objects we need to run our test

        //Act- the function /action of executing the code/logic we're testing

        //Assert - the final check to pass or fail


    /*
    public ExpenseDTO getById(String id){
        Optional<Expense> res = repository.findById(id);

        return (res.isEmpty()) ? null : ExpenseToDto(res.get());

    }

    */

    void happyPath_getExpenseId_returnsExpenseDTO(){
        //Arrange
        String id = "thisIsTheId";
        Expense savedExpense = new Expense(LocalDate.now(), new BigDecimal("50.00"), "Video Games");
        savedExpense.setId(id);

        ExpenseDTO expected = new ExpenseDTO(id, date, new BigDecimal("50.00"), "Video Games");


        // ACT
        service.getById(id);


    }
}
