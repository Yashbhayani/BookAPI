package com.bookstore.bookstore.Repository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface AuthJwtRepository {
    public String generateToken(String username);
    public String getUsernameFromToken(String token);
    public Jws<Claims> getClaims(String token);
    public boolean isTokenValid(String token);

    public String BookidEncrypt(int bookid) throws Exception;
    public String BookidDecrypt(String bookid) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

}
