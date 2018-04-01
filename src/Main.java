import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Generator g = new Generator("./sort/", 10000, 30);
        ExternalSort e = new ExternalSort(g, "result.txt");
        e.start();
    }
}
