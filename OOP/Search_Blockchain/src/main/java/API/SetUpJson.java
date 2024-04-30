/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package API;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ngọc
 */
public class SetUpJson {
    
    private HashString listHash[] = new HashString[20];
    static private ArrayList<HashString> lHash = new ArrayList<HashString>();
    
    static void setup() {
        FileReader reader = null;
        try { 
            // đọc file project.json
            
            // địa chỉ tương đối này lại dùng được với vsCode
            // reader = new FileReader("ProjectAssignment/src/main/java/JsonFile/project.json");
            
            /// địa chỉ tương đối này chỉ dùng được với netbeans
            reader = new FileReader("src/main/java/TrendingMethod/Contents.json");
            
            /// Địa chỉ tuyệt đối luôn đúng
            
            // tạo một kiểu cho list News
            Gson gson = new Gson();
            java.lang.reflect.Type classOfT = new TypeToken<ArrayList<News>>(){}.getType();

            // Mothod 1: get data in Json with Java
            ArrayList<News> list = gson.fromJson(reader, classOfT);
            
            for(News news : list) {
                String res = news.toString();
                HashString cur = new HashString(res.toLowerCase());
                lHash.add(cur);
                lHash.getLast().setHash();
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        setup();
    }
}
