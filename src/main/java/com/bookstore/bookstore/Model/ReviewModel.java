package com.bookstore.bookstore.Model;

public class ReviewModel {

    public int Review;
    public String Comment;

    public ReviewModel(int review, String comment) {
        Review = review;
        Comment = comment;
    }

    public ReviewModel() {
    }

    public int getReview() {
        return Review;
    }

    public void setReview(int review) {
        Review = review;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
