package com.revature.ExpenseReport;

import com.revature.ExpenseReport.Model.Expense;
import com.revature.ExpenseReport.Repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ExpenseReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseReportApplication.class, args);
	}

    @Bean
    CommandLineRunner seedData (ExpenseRepository repository) {
        return args -> {
            var e1 = new Expense(new Date(), 59.99, "Walmart");
            var e2 = new Expense(new Date(), 14.75, "Starbucks");
            var e3 = new Expense(new Date(), 99.88, "Buffalo Wild Wings");

            repository.saveAll(List.of(e1, e2, e3));
        };
    }
}