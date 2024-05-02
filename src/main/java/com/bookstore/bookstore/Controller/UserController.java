package com.bookstore.bookstore.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.bookstore.Model.User;
import com.bookstore.bookstore.Repository.AuthJwtRepository;
import com.bookstore.bookstore.Repository.Userrepository;

@ComponentScan(basePackages = "com.bookstore.bookstore.Services")
@Configuration
@RestController
@RequestMapping("api")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final Userrepository userrepository;
    private final AuthJwtRepository authjwtrepository;

    public UserController(Userrepository userrepository, AuthJwtRepository authjwtrepository) {
        this.userrepository = userrepository;
        this.authjwtrepository = authjwtrepository;
    }
        @GetMapping("/hello")
    public String index(){
        return userrepository.Hello();
    }

    @GetMapping(value = "/userimage/{imagename}")
    public void userImage(@PathVariable("imagename") String imageName,
                          HttpServletResponse response) throws IOException {
        InputStream resource = userrepository.getResource(imageName);
        String fileExtension = imageName.substring(imageName.lastIndexOf('.') + 1);
        String contentType;
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        } else if ("png".equalsIgnoreCase(fileExtension)) {
            contentType = MediaType.IMAGE_PNG_VALUE;
        } else {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        }
        response.setContentType(contentType);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user){
        return  userrepository.Login(user);
    }

    @GetMapping("/checkuser")
    public Map<String, Object> checkuser(
            @RequestHeader("token") String Token){
        return  userrepository.Checkuser(Token);
    }

}
