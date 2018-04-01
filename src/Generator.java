import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Generator {
    private final String path;
    private int per;
    private int count;

    public Generator(String path, int per, int count) {
        this.path = path;
        this.per = per;
        this.count = count;
    }

    public Generator() {
        this("./sort/", 500, 100000);
    }

    /**
     * 生成随机的数字文件
     *
     * @throws FileNotFoundException
     */
    public void generate() throws FileNotFoundException {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        Random rd = new Random();
        for (int j = 0; j < count; j++) {
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < per; i++) {
                numbers.add(rd.nextInt(10000));
            }
            writeByIndex(numbers, j);
            System.out.println(numbers);
        }
    }

    /**
     * 通过索引读取文件
     *
     * @param i
     * @return
     * @throws FileNotFoundException
     */
    public List<Integer> readByIndex(int i) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(getFileName(i)));
        List<Integer> numbers = new ArrayList<>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            for (String s : line.split("\t")) {
                numbers.add(Integer.valueOf(s));
            }
        }
        return numbers;
    }

    /**
     * 通过索引写入数据
     *
     * @param numbers
     * @param i
     * @throws FileNotFoundException
     */
    public void writeByIndex(List<Integer> numbers, int i) throws FileNotFoundException {
        write(numbers, getFileName(i));
    }

    /**
     * 写入数据到文件
     *
     * @param numbers
     * @param fname
     * @throws FileNotFoundException
     */
    public void write(List<Integer> numbers, String fname) throws FileNotFoundException {
        FormatPrintWriter writer = new FormatPrintWriter(new PrintWriter(fname), per);
        for (Integer number : numbers) {
            writer.write(number);
        }
        writer.close();
    }

    public FormatScanner getScannerByIndex(int i) throws FileNotFoundException {
        return getScanner(getFileName(i));
    }

    public FormatScanner getScanner(String fname) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fname));
        scan.useDelimiter("\t");
        return new FormatScanner(scan);
    }

    public String getPath() {
        return path;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFileName(int i) {
        return path + i + ".txt";
    }

}
