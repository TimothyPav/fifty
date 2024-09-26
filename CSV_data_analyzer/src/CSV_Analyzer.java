import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.stream.Collectors;

public class CSV_Analyzer {
    private final File file;
    private Scanner scanner;
    private char seperator;
    private final int num_cols;

    public CSV_Analyzer(String file_name, char separator) {
        file = new File(file_name);
        this.seperator = separator;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + file_name + "' not found");
        }
        num_cols = get_num_of_cols();
    }

    public static void print(Object... objects) {
        String result = Arrays.stream(objects)
                .map(obj -> obj == null ? "null" : obj.toString())
                .collect(Collectors.joining(" "));
        System.out.println(result);
    }


    public int get_column_by_name(String name) {
        int i = 1;
        while (true) {
            if (coord(1, i).equals(name)) return i;
            else if (i >= num_cols) return -1;
            i++;
        }
    }

    private int get_num_of_cols() {
        int col = 1;
        int quotes = 0;
        Scanner scanner = build_scanner();

        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == seperator && quotes % 2 == 0) col++;
                else if (line.charAt(i) == '"') quotes++;
            }
        }
        return col;


    }

    private Scanner build_scanner() {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + file + "' not found");
        }
        return scanner;
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

                    if (line.charAt(i) == seperator && quotes % 2 == 0) {
                        curr_col++;
                    }
                    if (curr_col == col && line.charAt(i) != seperator) {
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

    public void print_csv() {
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            System.out.println(data);
        }
    }


}
