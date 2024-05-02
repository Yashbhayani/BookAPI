package com.bookstore.bookstore.Model;


public class BooksModel {
    public  String id;
    public  int languageid;
    public String bookName;
    public String language_Name;
    public String image;
    public Double  price;
    public String author_Name;
    public Double review_Star;
    public BooksModel(String id, int languageid, String bookName, String language_Name, String image, Double price, String author_Name, Double review_Star) {
        this.id = id;
        this.languageid = languageid;
        this.bookName = bookName;
        this.language_Name = language_Name;
        this.image = image;
        this.price = price;
        this.author_Name = author_Name;
        this.review_Star = review_Star;
    }

    public BooksModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLanguageid() {
        return languageid;
    }

    public void setLanguageid(int languageid) {
        this.languageid = languageid;
    }

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLanguage_Name() {
        return language_Name;
    }

    public void setLanguage_Name(String language_Name) {
        this.language_Name = language_Name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAuthor_Name() {
        return author_Name;
    }

    public void setAuthor_Name(String author_Name) {
        this.author_Name = author_Name;
    }

    public Double getReview_Star() {
        return review_Star;
    }

    public void setReview_Star(Double review_Star) {
        this.review_Star = review_Star;
    }
}
