import java.io.*;
import java.util.*;
public class string{
    public static boolean isWeb(String s) {
        return s.startsWith("http:");
    }
    public static String[] splitWeb(String web) {
        int index = web.indexOf("://");
        if (index == -1) {
            return web.split("\\.");
        }
        else {
            String p = web.substring(0, index + 3);
            String rest = web.substring(index + 3);
            int dotIndex = rest.indexOf('.');
            String firstPart,remaining;
            if (dotIndex != -1) {
                firstPart = p + rest.substring(0, dotIndex);
                remaining = rest.substring(dotIndex + 1);
            }
            else {
                firstPart = p + rest;
                remaining = "";
            }
            String[] otherParts = remaining.isEmpty() ? new String[0] : remaining.split("\\.");
            String[] result = new String[otherParts.length + 1];
            result[0] = firstPart;
            System.arraycopy(otherParts, 0, result, 1, otherParts.length);
            return result;
        }
    }
    public static String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return filename.substring(dot + 1);
    }
    public static String[] splitUnixPath(String path) {
        int slash = path.lastIndexOf('/');
        String dir = path.substring(0, slash + 1);
        String file = path.substring(slash + 1);
        return new String[] { dir, file };
    }
    public static String replace(String input) {
        return input.replace("'", "\"");
    }
    public static void catFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
            catch (IOException ex) {
                System.err.println("读取文件 " + fileName + " 失败：" + ex.getMessage());
            }
        }
    }
    public static int hexToDecimal(String hexStr) {
        return Integer.parseInt(hexStr, 16);
    }
    public static List<String> getLongestWords(String fileName) {
        List<String> longestWords = new ArrayList<>();
        int maxLen = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String word;
            while ((word = br.readLine()) != null) {
                int len = word.length();
                if (len > maxLen) {
                    maxLen = len;
                    longestWords.clear();
                    longestWords.add(word);
                }
                else if (len == maxLen) {
                    longestWords.add(word);
                }
            }
        }
        catch (IOException ex) {
            System.err.println("读取文件失败：" + ex.getMessage());
        }
        return longestWords;
    }
    public static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
    public static int[] countLowerCaseLetters(String fileName) {
        int[] freq = new int[26];
        try (Reader reader = new FileReader(fileName)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                if (ch >= 'a' && ch <= 'z') {
                    freq[ch - 'a']++;
                }
            }
        }
        catch (IOException ex) {
            System.err.println("读取文件失败：" + ex.getMessage());
        }
        return freq;
    }
    public static void printWithOptions(String[] args) {
        String type = "n";
        String outputFile = null;
        boolean help = false;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-t":
                    if (i + 1 < args.length) {
                        type = args[++i];
                    }
                    else {
                        System.err.println("缺少type参数");
                        printHelp();
                        return;
                    }
                    break;
                case "-o":
                    if (i + 1 < args.length) {
                        outputFile = args[++i];
                    }
                    else {
                        System.err.println("缺少输出文件名参数");
                        printHelp();
                        return;
                    }
                    break;
                case "-h":
                    help = true;
                    break;
                default:
                    System.err.println("Wrong options");
                    printHelp();
                    return;
            }
        }
        if (help) {
            printHelp();
            return;
        }
        String content = "";
        if (type.equals("n")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append(i).append(" ");
            }
            content = sb.toString().trim();
        }
        else if (type.equals("a")) {
            StringBuilder sb = new StringBuilder();
            for (char c = 'a'; c <= 'z'; c++) {
                sb.append(c).append(" ");
            }
            content = sb.toString().trim();
        }
        else {
            System.err.println("Wrong options");
            printHelp();
            return;
        }
        if (outputFile != null) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
                pw.println(content);
            }
            catch (IOException ex) {
                System.err.println("写入文件失败：" + ex.getMessage());
            }
        }
        else {
            System.out.println(content);
        }
    }
    private static void printHelp() {
        System.out.println("usage: java Print [OPTIONS]");
        System.out.println("-t type       if type=n print 0-9, if type=a print a-z. Default: type=n");
        System.out.println("-o out.txt    outputs to out.txt, Default: standard out");
        System.out.println("-h            print this help information");
    }
    public static boolean isSecurePassword(String password) {
        if (password.length() < 8) return false;
        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasNonLetter = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            if (!Character.isLetter(c)) {
                hasNonLetter = true;
            }
        }
        return hasDigit && hasUpper && hasNonLetter;
    }
    public static void main(String[] args) {
        String web = "http://www.ecnu.edu.cn";
        System.out.println("1. 是否为网址: " + isWeb(web));

        System.out.println("2. 分解网址: " + Arrays.toString(splitWeb(web)));

        String filename = "a.jpg";
        System.out.println("3. 文件扩展名: " + getExtension(filename));

        String unixPath = "/home/tom/documents/a.jpg";
        String[] splitPath = splitUnixPath(unixPath);
        System.out.println("4. 路径名: " + splitPath[0] + ", 文件名: " + splitPath[1]);

        String sample = "It's a test.";
        System.out.println("5. 替换结果: " + replace(sample));

        String hexStr = "1A3F";
        System.out.println("7. 16进制 " + hexStr + " 转10进制: " + hexToDecimal(hexStr));

        String original = "Hello, World!";
        System.out.println("9. 倒序字符串: " + reverseString(original));

        String pwd = "Abc123$%";
        System.out.println("12. 密码 " + pwd + " 是否安全: " + isSecurePassword(pwd));

        if (args.length > 0 && !args[0].equals("cat")) {
            printWithOptions(args);
        }
    }
}