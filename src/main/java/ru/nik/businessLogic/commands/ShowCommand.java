package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

public class ShowCommand implements Command{
    ColletionWorker colletionWorker;
    ControlUnit controlUnit;
    public ShowCommand(ControlUnit c, ColletionWorker cw){
        colletionWorker = cw;
        controlUnit = c;
        c.addNewCommand("info" , this);
        c.addNewSettings(CommandType.CLEAR_COMMAND,"show");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {

        resultShell.checkResult("информация о коллекции " +"\n" + colletionWorker.getInfo());

    }
}
