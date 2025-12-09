package com.revature.ExpenseReport.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
public class Report {

    // Fields
    @Id
    @GeneratedValue
    private String reportId;
    private String reportTitle;
    private String reportStatus;

    @OneToMany(mappedBy = "report")
    private List<Expense> reportExpenses = new ArrayList<>();

    // Constructor
    public Report(String title, String status) {
        this.reportTitle = title;
        this.reportStatus = status;
    }

    // Methods
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public List<Expense> getReportExpenses() {
        return reportExpenses;
    }

    public void setReportExpenses(List<Expense> reportExpenses) {
        this.reportExpenses = reportExpenses;
    }
}
