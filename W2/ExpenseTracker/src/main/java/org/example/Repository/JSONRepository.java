package org.example.Repository;

import com.google.gson.Gson;
import org.example.Expense;
import java.io.FileWriter;
import java.util.List;

public class JSONRepository implements IRepository{
    // Fields
    private String filename = "expenses.json";
    private Gson gson = new Gson();

    // Constructor
    public JSONRepository() {}

    // Methods
    public void saveExpenses(List<Expense> expenses) {
        try {
            FileWriter file = new FileWriter(filename, false);
            gson.toJson(expenses, file);
            file.close();
            System.out.println("File written successfully");
        } catch (Exception e){
            System.out.println("Error writing file.");
        }
    }
}
