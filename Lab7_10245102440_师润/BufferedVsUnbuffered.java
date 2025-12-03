import java.io.*;

public class BufferedVsUnbuffered {
    private static final int BUFFER_SIZE = 8192;
    private static final String SRC = "largefile.dat";
    private static final String DST1 = "out_unbuffered.dat";
    private static final String DST2 = "out_buffered.dat";

    public static void main(String[] args) throws IOException {
        long t1 = copyUnbuffered();
        long t2 = copyBuffered();
        System.out.printf("Unbuffered: %d ms\n", t1 / 1_000_000);
        System.out.printf("Buffered:   %d ms\n", t2 / 1_000_000);
    }

    static long copyUnbuffered() throws IOException {
        try (FileInputStream in = new FileInputStream(SRC);
             FileOutputStream out = new FileOutputStream(DST1)) {
            long start = System.nanoTime();
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return System.nanoTime() - start;
        }
    }

    static long copyBuffered() throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(SRC));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(DST2))) {
            long start = System.nanoTime();
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            return System.nanoTime() - start;
        }
    }
}