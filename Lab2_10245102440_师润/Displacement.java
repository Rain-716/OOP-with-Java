import java.util.Scanner;
public class Displacement{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        int[] a=new int[2*N];
        for (int i=0;i<N;i++){
            a[i]=sc.nextInt();
        }
        for (int i=0;i<N;i++){
            a[a[i]+N-1]=i+1;
        }
        for (int i=0;i<N;i++){
            System.out.print(a[i+N]+" ");
        }
        sc.close();
    }
}