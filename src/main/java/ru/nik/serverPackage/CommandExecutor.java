package ru.nik.serverPackage;

import ru.nik.businessLogic.Exceptions.ExitException;
import ru.nik.businessLogic.commands.ControlUnit;
import ru.nik.serverPackage.accounts.AccountManager;
import ru.nik.serverPackage.accounts.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class CommandExecutor implements Callable<ResultShell> {
    private String executableCommand;
    private ResultShell resultShell;
    private ControlUnit cunit;
    private AccountManager accountManager;

    public CommandExecutor(String executableCommand, ResultShell resultShell, ControlUnit cunit, AccountManager accountManager) {
        this.executableCommand = executableCommand;
        this.resultShell = resultShell;
        this.cunit = cunit;
        this.accountManager = accountManager;
    }

    public void setQuery(String query){
        executableCommand = query;
    }
    public void setResultShell(ResultShell res){
        resultShell = res;
    }
    public void setControlUnit(ControlUnit cu){
        cunit = cu;
    }


    @Override
    public ResultShell call() {
        List<String> komOpt = Arrays.asList(executableCommand.split("!", 3));
        User currentUser = accountManager.getUser(komOpt.get(0));
        System.out.println(komOpt);
        try {
            if (komOpt.size() == 2) {
                cunit.executeCommand(komOpt.get(1), null, resultShell,currentUser);
            } else if (komOpt.size() == 3) {
                cunit.executeCommand(komOpt.get(1), komOpt.get(2), resultShell,currentUser);
            }
        } catch (NullPointerException ex) {
            resultShell.checkResult("Такой команды не существует");
            ex.printStackTrace();
        }
        return resultShell;
    }
}
