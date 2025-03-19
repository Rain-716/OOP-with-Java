public class Rumor{
    public static void main(String[] args){
        int N=10,T=1000000,times=0,sum=0;
        for (int i=0;i<T;i++){
            int random=0,cnt=1;
            boolean[] a=new boolean[N];
            a[random]=true;
            while(true){
                int temp;
                do{
                    temp=(int)(Math.random()*N);
                } while(temp==random);
                random=temp;
                if (a[random]){
                    break;
                }
                a[random]=true;
                cnt++;
            }
            if (cnt==N){
                times++;
            }
            sum+=cnt;
        }
        double p=(double)times/T;
        double exp=(double)sum/T;
        System.out.println("所有人都知道这个谣言的概率："+p);
        System.out.println("听到谣言人数的期望值："+exp);
    }
}