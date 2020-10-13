package ru.nik.dbpackage.ticketDAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.HyberSessionFactory;

import javax.persistence.Query;
import java.util.List;

public class TicketDAO implements ticketDAOI {
    @Override
    public void save(Ticket ticket) {
        Session session = HyberSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(ticket);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Ticket ticket) {
        Session session = HyberSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(ticket);
        tx1.commit();
        session.close();

    }

    @Override
    public void delete(List<Ticket> tickets) {
        Session session = HyberSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for (Ticket ticket : tickets) {
            session.delete(ticket);
        }
        tx1.commit();
        session.close();

    }


    @Override
    public void update(Ticket ticket) {

    }

    @Override
    public List<Ticket> loadTickets() {
        Session session = HyberSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createSQLQuery("SELECT * FROM tickets").addEntity(Ticket.class);
        List<Ticket> ticketList = query.getResultList();
        tx1.commit();
        session.close();
        return ticketList;
    }
}
