package ru.nik.businessLogic.mainPackage;

import ru.nik.businessLogic.ArrayListWorker;
import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.factories.IdController;
import ru.nik.businessLogic.fileAdministrator.FileManager;
import ru.nik.businessLogic.commands.*;

import java.util.*;

/**
 * Main class with main method
 * @author Petrenko Nikita
 */

public class Main {
    public static void main(String[] args)  {
        ColletionWorker coll = new ArrayListWorker();
        ControlUnit cunit = new ControlUnit();
        IdController idController = new IdController("idFile");
        FileManager fi = new FileManager(coll,"SaveFile");
        Command add = new AddElementCommand(cunit,coll);
        Command load = new LoadCommand(cunit,fi);
        Command save = new SaveCommand(cunit,fi);
        Command info = new InfoCommand(cunit,coll);
        Command execute = new ExecuteScriptCommand(cunit);
        Command addIfMax = new AddIfMaxCommand(cunit,coll);
        Command addIfMin = new AddIfMinCommand(cunit,coll);
        Command remove = new RemoveCommand(cunit,coll);
        Command removeGreate = new RemoveGreaterCommand(cunit,coll);
        Command clear = new ClearCommand(cunit,coll);
        Command update = new UpdateCommand(cunit,coll);
        Command sort = new SortCommand(cunit,coll);
        System.out.println("Добро пожаловать! Познакомиться с устройством программы вы сможете с помощью команды help");
        String komandLine;
        Scanner sc;
//      while (true) { //TODO обработк аисключения. Временные костыли убрать.
//          try {
//              System.out.print("> ");
//               sc =  new Scanner(System.in);
//               komandLine = sc.nextLine();
//              List<String> komOpt = Arrays.asList(komandLine.split(" "));
//              if(komOpt.get(0).equals("")){
//                  continue;
//              }
//              if (komOpt.size() == 1) {
//                  cunit.executeCommand(komOpt.get(0), null,);
//              } else if (komOpt.size() == 2) {
//                  cunit.executeCommand(komOpt.get(0), komOpt.get(1));
//              }
//          } catch (NullPointerException e){
//              System.out.println("Такой команды не сущестует! Попробуйте снова");
//          } catch (NoSuchElementException ex){
//              System.out.println(ex.fillInStackTrace());
//              System.exit(2);
//
//          }
//      }

    }
}

