package com.bookstore.bookstore.Controller;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import com.bookstore.bookstore.Model.ReviewModel;
import com.bookstore.bookstore.Repository.BooksRepository;
import org.springframework.web.multipart.MultipartFile;

@ComponentScan(basePackages = "com.bookstore.bookstore.Services")
@Configuration
@RestController
@RequestMapping("api")
public class BooksController {

    @Autowired
    private final BooksRepository booksRepository;

    public BooksController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @PostMapping("/addbooks")
    public Map<String, Object> AddBooks(@RequestHeader("token") String Token,
                                        @RequestParam("bookName")  String BookName,
                                        @RequestParam("price") int Price,
                                        @RequestParam("description") String Description,
                                        @RequestParam ("author_Name") String Author_Name,
                                        @RequestParam ("thumbnailimage") MultipartFile Thumbnailimage,
                                        @RequestParam ("image") MultipartFile Image,
                                        @RequestParam("language") String language) throws IOException {

      return  booksRepository.insertBookData(
        Token, BookName, Price, Description, Author_Name, Thumbnailimage, Image, language
      );
    }
    @GetMapping(value = "/bookthumbnailimage/{imagename}")
        public void bookthumbnailimage(@PathVariable("imagename") String imageName,
                              HttpServletResponse response) throws IOException {
            InputStream resource = booksRepository.getResource("bookthumbnailimage",imageName);

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


    @GetMapping(value = "/bookimage/{imagename}")
    public void bookImage(@PathVariable("imagename") String imageName,
                          HttpServletResponse response) throws IOException {
        InputStream resource = booksRepository.getResource("bookimage", imageName);

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
    @GetMapping("/books")
    public Map<String, Object> GetBooks(
            @RequestHeader("token") String Token,
            @RequestParam("bookitem") int Bookitem) throws IOException {
        return  booksRepository.getBookData(Token, Bookitem);
    }

    @GetMapping("/getbook")
    public Map<String, Object> GetBook(
            @RequestHeader("token") String Token,
            @RequestParam("bookid") String bookid
    ) throws IOException {
        return  booksRepository.getBook(Token, bookid);
    }

    @GetMapping("/category")
    public Map<String, Object> Category(
            @RequestHeader("token") String Token,
            @RequestParam("categoryitem") int Categoryitem
    ) throws IOException {
        return  booksRepository.getcategory(Token, Categoryitem);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> Dashboard(
            @RequestHeader("token") String Token){
        return booksRepository.dashboard(Token);
    }

    @GetMapping("/chart")
    public Map<String, Object> Chart(
            @RequestHeader("token") String Token){
        return booksRepository.chart(Token);
    }
}
