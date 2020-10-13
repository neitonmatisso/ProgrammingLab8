package ru.nik.businessLogic.commands;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

import java.util.*;

/**
 * Класс , который содержит в себе все команды. Выборка каманды выбирается по имени .Так же ей ( при наличии ) передаются парамемтры
 */
public class ControlUnit {
    private Map<String,Command> commandMap;
    private Map<CommandType, List<String>> settingMap;
    public ControlUnit() {
        commandMap = new HashMap<>();
        settingMap = new HashMap<>();
        addNewTypeOfCommand(CommandType.ARGS_COMMAND);
        addNewTypeOfCommand(CommandType.CLEAR_COMMAND);
        addNewTypeOfCommand(CommandType.OBJECT_COMMAND);
        addNewTypeOfCommand(CommandType.SCRIPT_COMMAND);
        addNewTypeOfCommand(CommandType.MESSAGE);
        settingMap.get(CommandType.CLEAR_COMMAND).add("exit");
    }
    public void addNewCommand(String name,Command newCommand){
        commandMap.put(name,newCommand);
    }
    public void addNewSettings(CommandType type, String name){
        settingMap.get(type).add(name);
    }
    public void addNewTypeOfCommand(CommandType c){
        settingMap.put(c,new ArrayList<>());
    }
    public List getCommandsByType(CommandType commandType){
        return settingMap.get(commandType);
    }
    /**
     * выполнение команды
     * @param name имя команды. Является ключем в паме
     * @param option параметры для некоторых команд
     */
    public  void executeCommand(String name, String option, ResultShell resultShell, User user){
        commandMap.get(name).execute(option,resultShell,user);
    }
    public String getCommands(){
        String s = "";
        Set<String> keySet = commandMap.keySet();
        for (String i : keySet){
            s += i +" ";

        }
        return s;
    }
    public Map getSettings(){
        return settingMap;
    }

}
