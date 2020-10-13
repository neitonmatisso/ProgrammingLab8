package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.fileAdministrator.FileManager;
import ru.nik.businessLogic.fileAdministrator.IOinterface;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;
import ru.nik.serverPackage.accounts.UserPriority;

/**
 * выполняет сериализацию объектов из коллекции и записывает в файл
 */
public class SaveCommand implements Command {
    private IOinterface fileManager;
    private ControlUnit controlUnit;
    public SaveCommand(ControlUnit c, FileManager f){
        fileManager = f;
        controlUnit = c ;
        controlUnit.addNewCommand("save",this);
        controlUnit.addNewSettings(CommandType.CLEAR_COMMAND, "save");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        if(!user.getPriority().equals(UserPriority.ROOT)){
            resultShell.checkResult("У вас недостаточно прав для выполения этой команды");
            return;
        }
        fileManager.write(resultShell);
    }
}
