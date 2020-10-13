package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

/**
 *  Команда осуществялет удалении объекта из коллекции по заданому id
 */
public class RemoveCommand implements Command{
    ColletionWorker colletionWorker;
    ControlUnit controlUnit;
    TicketDAO ticketDAO;
    public RemoveCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("remove_by_id",this);
        controlUnit.addNewSettings(CommandType.ARGS_COMMAND,"remove_by_id");
        ticketDAO = new TicketDAO();

    }

    /**
     *
     * @param Option нужный id. Передается в качестве строки. Для получение числового знаение парсится
     */
    @Override
    public  void execute(String Option, ResultShell resultShell, User user) {
        Ticket ticket = colletionWorker.getById(Long.parseLong(Option));
        if(ticket == null){
            resultShell.checkResult("Элемента с заданным id не сущетсвует");
            return;
        }
        if(!ticket.getOwner().equals(user.getLogin())){
            resultShell.checkResult("Вы не являетесь владельцем данного объекта!");
            return;
        } else if (ticket.getOwner().equals(user.getLogin())) {
            colletionWorker.removeTicket(ticket);
            ticketDAO.delete(ticket);
            resultShell.checkResult("Удаление прошло успешно!. Id удаленного объекта: " + ticket.getId());
        }
    }
}
