package com.bookstore.bookstore.Services;

import com.bookstore.bookstore.Repository.AuthJwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
@Service
public class AuthJwt implements AuthJwtRepository {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    String secretKey = "ThisIsASecretKey";
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        /*
        List<UU> uuList = new ArrayList<>();
        UU uu1 = new UU(1, username);
        uuList.add(uu1);
        ObjectMapper objectMapper = new ObjectMapper();
        String uuListJson;
        uuListJson = objectMapper.writeValueAsString(uuList); */
        try {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key)
                    .compact();
        } catch (Exception e) {
            return  e.getMessage();
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getBody().getSubject();
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String BookidEncrypt(int bookid) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        try {
            String bookidString = String.valueOf(bookid);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(bookidString.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String BookidDecrypt(String encryptedBookid) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBookid);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedBookid = new String(decryptedBytes);
            return decryptedBookid;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
