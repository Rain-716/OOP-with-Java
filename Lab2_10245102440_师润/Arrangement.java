import java.util.Scanner;
public class Arrangement{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        int[] a=new int[N];
        for (int i=0;i<N;i++){
            a[i]=i+1;
        }
        A(a, 0);
        sc.close();
    }
    private static void A(int[] a,int start){
        if (start==a.length){
            for (int i=0;i<a.length;i++){
                System.out.print(a[i]+" ");
            }
            System.out.println();
            return;
        }
        for (int i=start;i<a.length;i++){
            swap(a,start,i);
            A(a,start+1);
            swap(a,start,i);
        }
    }
    private static void swap(int[] a,int i,int j){
        int temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }
}