public class Main {
    public static void main(String[] args) {
        CSV_Analyzer csv = new CSV_Analyzer("data/email.csv", ';');
        String test = csv.coord(2,2);
        System.out.println(test);

    }
    // /home/tim/projects/fifty/CSV_data_analyzer/data/people-100.csv
}