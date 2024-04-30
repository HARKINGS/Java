/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package API;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.awt.Window.Type;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.codehaus.groovy.util.ListHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.NoSuchElementException;

/**
 *
 * @author Admin
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form API_EDIT
     */
//    private Stack<JPanel> previousStates = new Stack<>(); // Stack lưu trạng thái trước đó

//    private DefaultListModel mod;
//    private HashString listHash[] = new HashString[20];
    static private ArrayList<News> listNews =  new ArrayList<>(); /// lưu thông tin các bài viết
    static private ArrayList<HashString> lHash = new ArrayList<HashString>(); // Tìm kiếm ký tự cần tìm trong các bài viết
    
    // set up giao diện mặc định
    public MainFrame() {
        initComponents();
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        MainPanel.setLayout(new GridLayout(15,1));  
        updateScrollPane();
        MainPanel.removeAll();
        MainPanel.revalidate();
        MainPanel.repaint();
        
        // địa chỉ tương đôi để đọc file Json
        String filepath = "src/main/java/TrendingMethod/Contents.json";
        ContentPanel(JsonRead(filepath));
        updateScrollPane();
        
        // Thêm panel mới vào MainPanel và cập nhật hiển thị
        MainPanel.revalidate();
        MainPanel.repaint();
        
        // Thêm action cho Enter Key
        Search.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");
        Search.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search.doClick();
            }
        });

    }
    
    // khởi tạo thông tin bài viết dưới dạng xâu để tìm kiếm
    private static void setup() {
        FileReader reader = null;
        try {
            /// địa chỉ tương đối này chỉ dùng được với netbeans
            reader = new FileReader("src/main/java/TrendingMethod/Contents.json");
            
            // tạo một kiểu cho list News
            Gson gson = new Gson();
            java.lang.reflect.Type classOfT = new TypeToken<ArrayList<News>>(){}.getType();

            // Mothod 1: get data in Json with Java
            listNews = gson.fromJson(reader, classOfT);
            
            for(News news : listNews) {
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
    
    // Xóa nội dung hiển thị trước đó
    private void clearLayout() {
        MainPanel.removeAll();
        MainPanel.revalidate();
        MainPanel.repaint();
    }
    
    // đưa ra xâu chỉ id bài viết chứa ký tự nhập vào
    private ArrayList<News> searchSuggestion(String search) throws MalformedURLException, IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<News> ans = new ArrayList<News>(); // lưu thông tin vài bài viết chứa ký tự cần tìm
        // search được filter lại tìm cho dễ
        search = search.toLowerCase().replace(" ", "").replace(",", "").replace(".", "").replace(":", "").replace("/", ""); 
        
        // tìm kiếm kết quả
        HashString val = new HashString(search);
        val.setHash();
        int siz2 = val.getStr().length() - 1;

        int dem = 0;
        for(HashString res : lHash)
        {
            News news = listNews.get(dem);
            int siz1 = res.getStr().length() - 1;
            for(int i = 1; i <= siz1 - siz2 + 1; ++i)
            {
                /// kiểm tra xâu val có xuất hiện trong listHash[id] không
                if(res.check(val, i, i + siz2 - 1))
                {
                    ans.add(news);
                    break;
                }
            }
            dem++;
        }
        
        return ans;
    }
    
    // update tốc độ lăn chuột
    private void updateScrollPane() {
        int preferredHeight = MainPanel.getPreferredSize().height;
        int scrollPaneHeight = jScrollPane1.getViewport().getExtentSize().height;
        jScrollPane1.setVerticalScrollBarPolicy(preferredHeight > scrollPaneHeight ? 
                                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS : 
                                                JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }
    
    // đọc dữ liệu trong file JSON chuyển nó sang dạng JSONArray
    private static JSONArray JsonRead(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONArray value = (JSONArray) jsonParser.parse(reader);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /// show ra thông tin mặc định
    private static void ContentPanel(JSONArray jsonArray){
        if (jsonArray != null && !jsonArray.isEmpty()){
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                JPanel ContentPane = new JPanel();
                ContentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                ContentPane.setLayout(new GridLayout(3, 1)); // GridLayout cho 3 thành phần này
                    
                // Lấy các giá trị từ JSONObject
                String a = (String) jsonObject.get("Tiêu đề bài viết");
                String b = (String) jsonObject.get("Tên tác giả nếu có");
                String c = (String) jsonObject.get("Ngày tạo");

                JLabel Baiviet = new JLabel("       "+a);
                JLabel Tacgia = new JLabel("       Tác giả: "+b);
                JLabel Ngay = new JLabel("       Ngày tạo: "+c);
                Baiviet.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font Arial, kích thước 20


                Baiviet.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent  e) {
                        // Tạo JFrame chứa JTextArea
                        JFrame outputFrame = new JFrame("Thông tin bài viết");
                        outputFrame.setTitle("Thông tin bài viết");
                        Dimension preferredSize = new Dimension(650, 450);
                        outputFrame.setPreferredSize(preferredSize);
                        outputFrame.setLocation(400, 250); // Đặt vị trí xuất hiện của JFrame

                        // Lấy thông tin từ JSON và hiển thị trên JTextArea
                        JTextArea textArea = new JTextArea();
                        textArea.setEditable(false); // Không cho phép chỉnh sửa
                        textArea.setLineWrap(true); // Cho phép tự động xuống dòng nếu cần
                        textArea.setWrapStyleWord(true); // Đảm bảo không cắt từ
                        
                        textArea.append("Link bài viết    : " + jsonObject.get("Link bài viết") + "\n");
                        textArea.append("Nguồn website  : " + jsonObject.get("Nguồn website") + "\n");
                        textArea.append("Loại bài viết  : " + jsonObject.get("Loại bài viết") + "\n");
                        textArea.append("Tóm tắt bài viết  : " + jsonObject.get("Tóm tắt bài viết (nếu có)") + "\n");
                        textArea.append("Tiêu đề bài viết  : " + jsonObject.get("Tiêu đề bài viết") + "\n");
                        textArea.append("Nội dung bài viết  : " + jsonObject.get("Nội dung chi tiết bài viết") + "\n");
                        textArea.append("Ngày tạo  : " + jsonObject.get("Ngày tạo") + "\n");
                        textArea.append("Tag/Hash tag  : " + jsonObject.get("Tag/Hash tag đi kèm") + "\n");
                        textArea.append("Tên tác giả  : " + jsonObject.get("Tên tác giả nếu có") + "\n");
                        textArea.append("Chuyên mục : " + jsonObject.get("Chuyên mục mà bài viết thuộc về") + "\n\n");  
                        
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        outputFrame.add(scrollPane, BorderLayout.CENTER);
                        outputFrame.pack();
                        outputFrame.setVisible(true);
                    }
                    
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // Khi chuột vào, đặt font in đậm và có gạch chân
                        Font font = Baiviet.getFont();
                        Baiviet.setFont(font.deriveFont(font.getStyle() | Font.BOLD ));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // Khi chuột ra, đặt lại font bình thường
                        Font font = Baiviet.getFont();
                        Baiviet.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD ));
                    }


                });

                ContentPane.add(Baiviet);
                ContentPane.add(Tacgia);
                ContentPane.add(Ngay);
                MainPanel.add(ContentPane);
            } 
        }
        else{
                MainPanel.removeAll();
                MainPanel.revalidate();
                MainPanel.repaint();
                JLabel Nothing = new JLabel("Không có bài viết cần tìm!");
                Nothing.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font Arial, kích thước 20
                MainPanel.add(Nothing);
                // Thêm panel mới vào MainPanel và cập nhật hiển thị
                MainPanel.revalidate();
                MainPanel.repaint();
        }
    }

    /// show ra thông tin kết quả tìm kiếm dc
    private static void ContentPanel1(ArrayList<News> listNew){
        if (listNew != null && !listNew.isEmpty()){
            for (News news : listNew) {
                JPanel ContentPane = new JPanel();
                ContentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                ContentPane.setLayout(new GridLayout(3, 1)); // GridLayout cho 3 thành phần này
                    
                // Lấy các giá trị từ ArrayList News
                String a = (String) news.getTitle();
                String b = (String) news.getAuthor();
                String c = (String) news.getCreateDate();

                JLabel Baiviet = new JLabel("       "+a);
                JLabel Tacgia = new JLabel("       Tác giả: "+b);
                JLabel Ngay = new JLabel("       Ngày tạo: "+c);
                Baiviet.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font Arial, kích thước 20


                Baiviet.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent  e) {
                        // Tạo JFrame chứa JTextArea
                        JFrame outputFrame = new JFrame("Thông tin bài viết");
                        outputFrame.setTitle("Thông tin bài viết");
                        Dimension preferredSize = new Dimension(650, 450);
                        outputFrame.setPreferredSize(preferredSize);
                        outputFrame.setLocation(400, 250); // Đặt vị trí xuất hiện của JFrame

                        // Lấy thông tin từ JSON và hiển thị trên JTextArea
                        JTextArea textArea = new JTextArea();
                        textArea.setEditable(false); // Không cho phép chỉnh sửa
                        textArea.setLineWrap(true); // Cho phép tự động xuống dòng nếu cần
                        textArea.setWrapStyleWord(true); // Đảm bảo không cắt từ
                        
                        textArea.append("Link bài viết    : " + news.getLink() + "\n");
                        textArea.append("Nguồn website  : " + news.getWebsite() + "\n");
                        textArea.append("Loại bài viết  : " + news.getTypeBlog() + "\n");
                        textArea.append("Tóm tắt bài viết  : " + news.getSummary() + "\n");
                        textArea.append("Tiêu đề bài viết  : " + news.getTitle() + "\n");
                        textArea.append("Nội dung bài viết  : " + news.getContent() + "\n");
                        textArea.append("Ngày tạo  : " + news.getCreateDate() + "\n");
                        textArea.append("Tag/Hash tag  : " + news.getHashTag() + "\n");
                        textArea.append("Tên tác giả  : " + news.getAuthor() + "\n");
                        textArea.append("Chuyên mục : " + news.getCategory() + "\n\n");  
                        
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        outputFrame.add(scrollPane, BorderLayout.CENTER);
                        outputFrame.pack();
                        outputFrame.setVisible(true);
                    }
                    
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // Khi chuột vào, đặt font in đậm và có gạch chân
                        Font font = Baiviet.getFont();
                        Baiviet.setFont(font.deriveFont(font.getStyle() | Font.BOLD ));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // Khi chuột ra, đặt lại font bình thường
                        Font font = Baiviet.getFont();
                        Baiviet.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD ));
                    }
                });

                ContentPane.add(Baiviet);
                ContentPane.add(Tacgia);
                ContentPane.add(Ngay);
                MainPanel.add(ContentPane);
            } 
        }
        else{
                MainPanel.removeAll();
                MainPanel.revalidate();
                MainPanel.repaint();
                JLabel Nothing = new JLabel("Không có bài viết cần tìm!");
                Nothing.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font Arial, kích thước 20
                MainPanel.add(Nothing);
                // Thêm panel mới vào MainPanel và cập nhật hiển thị
                MainPanel.revalidate();
                MainPanel.repaint();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // phần xử lý khi thao tác Enter hoặc là bấm Button Search
    private void solve() {
        // Xoá màn hình
        clearLayout();
        
        try {
            String search = jTextField1.getText().trim();
            if(!search.equals("")) {
                ArrayList<News> ans = searchSuggestion(search);
                ContentPanel1(ans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        updateScrollPane();
        
        // Thêm panel mới vào MainPanel và cập nhật hiển thị
        MainPanel.revalidate();
        MainPanel.repaint();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        Home = new javax.swing.JButton();
        Search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        MainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Search_API");
        setName("MainFrame"); // NOI18N

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        Home.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Home.setText("Home");
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });

        Search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        MainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(MainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Home)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Search)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search)
                    .addComponent(Home))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1))
        );

        getAccessibleContext().setAccessibleName("MFrame");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // trả về dữ liệu mặc định (trang mặc định)
    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        // TODO add your handling code here:
        clearLayout();
        String filepath = "src/main/java/TrendingMethod/Contents.json";        
        ContentPanel(JsonRead(filepath));
        updateScrollPane();
        // Thêm panel mới vào MainPanel và cập nhật hiển thị
        MainPanel.revalidate();
        MainPanel.repaint();
    }//GEN-LAST:event_HomeActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        solve();
    }//GEN-LAST:event_SearchActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        solve();
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        setup();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Home;
    private static javax.swing.JPanel MainPanel;
    private javax.swing.JButton Search;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
