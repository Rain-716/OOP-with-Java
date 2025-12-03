import java.io.File;

public class ListDirectory {
    public static void listDir(File dir, String indent) {
        if (!dir.isDirectory()) {
            System.out.println(indent + "|- " + dir.getName());
            return;
        }
        // 打印目录名
        System.out.println(indent + dir.getName() + ":");
        File[] entries = dir.listFiles();
        if (entries == null) return;
        String subIndent = indent + "    ";
        for (File entry : entries) {
            if (entry.isDirectory()) {
                // 目录前缀
                System.out.print(subIndent + "|- ");
                listDir(entry, subIndent + "   ");
            } else {
                // 文件直接打印
                System.out.println(subIndent + "|- " + entry.getName());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java ListDirectory <directory>");
            System.exit(1);
        }
        File root = new File(args[0]);
        if (!root.exists() || !root.isDirectory()) {
            System.err.println(args[0] + " 不是一个有效的目录");
            System.exit(1);
        }
        listDir(root, "");
    }
}