package com.bookstore.bookstore.Model;

public class BookModel {
    public String bookName;
    public String bookCategoryName;
    public String image;
    public String thumbnailImage;
    public Double price;
    public String description;
    public String authorName;
    public String language;

    public Double star;
    public Double totalReview;
    public Double booklike;

    public BookModel() {
    }

    public BookModel(String bookName, String bookCategoryName, String image, String thumbnailImage, Double price, String description, String authorName, String language, Double star, Double totalReview, Double booklike) {
        this.bookName = bookName;
        this.bookCategoryName = bookCategoryName;
        this.image = image;
        this.thumbnailImage = thumbnailImage;
        this.price = price;
        this.description = description;
        this.authorName = authorName;
        this.language = language;
        this.star = star;
        this.totalReview = totalReview;
        this.booklike = booklike;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCategoryName() {
        return bookCategoryName;
    }

    public void setBookCategoryName(String bookCategoryName) {
        this.bookCategoryName = bookCategoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Double getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Double totalReview) {
        this.totalReview = totalReview;
    }

    public Double getBooklike() {
        return booklike;
    }

    public void setBooklike(Double booklike) {
        this.booklike = booklike;
    }
}

