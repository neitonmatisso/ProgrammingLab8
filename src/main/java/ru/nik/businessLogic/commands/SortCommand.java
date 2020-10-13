package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

/**
 * Команда, отвечающая за сортировку коллекции
 */
public class SortCommand implements Command {
    private ColletionWorker colletionWorker;
    private ControlUnit controlUnit;
    public SortCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("sort",this);
        controlUnit.addNewSettings(CommandType.CLEAR_COMMAND,"sort");

    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        colletionWorker.sort();
        resultShell.checkResult("Коллекция отсортирована");
    }
}
