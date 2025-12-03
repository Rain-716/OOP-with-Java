import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// 1-3: 单词处理工具类
public class WordProcessor {
    // 1. 将文本中所有的单词读入一个 List 中
    public static List<String> readWords(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 简单按非字母字符分割
                String[] tokens = line.split("[^a-zA-Z0-9]+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        words.add(token.toLowerCase());
                    }
                }
            }
        }
        return words;
    }

    // 2. 统计文本中有多少不同的单词
    public static int countDistinctWords(String filename) throws IOException {
        List<String> words = readWords(filename);
        Set<String> unique = new HashSet<>(words);
        return unique.size();
    }

    // 3. 统计文本中不同单词的出现次数
    public static Map<String, Integer> countWordFrequencies(String filename) throws IOException {
        List<String> words = readWords(filename);
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        return freq;
    }

    // 测试示例
    public static void main(String[] args) {
        String file = "test.txt";
        try {
            List<String> allWords = readWords(file);
            System.out.println("所有单词 (共 " + allWords.size() + "): " + allWords);

            int distinctCount = countDistinctWords(file);
            System.out.println("不同单词个数: " + distinctCount);

            Map<String, Integer> frequencies = countWordFrequencies(file);
            System.out.println("单词频率统计: ");
            for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


// 4. 稀疏矩阵类 SparseMatrix
// 使用 Map 作为容器, 只存储非零元素
class SparseMatrix {
    private final int rows;
    private final int cols;
    // 键为 (i,j) 对应的位置, 值为该位置的元素
    private final Map<Integer, Map<Integer, Double>> data;

    public SparseMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new HashMap<>();
    }

    // 设置元素
    public void set(int i, int j, double value) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            throw new IndexOutOfBoundsException("索引越界");
        }
        if (value == 0.0) {
            // 删除存储
            if (data.containsKey(i)) {
                data.get(i).remove(j);
                if (data.get(i).isEmpty()) {
                    data.remove(i);
                }
            }
        } else {
            data.computeIfAbsent(i, k -> new HashMap<>()).put(j, value);
        }
    }

    // 获取元素
    public double get(int i, int j) {
        if (data.containsKey(i) && data.get(i).containsKey(j)) {
            return data.get(i).get(j);
        }
        return 0.0;
    }

    // 稀疏矩阵加法
    public SparseMatrix add(SparseMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("矩阵尺寸不匹配");
        }
        SparseMatrix result = new SparseMatrix(rows, cols);
        // 复制 this 的元素
        for (Map.Entry<Integer, Map<Integer, Double>> rowEntry : data.entrySet()) {
            int i = rowEntry.getKey();
            for (Map.Entry<Integer, Double> colEntry : rowEntry.getValue().entrySet()) {
                result.set(i, colEntry.getKey(), colEntry.getValue());
            }
        }
        // 加入 other 的元素
        for (Map.Entry<Integer, Map<Integer, Double>> rowEntry : other.data.entrySet()) {
            int i = rowEntry.getKey();
            for (Map.Entry<Integer, Double> colEntry : rowEntry.getValue().entrySet()) {
                int j = colEntry.getKey();
                double sum = result.get(i, j) + colEntry.getValue();
                result.set(i, j, sum);
            }
        }
        return result;
    }

    // 稀疏矩阵乘法
    public SparseMatrix multiply(SparseMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("矩阵尺寸不匹配，无法相乘");
        }
        SparseMatrix result = new SparseMatrix(this.rows, other.cols);
        for (Map.Entry<Integer, Map<Integer, Double>> rowEntry : data.entrySet()) {
            int i = rowEntry.getKey();
            for (Map.Entry<Integer, Double> colEntry : rowEntry.getValue().entrySet()) {
                int k = colEntry.getKey();
                double v1 = colEntry.getValue();
                if (other.data.containsKey(k)) {
                    for (Map.Entry<Integer, Double> otherEntry : other.data.get(k).entrySet()) {
                        int j = otherEntry.getKey();
                        double v2 = otherEntry.getValue();
                        double sum = result.get(i, j) + v1 * v2;
                        result.set(i, j, sum);
                    }
                }
            }
        }
        return result;
    }

    // 打印矩阵
    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%6.2f ", get(i, j));
            }
            System.out.println();
        }
    }

    // 测试示例
    public static void sparseTest() {
        SparseMatrix A = new SparseMatrix(3, 3);
        A.set(0, 0, 1);
        A.set(1, 2, 2.5);
        A.set(2, 1, -3);

        SparseMatrix B = new SparseMatrix(3, 3);
        B.set(0, 0, 4);
        B.set(1, 2, -2.5);
        B.set(2, 2, 5);

        System.out.println("A ="); A.print();
        System.out.println("B ="); B.print();

        System.out.println("A + B =");
        SparseMatrix C = A.add(B);
        C.print();

        System.out.println("A * B =");
        SparseMatrix D = A.multiply(B);
        D.print();
    }

    public static void main(String[] args) {
        sparseTest();
    }
}