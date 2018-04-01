import java.util.Scanner;

public class FormatScanner {
    private Scanner scan;
    private boolean hasRead = true;
    private int currentValue;
    private boolean isEnd = false;

    public FormatScanner(Scanner scan) {
        this.scan = scan;
    }

    /**
     * 取出一个值，并标记已读
     * @return
     */
    public int take() {
        hasRead = true;
        return currentValue;
    }

    /**
     * 窥探值，但是不标记已读
     * @return
     */
    public int peek() {
        return currentValue;
    }

    /**
     * 判断当前值是否合法
     * 如果检测到当前值为末尾，则 set isEnd = true
     * 以后每次调用这个方法都返回isEnd
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
