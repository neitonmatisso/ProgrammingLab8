package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.Exceptions.RecursionOnScriptException;
import ru.nik.businessLogic.factories.ScriptPathChecker;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Команда отвечащая за исполнение скрипта
 */
public class ExecuteScriptCommand implements Command {
    private ControlUnit controlUnit;
    private Scanner sc;
    private File file;

    /**
     *
     * @param c общий блок для всех команд
     *
     */
    public ExecuteScriptCommand(ControlUnit c) {
        controlUnit = c;
        c.addNewCommand("execute", this);
        c.addNewSettings(CommandType.SCRIPT_COMMAND,"execute");

    }
    /**
     * Исполнение скрипта. Каждая строка разбивается по regex. В зависимости от разбиение происходит ввыборка команд
     * @param Option
     */
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        List <String> buffComm = Arrays.asList(Option.split("%"));
        String completeCommands = "";

        for (int i = 0; i < buffComm.size(); i++) {
            completeCommands += "\n" + buffComm.get(i);
        }
        System.out.println(completeCommands);
        int currLine = 0; // подсчет строк
//        ScriptPathChecker.addNewPath(Option);
//        file = new File(Option); // получсем путь к скрипту
        List<String> check = null;
        try {
            sc = new Scanner(completeCommands);
            while (sc.hasNextLine()) {
                resultShell.checkResult("");
                ++currLine;
                String newxtLine = sc.nextLine();
                if (newxtLine.isEmpty()) {
                    continue;
                }

                check = Arrays.asList(newxtLine.split(" "));
                if (controlUnit.getCommandsByType(CommandType.OBJECT_COMMAND).contains(check.get(0))) {
                    String buffer = sc.nextLine() + " " + sc.nextLine() + " " + sc.nextLine() + " " + sc.nextLine() + " " + sc.nextLine();
                    controlUnit.executeCommand("scriptAdd", buffer, resultShell,user);
                    continue;
                }
//                if(check.get(0).equals("execute")){
//                    String pathBuff =check.get(1);
//                    if(ScriptPathChecker.checkRecursion(pathBuff)){
//                        resultShell.checkResult("В скрипте однаружена рекурсия! Скрипт будет прерван");
//                        throw new RecursionOnScriptException();
//                    }
//                    ScriptPathChecker.addNewPath(pathBuff);
//                    controlUnit.executeCommand(check.get(0), check.get(1),resultShell);
//                    continue;
//                }
                if (check.size() == 1) {
                    controlUnit.executeCommand(check.get(0), null, resultShell,user);
                    continue;
                }
                controlUnit.executeCommand(check.get(0), check.get(1), resultShell,user);
            }
            sc.close();
        }
//            ScriptPathChecker.clear();
//        } catch (FileNotFoundException ex){
//            System.out.println("Файл не найден");
//        } catch (RecursionOnScriptException ex2){
//            System.out.println("Скрипт прерван!");
//        }
        catch (Exception e){
            resultShell.checkResult("Ошибка в строке " +currLine);
        }
    }
}
