import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 格式化PrintWriter
 * 讲所有的数字按照指定的阈值进行换行
 */
public class FormatPrintWriter {
    private List<String> numbers = new ArrayList<>();

    private PrintWriter p;

    private int count = 1;

    private int threshold;

    private FormatPrintWriter(PrintWriter p) {
        this(p, 3);
    }

    private FormatPrintWriter(PrintWriter p, int threshold) {
        this.p = p;
        this.threshold = threshold;
    }

    public void write(int n) {
        numbers.add(String.valueOf(n));
        count++;
    }

    public void write(String s) {
        numbers.add(s);
        count++;
    }

    private void flush() {
        if (!numbers.isEmpty()) {
            StringBuilder bud = new StringBuilder();
            for (int j = 0, i = count - numbers.size(); j < numbers.size(); j++, i++) {
                if ((i - 1) % threshold == 0 && i != 1) {
                    bud.append("\n");
                }
                if (threshold != 1 && i % threshold != 1) {
                    bud.append("\t");
                }
                bud.append(numbers.get(j));
            }
            p.flush();
            p.print(bud.toString());
            numbers.clear();
        }
    }

    public void close() {
        flush();
        p.close();
    }

    public static FormatPrintWriter wrap(PrintWriter p, int threshold) {
        return new FormatPrintWriter(p, threshold);
    }

    public static FormatPrintWriter wrap(PrintWriter p) {
        return new FormatPrintWriter(p);
    }
}
