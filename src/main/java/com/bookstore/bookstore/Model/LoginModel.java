package com.bookstore.bookstore.Model;

import org.springframework.http.HttpStatusCode;

public class LoginModel {
    public String email;
    public String role;
    public String result;

    public LoginModel(String email, String role, String result) {
        this.email = email;
        this.role = role;
        this.result = result;
    }

    public LoginModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
