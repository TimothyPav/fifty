import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        CSV_Analyzer csv = new CSV_Analyzer("data/employee_data.csv", ',');
        Tuple<Integer, String> test = csv.get_column_by_name("Name");
        System.out.println(csv.unique_values(test));


    }
}