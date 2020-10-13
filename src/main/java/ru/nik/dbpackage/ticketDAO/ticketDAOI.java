package ru.nik.dbpackage.ticketDAO;

import ru.nik.businessLogic.ticketClasses.Ticket;

import java.util.List;

public interface ticketDAOI {
    void save (Ticket ticket);
    void delete (Ticket ticket);
    void delete (List<Ticket> tickets);
    void update(Ticket ticket);
    List<Ticket> loadTickets();

}
