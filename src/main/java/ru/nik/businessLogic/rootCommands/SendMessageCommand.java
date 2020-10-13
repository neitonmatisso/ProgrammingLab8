package ru.nik.businessLogic.rootCommands;

import com.google.gson.Gson;
import ru.nik.businessLogic.commands.Command;
import ru.nik.businessLogic.commands.CommandType;
import ru.nik.businessLogic.commands.ControlUnit;
import ru.nik.serverPackage.MessegeManager;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.AccountManager;
import ru.nik.serverPackage.accounts.User;

public class SendMessageCommand implements Command {
    private AccountManager accountManager;
    private ControlUnit controlUnit;
    private MessegeManager messegeManager;
    public SendMessageCommand(ControlUnit c, AccountManager accountManager){
        controlUnit = c;
        this.accountManager = accountManager;
        messegeManager = new MessegeManager(accountManager);
        c.addNewCommand("send_message" , this);
        c.addNewSettings(CommandType.MESSAGE,"send_message");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        try {
            Message message = new Gson().fromJson(Option,Message.class);
            messegeManager.sendAll(user, message.getText());
            resultShell.checkResult("Ваше сообщение отправлено!");
        } catch (NullPointerException ex){
            resultShell.checkResult("Данный пользователь не в сети!");
        }

    }
}
