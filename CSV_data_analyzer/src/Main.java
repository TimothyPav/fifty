public class Main {
    public static void main(String[] args) {
        CSV_Analyzer csv = new CSV_Analyzer("data/employee_data.csv", ',');
        Tuple<Integer, String> test = csv.get_column_by_name("Performance Score");
        System.out.println(test.first);
        System.out.println(test.second);

    }
}