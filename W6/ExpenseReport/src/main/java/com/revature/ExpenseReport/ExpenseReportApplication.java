package com.revature.ExpenseReport;

import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Model.Report;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import com.revature.ExpenseReport.Repository.ReportRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ExpenseReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseReportApplication.class, args);
	}

    @Bean // Bean is a single method that is run after the application is started
    CommandLineRunner seedEx(ExpenseRepository expenseRepository,
                             ReportRepository reportRepository) {
        return args -> {
            var r = new Report("My_Report", "Not Reimbursed");

            var e1 = new Expense(LocalDate.now(), new BigDecimal("59.99"), "Walmart");
            e1.setReport(r);

            var e2 = new Expense(LocalDate.now().minusDays(1), new BigDecimal("14.75"), "Starbucks");
            e2.setReport(r);

            var e3 = new Expense(LocalDate.now().minusDays(2), new BigDecimal("99.88"), "Buffalo Wild Wings");
            e3.setReport(r);

            List<Expense> expenses = List.of(e1, e2, e3);
            r.setReportExpenses(expenses);

            reportRepository.save(r);
            expenseRepository.saveAll(expenses);
        };
    }

}