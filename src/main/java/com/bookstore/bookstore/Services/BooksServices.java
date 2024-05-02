package com.bookstore.bookstore.Services;

import com.bookstore.bookstore.Model.*;
import com.bookstore.bookstore.Repository.AuthJwtRepository;
import com.bookstore.bookstore.Repository.BooksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service    
public class BooksServices  implements BooksRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final AuthJwtRepository authjwtrepository;

    @Value("${project.images}")
    private String path;

    @Value("${project.thumbnailimage}")
    private String thumbnailimagepath;
    public BooksServices(AuthJwtRepository authjwtrepository) {
        this.authjwtrepository = authjwtrepository;
    }
    public Map<String, Object> insertBookData(
            String Token,
            String BookName,
            int Price,
            String Description,
            String Author_Name,
            MultipartFile Thumbnailimage,
            MultipartFile Image,
            String language
    ) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try{
            int maxDescriptionLength = 500;
            if(Description.length()>500){
                String query = "SELECT CASE WHEN COUNT(*) = 1 " +
                        "THEN 'True' ELSE 'False' END AS Result, " +
                        "id  AS  ID " +
                        "FROM usertable WHERE Email = ? && Role = 'Admin'";
                Map<String, Object>  result = jdbcTemplate.queryForMap(query, new Object[]{Token});
                if ("True".equals((String) result.get("Result"))) {
                    String newQuery = "INSERT INTO bookstable (userid, BookName, Price, Description, Author_Name) VALUES (?, ?, ?, ?, ?, ?)";
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    int UID = (int) result.get("ID");
                    jdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(newQuery, Statement.RETURN_GENERATED_KEYS);
                        ps.setDouble(1, UID);
                        ps.setString(2, BookName);
                        ps.setDouble(4, Price);
                        ps.setString(5, Description);
                        ps.setString(6, Author_Name);
                        return ps;
                    }, keyHolder);
                        Long generatedId = keyHolder.getKey().longValue();

                        String Thumbnailname = Thumbnailimage.getOriginalFilename();
                        String ThumbnailrendomId = UUID.randomUUID().toString();
                        String ThumbnailFileRandomName = ThumbnailrendomId.concat(Thumbnailname.substring(Thumbnailname.lastIndexOf(".")));
                        String ThumbnailFullPath = thumbnailimagepath + File.separator + ThumbnailFileRandomName;
                        File Thumbnailf = new File(thumbnailimagepath);
                        if (!Thumbnailf.exists()) {
                            Thumbnailf.mkdir();
                        }
                        Files.copy(Image.getInputStream(), Paths.get(ThumbnailFullPath));
                        String ThumbnailimageQuery = "INSERT INTO thumbnailimagetable (bookId, image) VALUES (?, ?)";
                        jdbcTemplate.update(ThumbnailimageQuery, generatedId, ThumbnailFileRandomName);

                        String name = Image.getOriginalFilename();
                        String rendomId = UUID.randomUUID().toString();
                        String FileRandomName = rendomId.concat(name.substring(name.lastIndexOf(".")));
                        String FullPath = path + File.separator + FileRandomName;
                        File f = new File(path);
                        if (!f.exists()) {
                            f.mkdir();
                        }
                        Files.copy(Image.getInputStream(), Paths.get(FullPath));
                        String ImagetableQuery = "INSERT INTO imagetable (bookId, image) VALUES (?, ?)";
                        jdbcTemplate.update(ImagetableQuery, generatedId, FileRandomName);


                    String Query = "INSERT INTO languagetable (bookId, language) VALUES (?, ?)";
                    jdbcTemplate.update(Query, generatedId, language);

                    response.put("Message", "Data Added!");
                    response.put("Success", true);
                }else {
                    response.put("Message", "User can't add it!");
                    response.put("Success", false);
                }
            }else{
                response.put("Message", "Up to 500");
                response.put("Success", false);
            }
        }catch (Exception e){
            response.put("Message", e.getMessage());
            response.put("Success", false);
        }
        return  response;
    }

    public Map<String, Object> getBookData(String Token, Integer bookitem) {
        Map<String, Object> response = new HashMap<>();
        try{
            if (authjwtrepository.isTokenValid(Token)) {
                String username = authjwtrepository.getUsernameFromToken(Token);
                String query = "CALL CheckUserRole(?)";
                Map<String, Object> result = jdbcTemplate.queryForMap(query, new Object[]{username});
                String userRoleResult = (String) result.get("Result");
                if (userRoleResult != null) {
                    boolean isAdmin = Boolean.parseBoolean(userRoleResult);
                    if (isAdmin) {
                        String procedureCall = "CALL GetBooksWithReviewStars(?)";

                        List<BooksModel> booksList = jdbcTemplate.query(procedureCall, new Object[]{bookitem}, new RowMapper<BooksModel>() {
                            @Override
                            public BooksModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                                BooksModel book = new BooksModel();
                                try {
                                    book.setId(authjwtrepository.BookidEncrypt(rs.getInt("BookID")));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                book.setLanguageid(rs.getInt("LanguageID"));
                                book.setBookName(rs.getString("BookName"));
                                book.setLanguage_Name(rs.getString("language_Name"));
                                book.setImage(rs.getString("Image"));
                                book.setPrice(rs.getDouble("Price"));
                                book.setAuthor_Name(rs.getString("Author_Name"));
                                book.setReview_Star(rs.getDouble("Review_Star"));
                                return book;
                            }
                        });
                        response.put("data", booksList);
                        response.put("Success", true);
                        response.put("Code", 200);
                    } else {
                        String procedureCall = "CALL GetBooksForUserAdmin(?,?)";
                        List<BooksModel> booksList = jdbcTemplate.query(procedureCall, new Object[]{username,bookitem}, new RowMapper<BooksModel>() {
                            @Override
                            public BooksModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                                BooksModel book = new BooksModel();
                                try {
                                    book.setId(authjwtrepository.BookidEncrypt(rs.getInt("BookID")));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }                                book.setLanguageid(rs.getInt("LanguageID"));
                                book.setBookName(rs.getString("BookName"));
                                book.setLanguage_Name(rs.getString("language_Name"));
                                book.setImage(rs.getString("Image"));
                                book.setPrice(rs.getDouble("Price"));
                                book.setAuthor_Name(rs.getString("Author_Name"));
                                book.setReview_Star(rs.getDouble("Review_Star"));
                                return book;
                            }
                        });
                        response.put("data", booksList);
                        response.put("Success", true);
                        response.put("Code", 200);
                    }
                }else {
                    response.put("Message", "User is Not valid");
                    response.put("Success", false);
                }
            }else{
                response.put("Message", "User is Not valid");
                response.put("Success", false);
            }
        }catch (Exception e){
            response.put("Message", e.getMessage());
            response.put("Success", false);
        }
        return response;
    }

    public Map<String, Object> getBook(String Token, String bookId) {
        Map<String, Object> response = new HashMap<>();
        try{
            if (authjwtrepository.isTokenValid(Token)) {
                String username = authjwtrepository.getUsernameFromToken(Token);
                String userValidationQuery = "SELECT CASE WHEN COUNT(*) = 1 THEN 'True' ELSE 'False' END AS Result FROM usertable WHERE Email = ?";
                String userResult = jdbcTemplate.queryForObject(userValidationQuery, new Object[]{username}, String.class);

                if ("True".equals(userResult)) {
                    int Bid =  Integer.parseInt(authjwtrepository.BookidDecrypt(bookId));
                    String procedureCall = "CALL BookAllDetails(?)";

                    List<BookModel> booksList = jdbcTemplate.query(procedureCall, new Object[]{Bid}, new RowMapper<BookModel>() {
                        @Override
                        public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                                BookModel book = new BookModel();
                                book.setBookName(rs.getString("BookName"));
                                book.setBookCategoryName(rs.getString("BookCategoryName"));
                                book.setImage(rs.getString("Image"));
                                book.setThumbnailImage(rs.getString("ThumbnailImage"));
                                book.setPrice(rs.getDouble("Price"));
                                book.setDescription(rs.getString("Description"));
                                book.setAuthorName(rs.getString("Author_Name"));
                                book.setLanguage(rs.getString("language_Name"));
                                book.setStar(rs.getDouble("Review_Star"));
                                book.setTotalReview(rs.getDouble("TotalReview"));
                                book.setBooklike(rs.getDouble("BookLike"));
                                return book;
                        }
                    });
                    if (!booksList.isEmpty()) {
                        response.put("data", booksList);
                        response.put("Success", true);
                        response.put("Code", 200);
                    } else {
                        response.put("Message", "Book not found for the specified language.");
                        response.put("Success", false);
                    }
                } else {
                    response.put("Message", "User is Not valid");
                    response.put("Success", false);
                }
            } else {
                response.put("Message", "User is Not valid");
                response.put("Success", false);
            }
        }catch (Exception e){
            response.put("Message", e.getMessage());
            response.put("Success", false);
        }
        return response;
    }

    @Override
    public Map<String, Object> getcategory(String Token, Integer Categoryitem) {
        Map<String, Object> response = new HashMap<>();
        try{
            if (authjwtrepository.isTokenValid(Token)) {
                String username = authjwtrepository.getUsernameFromToken(Token);
                String query = "CALL CheckUserRole(?)";
                Map<String, Object> result = jdbcTemplate.queryForMap(query, new Object[]{username});
                String userRoleResult = (String) result.get("Result");
                if (userRoleResult != null) {
                    boolean isAdmin = Boolean.parseBoolean(userRoleResult);
                    if (isAdmin) {
                        String CategoryList = "CALL CategoryList(?)";
                        List<CategoryModel> categoryModelsList = jdbcTemplate.query(CategoryList, new Object[]{Categoryitem}, new RowMapper<CategoryModel>() {
                            @Override
                            public CategoryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                                CategoryModel categoryModel = new CategoryModel();
                                try {
                                    categoryModel.setId(authjwtrepository.BookidEncrypt(rs.getInt("ID")));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                categoryModel.setCategoryName(rs.getString("CategoryName"));
                                categoryModel.setActiveOrNot(rs.getBoolean("ActiveOrNot"));
                                return categoryModel;
                            }
                        });
                        response.put("data", categoryModelsList);
                        response.put("Success", true);
                        response.put("Code", 200);
                    }
                    else {
                        response.put("Message", "User is Not valid");
                        response.put("Success", false);
                    }
                }else {
                    response.put("Message", "User is Not valid");
                    response.put("Success", false);
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
        return response;    }

    public Map<String, Object> dashboard(String Token){
        Map<String, Object> response = new HashMap<>();
        try{
            if (authjwtrepository.isTokenValid(Token)) {
                String username = authjwtrepository.getUsernameFromToken(Token);
                String query = "CALL CheckUserRole(?)";
                Map<String, Object> result = jdbcTemplate.queryForMap(query, new Object[]{username});
                String userRoleResult = (String) result.get("Result");
                if (userRoleResult != null) {
                    boolean isAdmin = Boolean.parseBoolean(userRoleResult);
                    if (isAdmin) {
                        String procedureCall = "CALL AdminDashboard()";

                        List<DashboardModel> dashboardList = jdbcTemplate.query(procedureCall, new Object[]{}, new RowMapper<DashboardModel>() {
                            @Override
                            public DashboardModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                                DashboardModel dashboard = new DashboardModel();
                                dashboard.setTotalUser(rs.getDouble("TotalUser"));
                                dashboard.setTotalBook(rs.getDouble("TotalBook"));
                                dashboard.setTotalLike(rs.getDouble("TotalLike"));
                                dashboard.setTotalAverageReview(rs.getDouble("AverageReview"));
                                return dashboard;
                            }
                        });
                        response.put("data", dashboardList);
                        response.put("Success", true);
                        response.put("Code", 200);
                    } else {
                        String procedureCall = "CALL UserAdminDashboard(?)";
                        List<DashboardModel> dashboardList = jdbcTemplate.query(procedureCall, new Object[]{username}, new RowMapper<DashboardModel>() {
                            @Override
                            public DashboardModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                                DashboardModel dashboard = new DashboardModel();
                                dashboard.setTotalUser(Double.valueOf(0));
                                dashboard.setTotalBook(rs.getDouble("TotalBook"));
                                dashboard.setTotalLike(rs.getDouble("TotalLike"));
                                dashboard.setTotalAverageReview(rs.getDouble("AverageReview"));
                                return dashboard;
                            }
                        });
                        response.put("data", dashboardList);
                        response.put("Success", true);
                        response.put("Code", 200);
                    }
                }else {
                    response.put("Message", "User is Not valid");
                    response.put("Success", false);
                }
            }else{
                response.put("Message", "User is Not valid");
                response.put("Success", false);
            }
        }catch (Exception e){
            response.put("Message", e.getMessage());
            response.put("Success", false);
        }
        return response;
    }

    @Override
    public Map<String, Object> chart(String Token) {
        return null;
    }

    public InputStream getResource(String Type, String fileName) throws FileNotFoundException {
        if(Type == "bookthumbnailimage") {
            String fullpath = thumbnailimagepath + File.separator + fileName;
            InputStream is = new FileInputStream(fullpath);
            return is;
        }else {
            String fullpath = path + File.separator + fileName;
            InputStream is = new FileInputStream(fullpath);
            return is;
        }
    }
}
