package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.fileAdministrator.FileManager;
import ru.nik.businessLogic.fileAdministrator.IOinterface;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;
import ru.nik.serverPackage.accounts.UserPriority;

/**
 * Данная команда осуществялет десериализацию объектов из файла 
 */
public class LoadCommand implements Command {
    private IOinterface fileManager;
    private ControlUnit controlUnit;
    public LoadCommand(ControlUnit c, FileManager f){
        fileManager = f;
        controlUnit = c ;
        controlUnit.addNewCommand("load",this);
        controlUnit.addNewSettings(CommandType.CLEAR_COMMAND,"load");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        resultShell.checkResult("Данная комнада недоступна в этой версии");
    }
}
