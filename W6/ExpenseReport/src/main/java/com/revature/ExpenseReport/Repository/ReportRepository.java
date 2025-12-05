package com.revature.ExpenseReport.Repository;

<<<<<<< HEAD
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<> {
}
=======
import com.revature.ExpenseReport.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, String> {}
>>>>>>> origin
