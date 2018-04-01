import java.util.Scanner;

public class FormatScanner {
    private Scanner scan;
    private boolean hasRead = true;
    private int currentValue;
    private boolean isEnd = false;

    public FormatScanner(Scanner scan) {
        this.scan = scan;
    }

    public int take() {
        hasRead = true;
        return currentValue;
    }

    public int peek() {
        return currentValue;
    }

    /**
     * 判断当前值是否合法
     *
     * @return
     */
    public boolean hasNext() {
        if (isEnd) return false;
        try {
            if (hasRead) {
                currentValue = Integer.valueOf(scan.next());
                hasRead = false;
            }
        } catch (Exception e) {
            isEnd = true;
            return false;
        }
        return true;
    }
}
