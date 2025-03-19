public class Sqrt {
    public static double sqrt(double c) {
        if (c==0) {
            return 0;
        }
        double x0=c;
        double eps=1e-10;
        double x1=(x0+c/x0)/2.0;
        while (Math.abs(x0-x1)>eps) {
            x0=x1;
            x1=(x0+c/x0)/2.0;
        }
        return x1;
    }
    public static void main(String[] args) {
        double c=2.0;
        double result=sqrt(c);
        System.out.println(result);
    }
}