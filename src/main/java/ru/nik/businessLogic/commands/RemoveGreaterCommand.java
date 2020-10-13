package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.factories.TicketFactory;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

import java.util.List;

/**
 * Команда, которая удаляет по определенному критерию.
 * Проблема в ненужном использовании метода .getCollection()
 * при первой же возможномти постараюсь исправить       
 */
public class RemoveGreaterCommand implements Command {
    private ColletionWorker colletionWorker;
    private ControlUnit controlUnit;
    private TicketDAO ticketDAO;
    public RemoveGreaterCommand(ControlUnit cu , ColletionWorker cw){
        controlUnit = cu;
        colletionWorker = cw;
        controlUnit.addNewCommand("remove_greater",this);
        controlUnit.addNewSettings(CommandType.OBJECT_COMMAND,"remove_greater");
        ticketDAO = new TicketDAO();

    }
    @Override
    public synchronized void execute(String Option , ResultShell resultShell, User user) { // TODO исправить говнокод
        Ticket t = new TicketFactory().createTicketFromJson(Option);
        List <Ticket> greaterList = colletionWorker.getGreater(t, user.getLogin());
        if(greaterList.isEmpty()){
            resultShell.checkResult("Не найдено ваших элементов со значением больше заданного");
            return;
        }
        for (Ticket ticket : greaterList){
            colletionWorker.removeTicket(ticket);
        }
        ticketDAO.delete(greaterList);
        resultShell.checkResult("Удаление успешно complete! Удалено объектов: " + greaterList.size());

    }
}
