import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.util.Scanner; // Import the Scanner class to read text files

public class CSV_Analyzer {
    private final File file;
    private Scanner scanner;
    private char seperator;

    public CSV_Analyzer(String file_name, char separator){
        file = new File(file_name);
        this.seperator = separator;
        try{
            scanner = new Scanner(file);
        } catch (FileNotFoundException e){
            System.err.println("File '" + file_name + "' not found");
        }
    }

    public String coord(int row, int col){
        int curr_col = 0;
        int curr_row = 0;
        boolean built = false;
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()){
            System.out.println("hello " + curr_row + " : " + curr_col);
            String line = scanner.nextLine();
            if(curr_row == row) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == seperator) {
                        curr_col++;
                    }
                    if (curr_col == col && line.charAt(i) != seperator) {
                        sb.append(line.charAt(i));
                        built = true;
                    } else if (built) break;
                }
            }
            curr_row++;
            if(curr_row > row) break;
        }

        return sb.toString();
    }

    public void print_csv(){
       while(scanner.hasNextLine()){
           String data = scanner.nextLine();
           System.out.println(data);
       }
    }


}
