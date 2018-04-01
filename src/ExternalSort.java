import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 外部排序的核心类
 */
public class ExternalSort {

    private Generator g;

    /**
     * 结果存放的文件名
     */
    private String resultPath;

    public ExternalSort(Generator g, String resultPath) {
        this.g = g;
        this.resultPath = resultPath;
    }

    /**
     * 开始进行外部排序
     *
     * @throws Exception
     */
    public void start() throws Exception {
        g.generate();
        for (int i = 0; i < g.getCount(); i++) {
            List<Integer> numbers = g.readByIndex(i);
            SoftTool.quickSort(numbers, 0, numbers.size() - 1);
            g.writeByIndex(numbers, i);
        }
        BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();
        AtomicInteger at = new AtomicInteger(0);
        solveInitFile(queue, at);
        solveQueueFile(queue, at);
    }

    /**
     * 解析初始化文件
     *
     * @param queue
     * @param at
     * @throws Exception
     */
    private void solveInitFile(BlockingQueue<Integer> queue, AtomicInteger at) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> calls = new ArrayList<>();
        int index = 0;
        while (index < g.getCount()) {
            if (g.getCount() - index >= 2) {
                int finalIndex = index;
                int num = at.incrementAndGet();
                calls.add(() -> externalSort2(g.getFileName(finalIndex), g.getFileName(finalIndex + 1), num));
            } else {
                int finalIndex = index;
                int num = at.incrementAndGet();
                calls.add(() -> externalSort1(g.getFileName(finalIndex), num));
            }
            index += 2;
        }
        for (Future<Integer> n : pool.invokeAll(calls)) {
            queue.put(n.get());
        }

        pool.shutdown();
    }

    /**
     * 循环解析队列里面的文件
     *
     * @param queue
     * @param at
     * @throws Exception
     */
    private void solveQueueFile(BlockingQueue<Integer> queue, AtomicInteger at) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        while (true) {
            List<Callable<Integer>> calls = new ArrayList<>();
            synchronized (queue) {
                if (queue.size() == 1) {
                    String name = getFileName(queue.take());
                    File f = new File(name);
                    File fRename = new File(f.getParent() + "/" + resultPath);
                    if (fRename.exists()) {
                        fRename.delete();
                    }
                    f.renameTo(fRename);
                    break;
                } else {
                    int index = 0;
                    while (index < queue.size()) {
                        if (queue.size() - index >= 2) {
                            int num = at.incrementAndGet();
                            calls.add(() -> externalSort2(getFileName(queue.take()), getFileName(queue.take()), num));
                        } else {
                            int num = at.incrementAndGet();
                            calls.add(() -> externalSort1(getFileName(queue.take()), num));
                        }
                        index += 2;
                    }
                }
            }
            for (Future<Integer> n : pool.invokeAll(calls)) {
                queue.put(n.get());
            }
        }
        pool.shutdown();
    }

    /**
     * 对两个文件进行外部排序
     *
     * @param f1
     * @param f2
     * @param target
     * @return
     * @throws FileNotFoundException
     */
    private int externalSort2(String f1, String f2, int target) throws FileNotFoundException {
        FormatScanner scan1 = g.getScanner(f1);
        FormatScanner scan2 = g.getScanner(f2);
        FormatPrintWriter p = FormatPrintWriter.wrap(new PrintWriter(new File(getFileName(target))), g.getPer());
        while (scan1.hasNext() && scan2.hasNext()) {
            if (scan1.peek() < scan2.peek()) {
                p.write(scan1.take());
            } else {
                p.write(scan2.take());
            }
        }
        while (scan1.hasNext()) {
            p.write(scan1.take());
        }
        while (scan2.hasNext()) {
            p.write(scan2.take());
        }
        p.close();
        return target;
    }

    /**
     * 对一个文件进行外部排序
     *
     * @param f1
     * @param target
     * @return
     * @throws FileNotFoundException
     */
    private int externalSort1(String f1, int target) throws FileNotFoundException {
        FormatScanner scan1 = g.getScanner(f1);
        FormatPrintWriter p = FormatPrintWriter.wrap(new PrintWriter(new File(getFileName(target))), g.getPer());
        while (scan1.hasNext()) {
            p.write(scan1.take());
        }
        p.close();
        return target;
    }

    private String getFileName(int target) {
        return "./sort/external_" + target + ".txt";
    }
}
