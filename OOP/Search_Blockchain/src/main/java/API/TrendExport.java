/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package API;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;


/**
 *
 * @author Admin
 */
public class TrendExport {

    public static void main(String[] args) {
        // Đường dẫn đến tệp JSON
        String filePath = "src/main/java/TrendingMethod/Test.json";
        String trending = new String();
        try {
            // Read the JSON file
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

            // Count the occurrence of each hashtag
            Map<String, Integer> hashtagCounts = new HashMap<>();
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray hashtags = (JSONArray) jsonObject.get("tags");

                for (Object hashtagObj : hashtags) {
                    String hashtag = (String) hashtagObj;
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }

            // Sort hashtags by frequency in descending order
            List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCounts.entrySet());
            sortedHashtags.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            for (Map.Entry<String, Integer> entry : sortedHashtags) {
                trending = entry.getKey();
//                System.out.print(trending);
                break;
            } 
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        
                 // Print the result
            System.out.println("Trending :");   
     try {
        // Đọc file JSON
        FileReader reader = new FileReader(filePath);

        // Parse file JSON
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
        List<String> values = new ArrayList<>();
        
        // Duyệt qua các đối tượng trong mảng JSON
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray tags = (JSONArray) jsonObject.get("tags");
            
            // Kiểm tra xem giá trị mong muốn có trong mảng tags không
            if (tags.contains(trending)) {
                values.add(jsonObject.toJSONString()); // Thêm object JSON vào danh sách
            }
        }
        
        // In ra các object JSON tương ứng với giá trị mong muốn của khóa
        for (String json : values) {
            System.out.println(json);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}