import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ExpenseTable {
    static final ArrayList<Expense> expenses = new ArrayList<>();
    private static final String FILE_NAME = "expenses.csv";

    public static void addNewExpense(Scanner in) {
        String description = readDescription(in);

        double amount = readAmount(in);
        String category = readCategory(in);
        LocalDate date = readDate(in);

        expenses.add(new Expense(amount, category, description, date));
    }

    public static void editExpense(Scanner in) {
        Expense expense;
        String input;

        while (true) {
            System.out.print("Enter the ID of the expense you would like to edit, or leave blank to return to menu: ");
            input = in.nextLine().trim();
            if (input.isEmpty()) {
                return;
            }

            try {
                int id = Integer.parseInt(input);

                // Lookup by ID
                expense = expenses.stream()
                        .filter(e -> e.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (expense == null) {
                    System.out.println("Expense with ID " + id + " does not exist.\n\n");
                    continue;
                }
                break;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID.\n\n");
            }
        }

        while (true) {
            System.out.println("""
                1. Edit Amount
                2. Edit Category
                3. Edit Date
                4. Edit Description
                5. Delete Expense

                Choose an option:
                """);
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    double amount = readAmount(in);
                    expense.setAmount(amount);
                    System.out.println("✓ Amount updated successfully!\n");
                }
                case "2" -> {
                    String category = readCategory(in);
                    expense.setCategory(category);
                    System.out.println("✓ Category updated successfully!\n");
                }
                case "3" -> {
                    LocalDate date = readDate(in);
                    expense.setDate(date);
                    System.out.println("✓ Date updated successfully!\n");
                }
                case "4" -> {
                    String description = readDescription(in);
                    expense.setDescription(description);
                    System.out.println("✓ Description updated successfully!\n");
                }
                case "5" -> {
                    System.out.print("Are you sure you want to delete this expense? Y or Yes to confirm: ");
                    String confirm = in.nextLine().trim();
                    if (confirm.equalsIgnoreCase("y") || confirm.trim().equalsIgnoreCase("yes")) {
                        expenses.remove(expense);
                        System.out.println("✓ Expense deleted successfully!\n");
                        break;
                    }
                    System.out.println("Returning to menu...");
                }
                default -> {
                    System.out.println("Invalid choice.\n\n");
                    continue;
                }
            }
            return;
        }
    }

    private static double readAmount(Scanner in) {
        double amount;
        String input;
        while (true) {
            System.out.print("Enter Amount: ");
            input = in.nextLine().trim();
            try {
                amount = Double.parseDouble(input);
                if (amount < 0) {
                    System.out.println("Amount must be greater than or equal to 0.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount greater than or equal to 0.\n");
            }
        }
    }

    private static LocalDate readDate(Scanner in) {
        String input;
        int month;
        int day;
        int year;
        int minYear = 1900;
        int maxYear = 2100;

        String errMsgYear = "Please enter a valid year from 1900-" + maxYear + ".\n";
        while (true) {
            System.out.print("Enter Year: ");
            input = in.nextLine().trim();
            try {
                year = Integer.parseInt(input);
                if (year < minYear || year > maxYear) {
                    System.out.println(errMsgYear);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(errMsgYear);
            }
        }

        String errMsgMonth = "Month must be between 1 and 12.";
        while (true) {
            System.out.print("Enter Month (must be integer from 1 to 12): ");
            input = in.nextLine().trim();
            try {
                month = Integer.parseInt(input);
                if (month < 1 || month > 12) {
                    System.out.println(errMsgMonth);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(errMsgMonth);
            }
        }

        String errMsgDay = "Day must be between 1 and " + YearMonth.of(year, month).lengthOfMonth() + ".\n";
        while (true) {
            System.out.print("Enter Day: ");
            input = in.nextLine().trim();
            try {
                day = Integer.parseInt(input);
                if (day < 1 || day > YearMonth.of(year, month).lengthOfMonth()) {
                    System.out.println(errMsgDay);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(errMsgDay);
            }
        }
        return LocalDate.of(year, month, day);
    }

    private static String readCategory(Scanner in) {
        String input;
        while (true) {
            System.out.print("Enter category: ");
            input = in.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Category cannot be empty");
                continue;
            }

            if(input.contains(",")) {
                System.out.println("Input cannot contain commas");
                continue;
            }
            return input;
        }
    }

    private static String readDescription(Scanner in) {
        String input;
        while (true) {
            System.out.print("Enter description (press enter for none: ");
            input = in.nextLine().trim();

            if(input.contains(",")) {
                System.out.println("Input cannot contain commas");
                continue;
            }
            return input;
        }
    }

    public static void searchExpenses(Scanner in) {
        while (true) {
            System.out.print("Enter search term, or leave blank to return to menu: ");
            String input = in.nextLine().trim().toLowerCase();

            if (input.isEmpty()) {
                System.out.println("Returning to menu...");
                return;
            }
            ArrayList<Expense> results = expenses.stream()
                    .filter(e -> e.getCategory().toLowerCase().contains(input) ||
                            e.getDescription().toLowerCase().contains(input))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (results.isEmpty()) {
                System.out.println("No results found. Returning to menu...");
                return;
            }

            printExpenses(results);
        }
    }

    public static void printExpenses(ArrayList<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses have been added");
            return;
        }

        expenses.sort(Comparator.comparing(Expense::getDate));


        // Print header
        System.out.printf("%-5s %-10s %-15s %-12s %-30s%n",
                "ID", "Amount", "Category", "Date", "Description");
        System.out.println("--------------------------------------------------------------------------");

        // Print each expense in aligned columns
        for (Expense e : expenses) {
            System.out.printf("%-5d $%-9.2f %-15s %-12s %-30s%n",
                    e.getId(),
                    e.getAmount(),
                    e.getCategory(),
                    e.getDate(),
                    e.getDescription().isEmpty() ? "(none)" : e.getDescription());
        }

        System.out.println("\n");
    }

    public static void saveToFile() {

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME, false))) {
            // header row
            String[] header = { "ID", "Amount", "Category", "Date", "Description" };
            writer.writeNext(header);

            for (Expense expense : expenses) {
                String[] row = {
                        String.valueOf(expense.getId()),
                        String.format("%.2f", expense.getAmount()),
                        expense.getCategory(),
                        expense.getDate().toString(),
                        expense.getDescription().isEmpty() ? "(none)" : expense.getDescription()
                };
                writer.writeNext(row);
            }

            System.out.println("Expenses saved successfully to " + FILE_NAME + " .");
        }
        catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    public static void loadFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (CSVReader reader = new CSVReader(new FileReader(FILE_NAME))) {
            String[] nextLine;
            reader.readNext(); // skip header row
            while ((nextLine = reader.readNext()) != null) {
                int id = Integer.parseInt(nextLine[0]);
                double amount = Double.parseDouble(nextLine[1]);
                String category = nextLine[2];
                LocalDate date = LocalDate.parse(nextLine[3]);
                String description = nextLine[4].equals("(none)") ? "" : nextLine[4];

                expenses.add(new Expense(id, amount, category, description, date));
            }

            if (!expenses.isEmpty()) {
                int maxId = expenses.stream()
                        .mapToInt(Expense::getId)
                        .max()
                        .orElse(0);

                Expense.setNextId(maxId + 1);
            }
        }
        catch (IOException | CsvException e) {
            System.out.println("Error reading expense file: " + e.getMessage());
        }
    }


}

