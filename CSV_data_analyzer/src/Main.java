public class Main {
    public static void main(String[] args) {
        CSV_Analyzer csv = new CSV_Analyzer("data/employee_data.csv", ',');
         int test = csv.get_column_by_name("ID");
        //String test = csv.coord(1,5);
        System.out.println(test);

    }
}