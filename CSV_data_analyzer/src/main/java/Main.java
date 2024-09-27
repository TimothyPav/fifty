import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSV_Analyzer csv = new CSV_Analyzer("data/employee_data.csv", ',');

        Tuple<Integer, String> Salary = csv.get_column_by_name("Salary");
        List<Integer> salary = csv.col_to_array(Salary);

        Tuple<Integer, String> Name = csv.get_column_by_name("Name");
        List<String> name = csv.col_to_array(Name);

        Histogram hist = new Histogram("Worker Pay", "Names", "Salary");
        hist.add_series("Salaries", name, salary);
        hist.display();

    }
}