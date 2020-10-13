package ru.nik.businessLogic.rootCommands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.commands.Command;
import ru.nik.businessLogic.commands.CommandType;
import ru.nik.businessLogic.commands.ControlUnit;
import ru.nik.serverPackage.MessegeManager;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.AccountManager;
import ru.nik.serverPackage.accounts.User;
import ru.nik.serverPackage.accounts.UserPriority;

public class UpdatePriorityCommand implements Command {
    private AccountManager accountManager;
    private ControlUnit controlUnit;
    private MessegeManager messegeManager;
    public UpdatePriorityCommand(ControlUnit c, AccountManager accountManager){
        controlUnit = c;
        this.accountManager = accountManager;
        messegeManager = new MessegeManager(accountManager);
        c.addNewCommand("update_priority" , this);
        c.addNewSettings(CommandType.ARGS_COMMAND,"update_priority");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        if(!user.getPriority().equals(UserPriority.ROOT)){
            resultShell.checkResult("У вас недостаточно прав для выполненя  данной команды");
            return;
        }
        User buffUser = accountManager.getUser(Option);
        buffUser.setPriority(UserPriority.ROOT);
        messegeManager.sendMassege(user, Option, "ВЫ ТЕПЕРЬ ROOT");
    }
}
