import java.util.List;

public class SoftTool {
    public static void quickSort(List<Integer> data, int start, int end) {
        if (start >= end) {
            return;
        }
        int l = start, r = end;
        int number = data.get(l);
        while (l < r) {
            while (l < r && data.get(r) >= number) {
                r--;
            }
            data.set(l, data.get(r));
            while (l < r && data.get(l) <= number) {
                l++;
            }
            data.set(r, data.get(l));
        }
        data.set(l, number);
        quickSort(data, l + 1, end);
        quickSort(data, start, l - 1);
    }

}
