# Expense Tracker

A simple Java console application for personal expense management with persistent CSV storage.

## Features

- **Add Expenses**: Record expenses with amount, category, description, and date
- **Edit/Update**: Modify any field of existing expenses
- **Delete**: Remove expenses with confirmation prompt
- **Search**: Find expenses by category or description (case-insensitive)
- **Data Persistence**: Automatic saving to CSV file for data retention between sessions
- **Input Validation**: Comprehensive validation for all user inputs including:
  - Amount validation (non-negative values)
  - Date validation (with leap year support)
  - CSV-safe input (prevents comma insertion that could corrupt data)
- **Auto-incrementing IDs**: Unique identifier system that persists across sessions
- **Sorted Display**: Expenses automatically sorted by date for easy viewing

## Prerequisites

- Java 17 or higher
- OpenCSV library (4.6 or compatible version)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/rianxlewis/expense-tracker.git
cd expense-tracker
```

2. Ensure you have the OpenCSV library in your project's lib folder or classpath

3. Compile the project:
```bash
javac -cp "lib/opencsv-4.6.jar:." *.java
```
On Windows, use semicolon instead of colon:
```bash
javac -cp "lib/opencsv-4.6.jar;." *.java
```

## Usage

Run the application:
```bash
java -cp "lib/opencsv-4.6.jar:." Main
```
On Windows:
```bash
java -cp "lib/opencsv-4.6.jar;." Main
```

### Menu Options

1. **Add New Expense**: Enter expense details including description, amount, category, and date
2. **Search For Expense**: Search by category or description keywords
3. **Edit/Delete Expense**: Modify existing expenses by ID or remove them
4. **Exit Application**: Saves all data and exits

### Example Workflow

```
| | Welcome to Expense Tracker | |

ID    Amount     Category        Date         Description
--------------------------------------------------------------------------
2     $45.99     Food            2024-03-15   Grocery shopping
1     $12.50     Transport       2024-03-14   Bus fare
0     $89.99     Entertainment   2024-03-10   Concert tickets

1. Add New Expense
2. Search For Expense (by Category/Description)
3. Edit/Delete Expense
4. Exit Application

Choose an option: 1
Enter description: Coffee with friends
Enter Amount: 15.75
Enter category: Food
Enter Year: 2024
Enter Month (must be integer from 1 to 12): 3
Enter Day: 16
```

## Project Structure

```
expense-tracker/
│
├── Main.java           # Entry point and menu system
├── Expense.java        # Expense model class with ID management
├── ExpenseTable.java   # Business logic and data operations
├── expenses.csv        # Data file (auto-generated)
└── README.md          # This file
```

## Data Storage

Expenses are stored in CSV format with the following structure:
```csv
ID,Amount,Category,Date,Description
0,89.99,Entertainment,2024-03-10,Concert tickets
1,12.50,Transport,2024-03-14,Bus fare
```

## Key Design Decisions

- **Static ID Counter**: Ensures unique IDs across application restarts by finding the maximum ID on load
- **CSV Validation**: Prevents commas in user input to maintain data integrity
- **Stream API**: Utilizes Java 8+ streams for efficient searching and filtering
- **Separation of Concerns**: Clean separation between UI (Main), data model (Expense), and business logic (ExpenseTable)

## Technologies Used

- **Java 17**: Core language
- **OpenCSV**: CSV file handling
- **Java Time API**: Modern date handling with LocalDate
- **Java Stream API**: Efficient data filtering and processing

## Future Enhancements

Potential improvements for future versions:
- Add expense categories as enums for consistency
- Implement monthly/yearly expense summaries
- Add budget tracking and alerts
- Export reports in multiple formats (PDF, Excel)
- Add data visualization for spending patterns

## Author

**Rian Lewis**  
Student Developer

## License

This project is open source and available for educational purposes.
