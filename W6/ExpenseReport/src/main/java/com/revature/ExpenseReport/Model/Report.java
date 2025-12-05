package com.revature.ExpenseReport.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
@Data
@ToString(exclude = {"reportExpenses"})
@NoArgsConstructor
public class Report {
    // Fields
    @Id @GeneratedValue private String reportId;

    @Column(name = "reportTitle")
    private String reportTitle;

    @Column(columnDefinition = "varchar(255) default 'Not Reimbursed'")
    private String reportStatus;

    @OneToMany(mappedBy = "report")
    @JsonManagedReference
    private List<Expense> reportExpenses = new ArrayList<>(); // 1:M relationship

    public Report(String title, String status) {
        this.reportTitle = title;
        this.reportStatus = status;
    }

    // Methods
}
