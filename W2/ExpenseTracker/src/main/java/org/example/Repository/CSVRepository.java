package org.example.Repository;

import org.example.Expense;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;

public class CSVRepository implements IRepository{
    // Fields
    private String filename = "expenses.csv";

    // Constructor
    public CSVRepository() {}

    // Methods
    public void saveExpenses(List<Expense> expenses){
        try {
            BufferedWriter writer = new BufferedWriter( new FileWriter(filename));

            writer.write("id, date, value, merchant");
            writer.newLine();

            for( Expense ex : expenses ) {
                writer.write(ex.toCSV());
                writer.newLine();
            }
            writer.flush();
            writer.close();
            System.out.println("File written successfully");
        } catch (Exception e){
            System.out.println("Error writing file.");
        }
    }
}
