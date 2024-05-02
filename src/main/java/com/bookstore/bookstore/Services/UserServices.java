package com.bookstore.bookstore.Services;

import com.bookstore.bookstore.Model.*;
import com.bookstore.bookstore.Repository.AuthJwtRepository;
import com.bookstore.bookstore.Repository.Userrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Service
public class UserServices implements Userrepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final AuthJwtRepository authjwtrepository;
    private  AuthJwt authJwt;
    @Value("${project.image}")
    private String path;

    public UserServices(AuthJwtRepository authjwtrepository) {
        this.authjwtrepository = authjwtrepository;
    }


    public String Hello() {
        return "Hello World!";
    }


    /*    public String insertUser(String FirstName, String LastName, String Email, String Password, MultipartFile Image) throws IOException {
            String query = "SELECT CASE WHEN COUNT(*) = 1 THEN 'True' ELSE 'False' END AS Result FROM usertable WHERE Email = ?";
            String result = jdbcTemplate.queryForObject(query, new Object[]{Email}, String.class);
            if ("False".equals(result)) {
                String name = Image.getOriginalFilename();
                String rendomId = UUID.randomUUID().toString();
                String FileRandomName = rendomId.concat(name.substring(name.lastIndexOf(".")));
                String FullPath = path + File.separator + FileRandomName;
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdir();
                }
                Files.copy(Image.getInputStream(), Paths.get(FullPath));
                String newQuery = "INSERT INTO usertable (FirstName, LastName, Email, image, PassWord) VALUES (?, ?, ?, ?, ?)";
                jdbcTemplate.update(newQuery, FirstName, LastName, Email, FileRandomName, Password);
                return "User Added successfully";
            } else {
                return "Email Id is already added!";
            }
        }*/
    public Map<String, Object> Login(User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            String query = "SELECT \n" +
                    "Email,\n" +
                    "CASE \n" +
                    "WHEN r.value LIKE '%Admin%' THEN 'TRUE' \n" +
                    "ELSE 'FALSE' \n" +
                    "END AS Role,\n" +
                    "CASE \n" +
                    "WHEN COUNT(*) = 1 THEN 'TRUE' \n" +
                    "ELSE 'FALSE' \n" +
                    "END AS Result\n" +
                    "FROM \n" +
                    "usertable as user\n" +
                    "LEFT JOIN \n" +
                    "role as r ON user.roleid = r.id\n" +
                    "WHERE \n" +
                    "user.Email = ? AND user.PassWord = ?";
            LoginModel kl = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(LoginModel.class), user.getEmail(), user.getPassword());
            if ("TRUE".equals(kl.getRole())) {
                if(Boolean.valueOf(kl.getResult())){
                    String token = authjwtrepository.generateToken(kl.getEmail());
                    response.put("token", token);
                    response.put("status", true);
                    response.put("Message", "Login is Successfully!");
                }else{
                    response.put("Message", "User is not valid!");
                    response.put("status", false);
                }
            } else {
                response.put("Message", "User is not valid!");
                response.put("status", false);
            }
            return response;
        } catch (EmptyResultDataAccessException e) {
            response.put("Message", e.getMessage());
            response.put("status", false);
            return response;
        }
    }

    @Override
    public Map<String, Object> Checkuser(String token) {
        Map<String, Object> response = new HashMap<>();
        try{
            if (authjwtrepository.isTokenValid(token)) {
                String username = authjwtrepository.getUsernameFromToken(token);
                String query = "CALL CheckUserRole(?)";
                Map<String, Object> result = jdbcTemplate.queryForMap(query, new Object[]{username});
                String userRoleResult = (String) result.get("Result");
                if (userRoleResult != null) {
                    boolean isAdmin = Boolean.parseBoolean(userRoleResult);
                    response.put("data", isAdmin);
                    response.put("Success", true);
                    response.put("Code", 200);
                }else{
                    response.put("Message", true);
                    response.put("Code", 200);
                }
            }
                else {
                    response.put("Message", "User is Not valid");
                    response.put("Success", false);
                }
            }
            catch (Exception e){
                response.put("Message", e.getMessage());
                response.put("Success", false);
            }
            return response;
    }

    public InputStream getResource(String fileName) throws FileNotFoundException {
            String fullpath = path + File.separator + fileName;
            InputStream is = new FileInputStream(fullpath);
            return is;
    }

}
