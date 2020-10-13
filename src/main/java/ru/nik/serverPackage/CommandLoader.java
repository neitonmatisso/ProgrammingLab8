package ru.nik.serverPackage;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.commands.*;
import ru.nik.businessLogic.fileAdministrator.FileManager;
import ru.nik.businessLogic.rootCommands.SendMessageCommand;
import ru.nik.businessLogic.rootCommands.ShowUsersCommand;
import ru.nik.serverPackage.accounts.AccountManager;

public class CommandLoader {
    public CommandLoader(ColletionWorker coll, ControlUnit cunit, FileManager fi, AccountManager accountManager){
        Command add = new AddElementCommand(cunit,coll);
        Command info = new InfoCommand(cunit,coll);
        Command show = new ShowCommand(cunit,coll);
        Command help= new HelpCommand(cunit);
        Command execute = new ExecuteScriptCommand(cunit);
        Command addIfMax = new AddIfMaxCommand(cunit,coll);
        Command addIfMin = new AddIfMinCommand(cunit,coll);
        Command remove = new RemoveCommand(cunit,coll);
        Command removeGreate = new RemoveGreaterCommand(cunit,coll);
        Command clear = new ClearCommand(cunit,coll);
        Command sort = new SortCommand(cunit,coll);
        Command scradd = new ScriptAddCommand(cunit,coll);
        Command filter = new FilterGreaterThanPriceCommand(cunit,coll);
        Command recive = new PrintDescendingCommand(cunit,coll);
        Command sendMessage = new SendMessageCommand(cunit,accountManager);
        Command showUser = new ShowUsersCommand(cunit,accountManager);
        Command update = new UpdateCommand(cunit, coll);
    }
}
