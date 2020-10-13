package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;
import ru.nik.serverPackage.accounts.UserPriority;

import java.util.List;

/**
 * Команда , которая отвечвет за очистку рабочей коллекции
 */
public class ClearCommand implements Command {
    private ColletionWorker colletionWorker;
    private ControlUnit controlUnit;
    private TicketDAO ticketDAO;
    /**
     * @param cu - ControlUnit - класс , связывающий все команды в одной мапе
     * @param cw - Ccылка на класс-обертку для рабочей коллекцией
     */
    public ClearCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("clear",this);
        controlUnit.addNewSettings(CommandType.CLEAR_COMMAND,"clear");
        ticketDAO = new TicketDAO();

    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        if(!user.getPriority().equals(UserPriority.ROOT)){
            List<Ticket> buffer = colletionWorker.getByUserLogin(user.getLogin());
            if(buffer.isEmpty()){
                resultShell.checkResult("В коллекции нету ваших элементов.");
                return;
            } else {
                colletionWorker.removeByUserLogin(user.getLogin());
                ticketDAO.delete(buffer);
                resultShell.checkResult("Удаление успешно произведено, удаленных элементов: "+ buffer.size());
                return;
            }
        } else if(user.getPriority().equals(UserPriority.ROOT)) {
            colletionWorker.clear();
            ticketDAO.delete(colletionWorker.getCollection());
            resultShell.checkResult("Коллекция была очищена!");
            return;
        }


    }
}
