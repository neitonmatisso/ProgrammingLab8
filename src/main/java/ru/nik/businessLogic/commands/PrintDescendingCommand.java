package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

public class PrintDescendingCommand implements Command {
    private ColletionWorker colletionWorker;
    private ControlUnit controlUnit;
    public PrintDescendingCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("reverse",this);
        controlUnit.addNewSettings(CommandType.CLEAR_COMMAND,"reverse");

    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        resultShell.checkResult(colletionWorker.recerve());
    }
}
