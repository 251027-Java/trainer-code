package org.example.Repository;

import org.example.Expense;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class TextRepository implements IRepository{
    // Fields
    private String filename = "expenses.txt";

    // Constructor
    public TextRepository() {}

    // Methods
    public void saveExpenses(List<Expense> expenses){
        try {
            FileWriter file = new FileWriter(filename, false);
            PrintWriter writer = new PrintWriter(file, true);
            writer.println(expenses);
            file.close();
            System.out.println("File written successfully");
        } catch (Exception e){
            System.out.println("Error writing file.");
        }
    }
}
