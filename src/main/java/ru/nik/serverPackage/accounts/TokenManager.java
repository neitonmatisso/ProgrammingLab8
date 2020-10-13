package ru.nik.serverPackage.accounts;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.SecureRandom;
import java.util.*;

public class TokenManager {
    private List<String> tokens = new ArrayList<>();
    public String getToken(){
        Integer a = new Random().nextInt();
        tokens.add(a.toString());
        return a.toString();
    }


    public boolean checkContains(String s){
        System.out.println(s);
        System.out.println(tokens);
        return tokens.contains(s);
    }


    public  void removeToken(String s){
        tokens.remove(s);
    }
    public String showTokens(){
        return tokens.toString();
    }


}
