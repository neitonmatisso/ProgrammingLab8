package ru.nik.businessLogic.commands;

import ru.nik.businessLogic.factories.Factory;
import ru.nik.businessLogic.factories.TicketFactory;
import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

/**
 * Команда , отвечающая за добавление нового элемента в коллекцию
 * Имеет в себе ссылку на реализацию класса-обертки над коллекцией
 */
public class AddElementCommand implements Command {
    private Factory factory;
    private ColletionWorker worker;
    private TicketDAO ticketDAO;


    /**
     * @param c - ControlUnit - класс , связывающий все команды в одной мапе
     * @param worker - Ccылка на класс-обертку для рабочей коллекцией
     */
    public AddElementCommand(ControlUnit c, ColletionWorker worker){
        factory = new TicketFactory();
        this.worker = worker;
        c.addNewCommand("add",this);
        c.addNewSettings(CommandType.OBJECT_COMMAND, "add");
        ticketDAO = new TicketDAO();
    }
    @Override
    public  synchronized void execute(String Option, ResultShell resultShell, User user) {
        Ticket t = factory.createTicketFromJson(Option);
        t.setOwner(user.getLogin());
        worker.addToCollection(t);
        ticketDAO.save(t);
        resultShell.checkResult("Новый элемент добавлен в коллекцию и сохранен в базу данных!");
    }


}
