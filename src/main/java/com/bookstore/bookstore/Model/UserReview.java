package com.bookstore.bookstore.Model;

public class UserReview {
    public int id;
    public int userid;
    public String firstName;
    public String lastName;
    public Double averageReview;
    public String allComments;
    public Boolean loginUser;

    public UserReview(int id, int userid, String firstName, String lastName, Double averageReview, String allComments, Boolean loginUser) {
        this.id = id;
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageReview = averageReview;
        this.allComments = allComments;
        this.loginUser = loginUser;
    }

    public UserReview(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getAverageReview() {
        return averageReview;
    }

    public void setAverageReview(Double averageReview) {
        this.averageReview = averageReview;
    }

    public String getAllComments() {
        return allComments;
    }

    public void setAllComments(String allComments) {
        this.allComments = allComments;
    }

    public Boolean getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Boolean loginUser) {
        this.loginUser = loginUser;
    }

}
