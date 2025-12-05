package com.revature.ExpenseReport.Controller;

import com.revature.ExpenseReport.Model.Report;
import com.revature.ExpenseReport.Service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses/reports")
public class ReportController {
    // Fields
    private final ReportService service;

    // Constructor
    public ReportController(ReportService service) {
        this.service = service;
    }

    // Methods

    // GET Display all reports
    @GetMapping
    public List<ReportDTO> getAllReports() {
        return service.getAllReports();
    }

    // GET Display report by title
    @GetMapping("/title")
    public ReportDTO getByTitle(@RequestParam String title) {
        return service.getByTitle(title);
    }

    // GET Display report by id
    @GetMapping("/{id}")
    public ReportDTO getById(@PathVariable String id) {
        return service.getById(id);
    }

    // GET Display reports with specific status
    //TODO: Decide report status is boolean or String
    @GetMapping("/status") // /api/expenses/reports/status?s=reimbursed
    // /api/expenses/reports/status?s=notreimbursed
    public List<ReportDTO> getByStatus(@RequestParam String s) {
        return service.getByReportStatus(s);
    }

    // POST Create a new report
    @PostMapping
    public ReportDTO create(@RequestBody ReportWOIDDTO report) {
        // report only has title and list of expenses
        // id is generated, and status default
        return service.create(report);
    }

    // PATCH Update report status
    @PatchMapping("/{id}")
    public ReportDTO updateStatus(@PathVariable String id) {
        return service.updateStatus(id);
    }

    // DELETE Delete an old report
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
