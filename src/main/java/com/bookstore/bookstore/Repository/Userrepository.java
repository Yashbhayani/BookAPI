package com.bookstore.bookstore.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bookstore.bookstore.Model.User;

public interface Userrepository {
    public String Hello();
    public Map<String, Object> Login(User user);
    public Map<String, Object> Checkuser(String token);
    InputStream getResource(String fileName) throws FileNotFoundException;

}
