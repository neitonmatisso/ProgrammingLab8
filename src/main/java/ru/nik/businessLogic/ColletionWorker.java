package ru.nik.businessLogic;

import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;

import java.util.List;

/**
 * Интерфейс для классов оберток над коллекциями
 */
public interface ColletionWorker{ //TODO дженерик, удалить ненужные команды;
    void addToCollection(Ticket t, User user);
    void setCollection(List<Ticket> l);
    void addToCollection(Ticket t);
    Ticket getById(long id);
    void removeById(long id, User user, ResultShell resultShell);
    List<Ticket> getCollection();
    List<Ticket> getByUserLogin(String s);
    List<Ticket> getGreater(Ticket t, String login);
    void removeByUserLogin (String s);
    void removeTicket(Ticket ticket);
    void clear();
    long getMaxPrice();
    long getMinPrice();
    void sort();
    Boolean isEmpty();
    String getInfo();
    String priceFilter(long pr);
    String recerve();
    String getAllJsonTickets();
    String Show();
}
