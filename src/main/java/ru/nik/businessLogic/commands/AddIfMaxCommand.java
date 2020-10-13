package ru.nik.businessLogic.commands;
import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.factories.TicketFactory;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

import java.util.NoSuchElementException;

/**
 * Команда , отвечающая за добавление в коллекцию нового билета при определенных условиях
 * Если в новом билете цена превышает цену максимального билета в коллекции - новый билет добавляется
 */
public class AddIfMaxCommand implements Command {
    private ColletionWorker colletionWorker;
    private ControlUnit controlUnit;
    private TicketDAO ticketDAO;
    /**
     * @param cu - ControlUnit - класс , связывающий все команды в одной мапе
     * @param cw - Ccылка на класс-обертку для рабочей коллекцией
     */
    public AddIfMaxCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("add_if_max",this);
        controlUnit.addNewSettings(CommandType.OBJECT_COMMAND,"add_if_max");
        ticketDAO = new TicketDAO();

    }
    @Override
    public synchronized void execute(String Option, ResultShell resultShell, User user) {
        Ticket t = new TicketFactory().createTicketFromJson(Option);
        t.setOwner(user.getLogin());
            if (t.getPrice() > colletionWorker.getMaxPrice()){
                colletionWorker.addToCollection(t);
                ticketDAO.save(t);
                resultShell.checkResult("Новый эдемент добавлен в коллецию");
                return;
            }
            resultShell.checkResult("Новый элемент не был добавлен в коллекцию!");



    }
}
