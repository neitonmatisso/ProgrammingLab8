package ru.nik.businessLogic.commands;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Команда help. Позволяет получит ьинформацию по любой команде
 * содержит в себе мапу всех информаций о командах. Ключ - имя , Значение - пояленение
 */
public class HelpCommand implements Command{
    private File directory; //директория для подкачки
    private Map<String,String> CommandInfo; // КЛюч - имя команды Значение - рписание команды
    public HelpCommand(ControlUnit c){ //подулючаемся к КотролЮниту для передачи задачи
        CommandInfo = new HashMap<>();
        loadInfo();
        c.addNewCommand("help", this);
        c.addNewSettings(CommandType.CLEAR_COMMAND,"help");
    }

    /**
     * Загружает информацию из директории , где находятся поялнения ко всем фалйам
     * цикл пробегается по всем файлам в директории и осущетсвляет маппинг
     */

    private void loadInfo() {
//        directory = new File("commanInfo"); //все файлы с информации по объектам
//        try {
//            for (int i = 0; i < directory.listFiles().length; i++) {
//                BufferedReader br = new BufferedReader(new FileReader(directory.listFiles()[i]));
//                CommandInfo.put(br.readLine(), br.readLine()); //первая строка всегда содержит имя команды. Она помещается как ключ
//            }
//        } catch (IOException e){
//            System.out.println("ИНформация о файлах не загружена ");
//        }

    }
    @Override
    public void execute(String option, ResultShell resultShell, User user) {
        System.out.println(CommandInfo);
        String buff = "Каждая кнопочка что-то делает \n" +
                "нажимай - не стеснйся)";

        resultShell.checkResult(buff);
    }
}
