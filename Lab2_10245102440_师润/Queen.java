import java.util.Scanner;
public class Queen{
    public static boolean safe(int[] a,int N){
        for (int i=1;i<=N;i++){
            for (int j=i+1;j<=N;j++){
                if (i-a[i]==j-a[j]||i+a[i]==j+a[j]){
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        int[] a=new int[N+1];
        for (int i=1;i<=N;i++){
            a[i]=sc.nextInt();
        }
        if (safe(a,N)){
            System.out.println("安全");
        }
        else {
            System.out.println("不安全");
        }
        sc.close();
    }
}