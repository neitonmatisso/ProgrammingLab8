package ru.nik.GUI.MainForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Localizator {

    private Map<String, String>  enMapOptions;
    private Map <String, String> ruMap;

    public Localizator(){
        enMapOptions = new HashMap<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File("OptionLayoutLocale/enSource.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка файла локализации");
            return;
        }
        while(sc.hasNextLine()){
            List<String> str = Arrays.asList(sc.nextLine().split("="));
            enMapOptions.put(str.get(0),str.get(1));
        }
    }

    public Map<String,String> getEnMapOptions(){
        return enMapOptions;
    }



}
