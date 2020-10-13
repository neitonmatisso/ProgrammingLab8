package ru.nik.serverPackage.accounts;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordIncoder {
    public  String hash(String password, String salt){
        try{
            String pepper = "22&3CdsFgh2cL97#3";
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] data = (pepper + password + salt).getBytes(StandardCharsets.UTF_8);
            byte[] hashbytes = md.digest(data);
            return Base64.getEncoder().encodeToString(hashbytes);
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }

    public  String getSalt(){
        byte[] salt = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        return new String(salt, StandardCharsets.UTF_8);
    }

}
