/**
 * Main expense tracking application for personal finance management.
 * Features: Add, edit, delete expenses with CSV persistence.
 * @author Rian Lewis
 * @version 1.0
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseTable.loadFromFile();
        System.out.println("| | Welcome to Expense Tracker | |\n\nHere are your current expenses:\n");
        generateMenu();

    }

    public static void generateMenu() {
        Scanner in = new Scanner(System.in);
        while (true) {
            ExpenseTable.printExpenses(ExpenseTable.expenses);
            System.out.print("""
                    1. Add New Expense
                    2. Search For Expense (by Category/Description)
                    3. Edit/Delete Expense
                    4. Exit Application
                    
                    Choose an option:\s""");

            String choice = in.nextLine().trim();
            switch (choice) {
                case "1" -> ExpenseTable.addNewExpense(in);
                case "2" -> ExpenseTable.searchExpenses(in);
                case "3" -> ExpenseTable.editExpense(in);
                case "4" -> {
                    ExpenseTable.saveToFile();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}