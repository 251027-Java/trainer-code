package com.revature.ExpenseReport.Repository;

import com.revature.ExpenseReport.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, String> {
    Report findByReportTitle(String reportTitle);
    List<Report> findByReportStatus(String reportStatus);
}
