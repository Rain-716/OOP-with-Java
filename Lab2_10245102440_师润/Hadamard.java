import java.util.Scanner;
public class Hadamard{
    public static boolean[][] H(int N){
        if (N==1){
            return new boolean[][] {{true}};
        }
        else {
            boolean[][] m=H(N-1);
            int size=(1<<N-2);
            boolean[][] mat=new boolean[2*size][2*size];
            for (int i=0;i<size;i++){
                for (int j=0;j<size;j++){
                    mat[i][j]=mat[size+i][j]=mat[i][size+j]=m[i][j];
                    mat[size+i][size+j]=!m[i][j];
                }
            }
            return mat;
        }
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        boolean[][] matrix=H(N);
        for (int i=0;i<(1<<N-1);i++){
            for (int j=0;j<(1<<N-1);j++){
                System.out.print((matrix[i][j] ? 1 : 0)+" ");
            }
            System.out.println();
        }
        sc.close();
    }
}