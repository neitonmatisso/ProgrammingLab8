package ru.nik.businessLogic.factories;

import java.util.HashSet;
import java.util.Set;

public class ScriptPathChecker {
    private static Set<String> paths = new HashSet<>();
    public static void addNewPath(String s){
        paths.add(s);
    }
    public static boolean checkRecursion(String s){
        return paths.contains(s);
    }
    public static void clear(){
        paths.clear();
    }
}
