package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

/**
 * Выводит информацию о содержимом коллекции
 */
public class InfoCommand implements Command {
    private ColletionWorker colletionWorker;
    private ControlUnit controlUnit;
    public InfoCommand(ControlUnit c, ColletionWorker cw){
        colletionWorker = cw;
        controlUnit = c;
        c.addNewCommand("show" , this);
        c.addNewSettings(CommandType.CLEAR_COMMAND,"info");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        if (colletionWorker.isEmpty()){
            resultShell.checkResult("Коллекция пуста!");
            return;
        }
        String s = "";
        for(Ticket t: colletionWorker.getCollection()){
            s+= t.toString();
        }
        resultShell.checkResult(s);

    }
}
