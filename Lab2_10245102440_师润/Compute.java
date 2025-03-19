import java.io.*;
import java.util.*;
public class Compute{
    public static void main(String[] args){
        String filename=args[0];
        List<Double> dataList=new ArrayList<Double> ();
        try (BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line;
            while((line=br.readLine())!=null){
                line=line.trim();
                if (!line.isEmpty()){
                        double num=Double.parseDouble(line);
                        dataList.add(num);
                }
            }
        }
        catch (IOException e){
            System.out.println("读取文件出错: " + e.getMessage());
            return;
        }
        double[] data=new double[dataList.size()];
        for (int i=0;i<dataList.size();i++){
            data[i]=dataList.get(i);
        }
        // 计算最大值，平均值，方差
        double max=Statistic.max(data);
        double mean=Statistic.mean(data);
        double variance=Statistic.variance(data);
        // 计算中位数
        double[] b=data.clone();
        Arrays.sort(b);
        double median;
        int n=b.length;
        median= n%2==1 ? b[n/2] : (b[n/2-1]+b[n/2])/2.0;
        System.out.println("最大值: "+max);
        System.out.println("均值: "+mean);
        System.out.println("方差: "+ variance);
        System.out.println("中位数: "+median);
        // 出现次数最多的数
        int[] h=Statistic.histogram(data);
        int maxF=0;
        double mostFdata=data[0];
        for (int i=0;i<h.length;i++){
            if (h[i]>maxF){
                maxF=h[i];
                mostFdata=data[i];
            }
        }
        System.out.println("出现次数最多的数: "+mostFdata+" (出现次数: "+maxF+")");
        // 最靠近均值的数
        double closest=data[0];
        double minD=Math.abs(data[0]-mean);
        for (int i=0;i<data.length;i++){
            double d=Math.abs(data[i]-mean);
            if (d<minD){
                minD=d;
                closest=data[i];
            }
        }
        System.out.println("最靠近均值的数: "+closest);
        // 与均值的距离小于1倍、2倍、3倍方差的数
        List<Double> within1=new ArrayList<Double>();
        List<Double> within2=new ArrayList<Double>();
        List<Double> within3=new ArrayList<Double>();
        for (double num : data){
            double d=Math.abs(num-mean);
            if (d<variance){
                within1.add(num);
            }
            if (d<2*variance){
                within2.add(num);
            }
            if (d<3*variance){
                within3.add(num);
            }
        }
        System.out.println("与均值的距离小于1倍方差的数: "+within1);
        System.out.println("与均值的距离小于2倍方差的数: "+within2);
        System.out.println("与均值的距离小于3倍方差的数: "+within3);
    }
}