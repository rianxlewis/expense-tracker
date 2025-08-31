import java.time.LocalDate;

public class Expense {
    private static int nextId = 0;  // auto-increment ID counter

    private final int id;           // unique ID for each expense
    private double amount;
    private String category;
    private String description;
    private LocalDate date;

    // Constructor
    public Expense(double amount, String category, String description, LocalDate date) {
        this.id = nextId++;  // assign and increment global counter
        this.amount = amount;
        this.category = category;
        this.description = description == null ? "" : description;
        this.date = date;
    }

    public Expense(int id, double amount, String category, String description, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description == null ? "" : description;
        this.date = date;

    }

    // Getters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static void setNextId(int nextId) {
        Expense.nextId = nextId;
    }

    // For printing
    @Override
    public String toString() {
        return String.format(
                "ID: %d | Amount: $%.2f | Category: %s | Date: %s | Description: %s",
                id, amount, category, date, description.isEmpty() ? "(none)" : description
        );
    }
}

