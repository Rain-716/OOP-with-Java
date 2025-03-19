import java.util.Scanner;
public class snake{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        sc.close();
        int[][] matrix=new int[N][N];
        int num=1;
        int top=0,bottom=N-1,left=0,right=N-1;
        while (num<=N*N){       
            for (int i=left;i<=right;i++){  //从左到右
                matrix[top][i]=num++;
            }
            top++;
            for (int i=top;i<=bottom;i++){  //从上到下
                matrix[i][right]=num++;
            }
            right--;
            for (int i=right;i>=left;i--){  //从右到左
                matrix[bottom][i]=num++;
            }
            bottom--;
            for (int i=bottom;i>=top;i--){  //从下到上
                matrix[i][left]=num++;
            }
            left++;
        }
        for (int i=0;i<N;i++){
            for (int j=0;j<N;j++){
                System.out.printf(matrix[i][j]+"  ");
            }
            System.out.println();
        }
    }
}