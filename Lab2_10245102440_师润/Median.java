public class Median {
    public static void main(String[] args) {
        int[] array=new int[5];
        for (int i=0;i<5;i++) {
            array[i]=Integer.parseInt(args[i]);
        }
        java.util.Arrays.sort(array);   //用Arrays.sort()对数组进行排序。
        System.out.println(array[2]);
    }
}