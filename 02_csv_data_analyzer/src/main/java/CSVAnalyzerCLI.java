import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class CSVAnalyzerCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static CSV_Analyzer csv;

    public static void main(String[] args) {
        System.out.println("Welcome to CSV Analyzer CLI");
        initializeCSV();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    analyzeColumn();
                    break;
                case 2:
                    createHistogram();
                    break;
                case 3:
                    createScatterPlot();
                    break;
                case 4:
                    initializeCSV();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void initializeCSV() {
        System.out.print("Enter the CSV file name (must be in the data/ directory): ");
        String fileName = scanner.nextLine();
        csv = new CSV_Analyzer("data/" + fileName, ',');
        System.out.println("CSV file loaded successfully.");
    }

    private static void displayMenu() {
        System.out.println("\nCSV Analyzer Menu:");
        System.out.println("1. Analyze a column");
        System.out.println("2. Create a histogram");
        System.out.println("3. Create a scatter plot");
        System.out.println("4. Load a different CSV file");
        System.out.println("5. Exit");
    }

    private static void analyzeColumn() {
        System.out.print("Enter the column name to analyze: ");
        String columnName = scanner.nextLine();
        Tuple<Integer, String> column = csv.get_column_by_name(columnName);

        if (column.first == -1) {
            System.out.println("Column not found. Please try again.");
            return;
        }

        System.out.println("Analysis for column: " + columnName);

        if (column.second.equals("String")){
            System.out.println("Most Frequent: " + csv.most_frequent(column).second);
            System.out.println("Shortest/Longest: " + csv.shortest_longest(column).first + " / " + csv.shortest_longest(column).second);
            System.out.println("Unique Values: " + csv.unique_values(column));
        } else {
            System.out.println("Mean: " + csv.mean(column));
            System.out.println("Range: " + csv.range(column));
            System.out.println("Median: " + csv.median(column));
            System.out.println("Most Frequent: " + csv.most_frequent(column).second);
            System.out.println("Standard Deviation: " + csv.standard_deviation(column));
        }
    }

    private static void createHistogram() {
        System.out.print("Enter the column name for x-axis: ");
        String xColumn = scanner.nextLine();
        System.out.print("Enter the column name for y-axis: ");
        String yColumn = scanner.nextLine();

        Histogram hist = new Histogram("Histogram", xColumn, yColumn);
        Tuple<Integer, String> xData = csv.get_column_by_name(xColumn);
        Tuple<Integer, String> yData = csv.get_column_by_name(yColumn);
        List<String> xList = csv.col_to_array(xData);
        List<Double> yList = csv.col_to_array(yData);
        hist.add_series(xColumn + " vs. " + yColumn, xList, yList);
        hist.display();
    }

    private static void createScatterPlot() {
        System.out.print("Enter the column name for x-axis: ");
        String xColumn = scanner.nextLine();
        System.out.print("Enter the column name for y-axis: ");
        String yColumn = scanner.nextLine();

        Scatter_Plot plot = new Scatter_Plot("Scatter Plot", xColumn, yColumn);
        Tuple<Integer, String> xData = csv.get_column_by_name(xColumn);
        Tuple<Integer, String> yData = csv.get_column_by_name(yColumn);

        if (xData.second.equals("String") || yData.second.equals("String")){
            System.out.println("Cannot plot strings/characters on scatter plot");
            return;
        }

        List<Double> xList;
        if (xData.second.equals("Integer")) {
            List<Integer> int_xList = csv.col_to_array(xData);
            xList = plot.convertToDouble(int_xList);
        } else {
            xList = csv.col_to_array(xData);
        }

        List<Double> yList;
        if (yData.second.equals("Integer")) {
            List<Integer> int_yList = csv.col_to_array(yData);
            yList = plot.convertToDouble(int_yList);
        } else {
            yList = csv.col_to_array(yData);
        }

        plot.add_data(xList, yList);
        plot.display(xColumn + " vs. " + yColumn);
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid number. Please try again.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character
        return input;
    }
}