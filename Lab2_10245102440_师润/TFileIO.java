public class TFileIO{
    public static void main(String[] args){
        String fileName="test.txt";
        FileIO.writeStringToFile("《老人与海》这本小说是根据真人真事写的。第一次世界大战结束后，海明威移居古巴，认识了老渔民格雷戈里奥·富恩特斯。\n",fileName);
        FileIO.writeStringToFile("1930年，海明威乘的船在暴风雨中沉没，富恩特斯搭救了海明威。从此，海明威与富恩特斯结下了深厚的友谊，并经常一起出海捕鱼。\n",fileName);
        FileIO.writeStringToFile("The novel The Old Man and the Sea is based on a real story. After the end of World War I, Hemingway moved to Cuba, where he met an old fisherman, Gregorio Fuentes.\n",fileName);
        FileIO.writeStringToFile("In 1930, Hemingway was rescued by Fuentes when his boat sank in a storm. From then on, Hemingway and Fuentes formed a deep friendship, and often went fishing together.\n",fileName);
        char ch=FileIO.getCharFromFile(4,fileName);
        System.out.println("第5个字符是: "+ch);
        String line3=FileIO.getLineFromFile(2,fileName);
        System.out.println("第3行是: "+line3);
        String[] allLines = FileIO.getAllLinesFromFile(fileName);
        System.out.println("文件所有行：");
        for (String line : allLines){
            System.out.println(line);
        }
    }
}