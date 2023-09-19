package work;
import java.io.*;
import java.util.*;
public class hello {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("第一个文件的路径");
        String text1= scanner.nextLine();
        System.out.println("第二个文件的路径");
        String text2 = scanner.nextLine();
        String sentence1 = readTextFromFile(text1);
        String sentence2 = readTextFromFile(text2);
        System.out.println("输出路径");
        String result = scanner.nextLine();

        Map<String, Integer> map1 = createWordFrequencyVector(sentence1);
        Map<String, Integer> map2 = createWordFrequencyVector(sentence2);

        double similar = calculateCosineSimilarity(map1, map2);
        Double similar1=new Double(similar);
       
        if(similar>0.6) System.out.println("抄袭");
        String end=similar1.toString();
        writeResultToFile(result, end);
        scanner.close();

    }

  static   double calculateCosineSimilarity(Map<String, Integer> map1, Map<String, Integer> map2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (String word : map1.keySet()) {
            if (map2.containsKey(word)) {
                dotProduct += map1.get(word) * map2.get(word);
            }
            norm1 += Math.pow(map1.get(word), 2);
        }

        for (String word : map2.keySet()) {
            norm2 += Math.pow(map2.get(word), 2);
        }

        if (norm1 == 0 || norm2 == 0) {
            return 0.0; // 避免除以零
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));//计算余弦相似度
    }

  static  Map<String, Integer> createWordFrequencyVector(String text) {
        Map<String, Integer> vector = new HashMap<>();
        String[] words = text.split("\\s+"); // 正则空格
        for (String word : words) {
            vector.put(word, vector.getOrDefault(word, 0) + 1);//出现关键字,则值加一
        }
        return vector;
    }
  static String readTextFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    static void writeResultToFile(String filePath, String result) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(result);
            System.out.println("结果已写入文件: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}