import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;


import static java.lang.Math.min;

public class CSV_Analyzer {
    private final File file;
    private Scanner scanner;
    private final char separator;
    private final int num_rows;
    private final int num_cols;


    public CSV_Analyzer(String file_name, char separator) {
        file = new File(file_name);
        this.separator = separator;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + file_name + "' not found");
        }

        num_rows = get_num_of_rows();
        num_cols = get_num_of_cols();
    }

    private static void print(Object... objects) {
        String result = Arrays.stream(objects)
                .map(obj -> obj == null ? "null" : obj.toString())
                .collect(Collectors.joining(" "));
        System.out.println(result);
    }


    private int get_num_of_cols() {
        int col = 1;
        int quotes = 0;
        Scanner scanner = build_scanner();

        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == separator && quotes % 2 == 0) col++;
                else if (line.charAt(i) == '"') quotes++;
            }
        }
        return col;
    }

    private int get_num_of_rows() {
        int row = 0;
        Scanner scanner = build_scanner();
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            row++;
        }
        return row;
    }

    private Scanner build_scanner() {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + file + "' not found");
        }
        return scanner;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> col_to_array(Tuple<Integer, String> col) {
        List<Integer> integerList = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        for (int row = 2; row < num_rows + 1; row++) {
            String val = coord(row, col.first);
            switch (col.second) {
                case "Integer" -> integerList.add(Integer.parseInt(val));
                case "Double" -> doubleList.add(Double.parseDouble(val));
                case "String" -> stringList.add(val);
            }
        }

        return switch (col.second) {
            case "Integer" -> (List<T>) integerList;
            case "Double" -> (List<T>) doubleList;
            case "String" -> (List<T>) stringList;
            default -> throw new IllegalArgumentException("Unsupported type of column ");
        };
    }

    public Tuple<Integer, String> get_column_by_name(String name) {
        int i = 1;
        while (!coord(1, i).equals(name)) {
            if (i >= num_cols) return new Tuple<>(-1, "");
            i++;
        }
        String example_value = coord(2, i);

        try {
            Integer.parseInt(example_value);
            return new Tuple<>(i, "Integer");
        } catch (NumberFormatException _) {
        }

        try {
            Double.parseDouble(example_value);
            return new Tuple<>(i, "Double");
        } catch (NumberFormatException _) {
        }

        try {
            java.time.LocalDate.parse(example_value);
            return new Tuple<>(i, "Date");
        } catch (java.time.format.DateTimeParseException _) {
        }

        return new Tuple<>(i, "String");
    }

    public String coord(int row, int col) {
        // convert from 1 indexed to 0 indexed
        row--;
        col--;

        int curr_col = 0;
        int curr_row = 0;
        int quotes = 0;
        boolean built = false;
        StringBuilder sb = new StringBuilder();

        Scanner scanner = build_scanner();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (curr_row == row) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '"') quotes++; // account for commas that are part of a cell

                    if (line.charAt(i) == separator && quotes % 2 == 0) {
                        curr_col++;
                    }
                    if (curr_col == col && line.charAt(i) != separator) {
                        sb.append(line.charAt(i));
                        built = true;
                    } else if (built) break;
                }
            }
            curr_row++;
            if (curr_row > row) break;
        }

        return sb.toString();
    }

    public double mean(Tuple<Integer, String> col) {
        if (!col.second.equals("Integer") && !col.second.equals("Double")) {
            System.err.println("Mean must use column with Integers or Doubles");
            return -1;
        }
        String type = col.second;

        int row = 2;
        double mean = 0;
        while (row != num_rows + 1) {
            if (type.equals("Integer")) mean += Integer.parseInt(coord(row, col.first));
            else mean += Double.parseDouble(coord(row, col.first));
            row++;
        }
        return mean / (num_rows - 1);
    }

    public double[] min_max(Tuple<Integer, String> col) {
        if (!col.second.equals("Integer") && !col.second.equals("Double")) {
            System.err.println("Column must use Integers or Doubles");
            System.exit(-1);
        }
        String type = col.second;

        int row = 2;
        double[] min_max_vals = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        while (row != num_rows + 1) {
            double val = Double.parseDouble(coord(row, col.first));

            min_max_vals[0] = Math.min(min_max_vals[0], val);
            min_max_vals[1] = Math.max(min_max_vals[1], val);
            row++;
        }
        return min_max_vals;
    }

    public double range(Tuple<Integer, String> col) {
        if (!col.second.equals("Integer") && !col.second.equals("Double")) {
            System.err.println("Column must use Integers or Doubles");
            System.exit(-1);
        }
        double[] min_max_vals = min_max(col);
        return (min_max_vals[1] - min_max_vals[0]);
    }

    public double median(Tuple<Integer, String> col) {
        if (!col.second.equals("Integer") && !col.second.equals("Double")) {
            System.err.println("Column must use Integers or Doubles");
            System.exit(-1);
        }
        List<Double> vals = new ArrayList<>();
        int row = 2;
        while (row < num_rows + 1) {
            vals.add(Double.parseDouble(coord(row, col.first)));

            row++;
        }
        Collections.sort(vals);
        if (vals.size() % 2 == 1) {
            return vals.get(vals.size() / 2);
        } else {
            return ((vals.get(vals.size() / 2) + vals.get((vals.size() / 2) - 1)) / 2);
        }
    }

    public double standard_deviation(Tuple<Integer, String> col) {
        if (!col.second.equals("Integer") && !col.second.equals("Double")) {
            System.err.println("Column must use Integers or Doubles");
            System.exit(-1);
        }

        int row = 2;
        double mean = mean(col);
        double added_deviations = 0;
        while (row < num_rows + 1) {
            double val = Math.pow(Double.parseDouble(coord(row, col.first)) - mean, 2);
            added_deviations += val;

            row++;
        }

        return round(Math.sqrt(added_deviations / num_rows - 1), 3);
    }

    public int unique_values(Tuple<Integer, String> col) {
        if (!col.second.equals("String")) {
            System.err.println("Column must use Strings");
            System.exit(-1);
        }

        HashSet<String> unique_values = new HashSet<>();
        int row = 2;
        while (row < num_rows + 1) {
            String val = coord(row, col.first);
            unique_values.add(val);

            row++;
        }
        return unique_values.size();
    }

    public Tuple<Integer, List<String>> most_frequent(Tuple<Integer, String> col) {
        HashMap<String, Integer> most_frequent = new HashMap<>();
        int row = 2;
        int max = Integer.MIN_VALUE;
        while (row < num_rows + 1) {
            String val = coord(row, col.first);
            if (most_frequent.containsKey(val)) {
                most_frequent.put(val, most_frequent.get(val) + 1);
            } else {
                most_frequent.put(val, 1);
            }
            max = Math.max(max, most_frequent.get(val));

            row++;
        }

        List<String> vals = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : most_frequent.entrySet()) {
            if (entry.getValue() == max) vals.add(entry.getKey());
        }

        return new Tuple<>(max, vals);
    }

    public Tuple<List<String>, List<String>> shortest_longest(Tuple<Integer, String> col){
        if (!col.second.equals("String")) {
            System.err.println("Column must use Strings");
            System.exit(-1);
        }

        HashMap<Integer, List<String>> shortest_longest = new HashMap<>();
        HashSet<String> dups = new HashSet<>();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int row = 2;

        while (row < num_rows + 1){
            String val = coord(row, col.first);
            int len = val.length();
            min = Math.min(min, len);
            max = Math.max(max, len);

            if (!shortest_longest.containsKey(len)){
               shortest_longest.put(len, new ArrayList<>());
            }

            if (!dups.contains(val)) shortest_longest.get(len).add(val);
            dups.add(val);

            row++;
        }

        return new Tuple<>(shortest_longest.get(min), shortest_longest.get(max));
    }

    public void print_csv() {
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            System.out.println(data);
        }
    }
}