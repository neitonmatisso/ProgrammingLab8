package ru.nik.businessLogic.commands;

import com.google.gson.Gson;
import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.factories.TicketFactory;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

/**
 * Обновляет значение билета с заданным id
 */
public class UpdateCommand implements Command{
    ColletionWorker colletionWorker;
    ControlUnit controlUnit;
    public UpdateCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("update",this);
        controlUnit.addNewSettings(CommandType.OBJECT_COMMAND,"update");
    }
    @Override
    public void execute(String Option, ResultShell resultShell, User user) {
        Ticket ticket = new Gson().fromJson(Option, Ticket.class);
        System.out.println(ticket);
        new TicketDAO().delete(colletionWorker.getById(ticket.getId()));
        colletionWorker.removeById(ticket.getId(),user,resultShell);
        colletionWorker.addToCollection(ticket);
        new TicketDAO().save(ticket);
    }
}
