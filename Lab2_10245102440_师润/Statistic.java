public class Statistic{
    // 返回数组a中的最大值
    public static double max(double a[]){
        double max=a[0];
        for (int i=0;i<a.length;i++){
            if (a[i]>max){
                max=a[i];
            }
        }
        return max;
    }
    // 返回数组a中的最小值
    public static double min(double a[]){
        double min=a[0];
        for (int i=0;i<a.length;i++){
            if (a[i]<min){
                min=a[i];
            }
        }
        return min;
    }
    // 返回数组a的均值
    public static double mean(double a[]){
        double sum=0;
        for (int i=0;i<a.length;i++){
            sum+=a[i];
        }
        return sum/a.length;
    }
    // 返回数组a的方差
    public static double variance(double a[]){
        double m=mean(a);
        double sum=0;
        for (int i=0;i<a.length;i++){
            double d=a[i]-m;
            sum+=d*d;
        }
        return sum/a.length;
    }
    // 返回数组a中第k大的数
    public static double select(double a[],int k){
        java.util.Arrays.sort(a);
        return a[a.length-k];
    }
    // 返回数组b,其中b[i]表示a[i]在数组a中出现的次数
    public static int[] histogram(double a[]){
        int[] h=new int[a.length];
        for (int i=0;i<a.length;i++){
            int count=0;
            for (int j=0;j<a.length;j++){
                if (a[j]==a[i]){
                    count++;
                }
            }
            h[i]=count;
        }
        return h;
    }
}