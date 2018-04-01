import java.io.File;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //随机文件生成的路径
        String generatePath = "sort/";
        //生成文件的数量
        int count = 30;
        //每个文件的数字数量
        int per = 10000;
        //生成最终文件名称
        String resultFile = "result.txt";

        Generator g = new Generator(generatePath, per, count);
        ExternalSort e = new ExternalSort(g, resultFile);
        e.start();
    }
}
