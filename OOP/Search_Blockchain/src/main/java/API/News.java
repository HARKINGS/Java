/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package API;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author Ngọc
 */
public class News {
    @SerializedName("Link bài viết")
    private String link;
    
    @SerializedName("Nguồn website")
    private String website;
    
    @SerializedName("Loại bài viết")
    private String typeBlog;
    
    @SerializedName("Tóm tắt bài viết (nếu có)")
    private String summary;
    
    @SerializedName("Tiêu đề bài viết")
    private String title;
    
    @SerializedName("Nội dung chi tiết bài viết")
    private String content;
    
    @SerializedName("Tag/Hash tag đi kèm")
    private ArrayList<String> hashTag = new ArrayList<String>();
    
    @SerializedName("Ngày tạo")
    private String createDate;
    
    @SerializedName("Tên tác giả nếu có")
    private String author;
    
    @SerializedName("Chuyên mục mà bài viết thuộc về")
    private String category;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTypeBlog() {
        return typeBlog;
    }

    public void setTypeBlog(String typeBlog) {
        this.typeBlog = typeBlog;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashTag() {
        String ans = "";
        for(String res : hashTag)
            ans += res;
        return ans;
    }

    public void setHashTag(ArrayList<String> hashTag) {
        this.hashTag = hashTag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }    
    
    public void display() {
        System.out.println("Link: " + getLink().replace(":", "").replace("-", "").replace("/", "").replace(".", "").replace(" ", ""));
        System.out.println("Website: " + getWebsite().replace(".", "").replace(" ", ""));
        System.out.println("Title: " + getTitle().replace(",", "").replace(" ", "").replace(".", ""));
        System.out.println("TypeBlog: " + getTypeBlog().replace(" ", ""));
        System.out.println("Content: " + getContent().replace(",", "").replace(" ", "").replace(".", "").replace(":", "").replace("/", ""));
        System.out.println("Summary: " + getSummary().replace(",", "").replace(" ", "").replace(".", "").replace(":", "").replace("/", ""));
        System.out.println("Hashtag: " + getHashTag().replace("#", ""));
        System.out.println("CreateDate: " + getCreateDate().replace("/", ""));
        System.out.println("Author: " + getAuthor().replace(" ", ""));
        System.out.println("Category: " + getCategory().replace(" ", "")); 
        System.out.println("");
    }
    
    @Override
    public String toString() {        
        return (getLink().replace("-", "").replace("/", "").replace(".", "").replace(" ", "") +
                getWebsite().replace(" ", "") +
                getTypeBlog().replace(" ", "") +
                getTitle().replace(",", "").replace(" ", "").replace(".", "") +
                getContent().replace(",", "").replace(" ", "").replace(".", "").replace(":", "").replace("/", "") +
                getSummary().replace(",", "").replace(" ", "").replace(".", "").replace(":", "").replace("/", "") +
                getHashTag() +
                getCreateDate().replace("/", "") +
                getAuthor().replace(" ", "") +
                getCategory().replace(" ", ""));
        
//        return (getLink().replace(" ", "") +
//                getWebsite().replace(" ", "") +
//                getTypeBlog().replace(" ", "") +
//                getTitle().replace(" ", "") +
//                getContent().replace(" ", "") +
//                getSummary().replace(" ", "") +
//                getHashTag().replace("#", "") +
//                getCreateDate().replace(" ", "") +
//                getAuthor().replace(" ", "") +
//                getCategory().replace(" ", ""));
    }
}
