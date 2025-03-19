public class Distance {
    public static void main(String[] args) throws Exception {
        double x1=Double.parseDouble(args[0]);
        double y1=Double.parseDouble(args[1]);
        double x2=Double.parseDouble(args[2]);
        double y2=Double.parseDouble(args[3]);
        double distance=Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
        System.out.println(distance);
    }
}