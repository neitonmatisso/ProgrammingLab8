package ru.nik.businessLogic.factories;

import ru.nik.businessLogic.ticketClasses.Ticket;

public interface Factory {
    Ticket createTicket();
    Ticket createTicketParams(String optinos);
    Ticket createTicketWithId(Long Id);
    Ticket createTicketFromJson(String json);
}
