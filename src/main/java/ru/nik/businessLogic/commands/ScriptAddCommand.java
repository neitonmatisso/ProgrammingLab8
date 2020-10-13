package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.factories.Factory;
import ru.nik.businessLogic.factories.TicketFactory;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

public class ScriptAddCommand implements Command {
    Factory factory;
    ColletionWorker worker;

    /**
     * @param c - ControlUnit - класс , связывающий все команды в одной мапе
     * @param worker - Ccылка на класс-обертку для рабочей коллекцией
     */
    public ScriptAddCommand(ControlUnit c, ColletionWorker worker){
        factory = new TicketFactory();
        this.worker = worker;
        c.addNewCommand("scriptAdd",this);
        c.addNewSettings(CommandType.OBJECT_COMMAND, "scriptAdd");
    }
    @Override
    public  synchronized void execute(String Option, ResultShell resultShell, User user) {
        worker.addToCollection(factory.createTicketParams(Option));
        resultShell.checkResult("Новый элемент добавлен в коллекцию!");
    }
}
