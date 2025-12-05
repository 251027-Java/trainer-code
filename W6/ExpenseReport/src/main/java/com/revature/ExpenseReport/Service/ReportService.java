package com.revature.ExpenseReport.Service;

import com.revature.ExpenseReport.Controller.ExpenseDTO;
import com.revature.ExpenseReport.Controller.ReportDTO;
import com.revature.ExpenseReport.Controller.ReportWOIDDTO;
import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Model.Report;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import com.revature.ExpenseReport.Repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReportService {
    // Fields
    private final ReportRepository repository;
    private final ExpenseService expenseService;

    // Constructor
    public ReportService(ReportRepository repository, ExpenseService expenseService) {
        this.repository = repository;
        this.expenseService = expenseService;
    }

    // Methods
    public List<ReportDTO> getAllReports() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public ReportDTO getByTitle(String reportTitle) {
        return toDTO(repository.findByReportTitle(reportTitle));
    }

    public ReportDTO getById(String reportId) {
        Report r = repository.findById(reportId).orElse(null);
        return r != null ? toDTO(r) : null;
    }

    public List<ReportDTO> getByReportStatus(String reportStatus) {
        return repository.findByReportStatus(reportStatus).stream().map(this::toDTO).toList();
    }

    public ReportDTO create(ReportWOIDDTO r) {
        // save report first
        Report report = repository.save( new Report(r.reportTitle(), r.reportStatus()) );

        // then get id
        // add report id to all expenses in expense list
        List<Expense> expenses = r.reportExpenses().stream().peek((expense -> {
            // Set report in expenses
            expense.setReport(report);

            // Update expense
            expenseService.update(expense.getExpenseId(),
                    new ExpenseDTO(expense.getExpenseId(), expense.getExpenseDate(),
                            expense.getExpenseValue(), expense.getExpenseMerchant(),
                            expense.getReport()));

        })).toList();

        // update report with expense list
        report.setReportExpenses(expenses);

        // return updated report converted to DTO
        return toDTO( repository.save(report));
    }

    public ReportDTO updateStatus(String reportId) {
        Report report =
                repository.findById(reportId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        report.setReportStatus("Reimbursed");
        return toDTO(repository.save(report));
    }

    public void delete(String reportId) {
        repository.deleteById(reportId);
    }

    public ReportDTO toDTO(Report report) {
        return new ReportDTO(report.getReportId(), report.getReportTitle(),
                report.getReportStatus(), report.getReportExpenses());
    }
}
