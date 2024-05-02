package com.bookstore.bookstore.Model;

public class DashboardModel {
    public Double totalUser;
    public Double totalBook;
    public Double totalLike;
    public Double totalAverageReview;

    public DashboardModel() {
    }

    public DashboardModel(Double totalUser, Double totalBook, Double totalLike, Double totalAverageReview) {
        this.totalUser = totalUser;
        this.totalBook = totalBook;
        this.totalLike = totalLike;
        this.totalAverageReview = totalAverageReview;
    }

    public Double getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Double totalUser) {
        this.totalUser = totalUser;
    }

    public Double getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(Double totalBook) {
        this.totalBook = totalBook;
    }

    public Double getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Double totalLike) {
        this.totalLike = totalLike;
    }

    public Double getTotalAverageReview() {
        return totalAverageReview;
    }

    public void setTotalAverageReview(Double totalAverageReview) {
        this.totalAverageReview = totalAverageReview;
    }
}
