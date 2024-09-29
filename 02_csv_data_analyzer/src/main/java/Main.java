import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSV_Analyzer csv = new CSV_Analyzer("data/test.csv", ',');

        Tuple<Integer, String> salary = csv.get_column_by_name("Salary"); //get column index based off name of column
        System.out.println(csv.mean(salary)); // return double that is the mean of column
        System.out.println(csv.range(salary)); // return double range
        System.out.println(csv.median(salary)); // return double median
        System.out.println(csv.most_frequent(salary).second); // return tuple where .second is the value u want which is an array
        System.out.println(csv.standard_deviation(salary)); // return double standard deviation

        Tuple<Integer, String> department = csv.get_column_by_name("Department"); // get column index but department is a string not number
        System.out.println(csv.shortest_longest(department).second); // return the shortest string or multiple if have same length
        System.out.println(csv.unique_values(department)); // return int of unique vals in the column
        System.out.println(csv.most_frequent(department).second); // return tuple.second which is list is more than 1 frequent value

        Histogram hist = new Histogram("title", "x_title", "y_title");
        List<String> dept = csv.col_to_array(department);
        List<Double> sal = csv.col_to_array(salary);
        hist.add_series("series name", dept, sal);
        // hist.display();

        Tuple<Integer, String> age = csv.get_column_by_name("Age");
        List<Integer> list_age = csv.col_to_array(age);
        Scatter_Plot plot = new Scatter_Plot("title", "x_title", "y_title");
        List<Double> double_age = plot.convertToDouble(list_age);
        plot.add_data(sal, double_age);
        // plot.display("series name");
    }
}