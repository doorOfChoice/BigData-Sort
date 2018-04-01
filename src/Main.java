import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Generator g = new Generator("./sort/", 5, 4);
        ExternalSort e = new ExternalSort(g, "result.txt");
        e.start();

    }
}
