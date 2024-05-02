package com.bookstore.bookstore.Model;

public class LikeModel {
    public  int likeId;
    public  int bookId;
    public String bookName;
    public String image;
    public Double  price;
    public String authorName;
    public String language_Name;
    public Double review_Star;

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLanguage_Name() {
        return language_Name;
    }

    public void setLanguage_Name(String language_Name) {
        this.language_Name = language_Name;
    }

    public Double getReview_Star() {
        return review_Star;
    }

    public void setReview_Star(Double review_Star) {
        this.review_Star = review_Star;
    }

    public LikeModel(int likeId, int bookId, String bookName, String image, Double price, String authorName, String language_Name, Double review_Star) {
        this.likeId = likeId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.image = image;
        this.price = price;
        this.authorName = authorName;
        this.language_Name = language_Name;
        this.review_Star = review_Star;
    }

    public LikeModel() {
    }
}
