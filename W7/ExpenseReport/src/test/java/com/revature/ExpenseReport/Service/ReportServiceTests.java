package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Controller.ReportDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Model.Report;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import com.revature.ExpenseReport.Repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTests {
    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void happyPath_getReportById_returnsExpenseDTO() {
        /*String expenseId = "thisIsTheId";
        LocalDate date = LocalDate.now();
        Expense savedExpense = new Expense(date, new BigDecimal("50.00"), "Video Games");
        savedExpense.setId(expenseId);
        ExpenseDTO expectedExpense = new ExpenseDTO(expenseId, date, new BigDecimal("50.00"), "Video Games");
        List<ExpenseDTO> expenses = new ArrayList<>(); expenses.add(expectedExpense);
        expenses.add(expectedExpense);
        */

        String reportId = "thisIsTheReportId";
        Report report = new Report("Groceries", "Accepted");
        report.setReportId(reportId);

        ReportDTO expectedReport = new ReportDTO(reportId, "Groceries", "Accepted", new ArrayList<>());
        //ReportDTO expectedReport = new ReportDTO(reportId, "Groceries", "Accepted", expenses);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        ReportDTO actualReport = reportService.getById(reportId);
        assertThat(actualReport).isEqualTo(expectedReport);
    }

}




