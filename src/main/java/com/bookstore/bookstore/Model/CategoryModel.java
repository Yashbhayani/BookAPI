package com.bookstore.bookstore.Model;

public class CategoryModel {
    public  String id;
    public String CategoryName;
    public boolean ActiveOrNot;

    public CategoryModel(String id, String categoryName, boolean activeOrNot) {
        this.id = id;
        CategoryName = categoryName;
        ActiveOrNot = activeOrNot;
    }

    public CategoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public boolean isActiveOrNot() {
        return ActiveOrNot;
    }

    public void setActiveOrNot(boolean activeOrNot) {
        ActiveOrNot = activeOrNot;
    }
}
