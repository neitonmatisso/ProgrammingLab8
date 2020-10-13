package ru.nik.businessLogic.factories;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Был вынужден сделать класс статическим из-зв того , что не успевал добавить передачу каждому классу ссылку прошу простить меня за этот говнокод
 * Выдает каждому новому объекту индивидуальный номер 
 */
public class IdController {
    private static File file;
    private static long id;
    private Scanner sc;
    public IdController(String filePath) {
        file = new File(filePath);
        try {
            sc = new Scanner(file);
            if(sc.hasNextLong()) {
                id = sc.nextLong();
            } else {
                id = 0;
            }
        } catch (FileNotFoundException e){
            System.out.println("Файл не наеден");
        } catch (NumberFormatException er){
            System.out.println("В файле произошла внутрянняя ошибкачё");
        }

    }
    public static long getNewId() {
        return ++id;
    }
    public static long getCurrentId(){
        return id;
    }
    public static String getFilePath(){
        return file.getAbsolutePath();
    }


}
