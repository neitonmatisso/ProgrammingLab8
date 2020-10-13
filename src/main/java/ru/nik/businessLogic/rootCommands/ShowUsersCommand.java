package ru.nik.businessLogic.rootCommands;

import ru.nik.businessLogic.commands.Command;
import ru.nik.businessLogic.commands.CommandType;
import ru.nik.businessLogic.commands.ControlUnit;
import ru.nik.serverPackage.MessegeManager;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.AccountManager;
import ru.nik.serverPackage.accounts.User;

public class ShowUsersCommand implements Command {
    private AccountManager accountManager;
    private ControlUnit controlUnit;
    public ShowUsersCommand(ControlUnit c, AccountManager accountManager){
        controlUnit = c;
        this.accountManager = accountManager;
        c.addNewCommand("show_users" , this);
        c.addNewSettings(CommandType.CLEAR_COMMAND,"show_users");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        resultShell.checkResult(accountManager.gerUserNames().toString());
    }
}
