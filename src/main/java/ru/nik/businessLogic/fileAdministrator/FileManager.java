package ru.nik.businessLogic.fileAdministrator;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.factories.IdController;
import ru.nik.businessLogic.ticketClasses.Ticket;
import com.google.gson.Gson;
import ru.nik.serverPackage.ResultShell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * класс , отвечающий за работу с файлом. Умеет записывать и читать содержимое файла с сериализованой/десериализованой коллекцией
 */
public class FileManager implements IOinterface{
    private File saveFile;
    private ColletionWorker colletionWorker;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Gson jsoner;
    private List<String> jsonList;
    private Scanner scanner;
    public FileManager(ColletionWorker c,String fileAdress) { //TODO добавить в параметры путь к файлу айди
        colletionWorker = c;
        jsonList = new ArrayList<>();
        jsoner = new Gson();
            saveFile = new File(fileAdress);

    }
    public void write(ResultShell resultShell){ //TODO нормально обработать исключения
          try {
              if(colletionWorker.isEmpty()){
                  System.out.println("Коллекция пуста. Запись невозможнна");
                  resultShell.checkResult("Коллекция пуста запись не удет произведена");
                  return;
              }
              bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
              System.out.println("Запись пошла!");
              System.out.println("Фвйл: " + saveFile.getAbsolutePath());
              for(int i =0; i<colletionWorker.getCollection().size(); i++) {
                  bufferedWriter.write(jsoner.toJson(colletionWorker.getCollection().get(i)) + "\n");
              }
              BufferedWriter bf = new BufferedWriter(new FileWriter(IdController.getFilePath()));
              Long buf = IdController.getCurrentId();
              bf.write(buf.toString());
              bf.close();
              bufferedWriter.close();
              resultShell.checkResult("Записьт в файло была успешно произведена");

          } catch (FileNotFoundException ex){
              System.out.println("Файл не найден!");
              resultShell.checkResult("Файл не найден!");
          }
          catch (IOException e){
              System.out.println("Произошла непредвиденная ошибка! Запись в файла не была произведена");
          }
      }

    public void read(ResultShell resultShell) {
        try {
            scanner = new Scanner(saveFile);
            while (scanner.hasNext()) {
                colletionWorker.addToCollection(jsoner.fromJson(scanner.nextLine(), Ticket.class));
            }
            resultShell.checkResult("Данные загружены успешно!");
            scanner.close();
        } catch (IOException e ){
            System.out.println("Чтение из файла было преравано!");
            resultShell.checkResult("чтение из файла было прервано!");
        }

    }

}
