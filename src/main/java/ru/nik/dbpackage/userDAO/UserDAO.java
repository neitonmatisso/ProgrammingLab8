package ru.nik.dbpackage.userDAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.HyberSessionFactory;
import ru.nik.serverPackage.accounts.User;

import javax.persistence.Query;
import java.util.List;

public class UserDAO implements UserDAOI {
    @Override
    public void addNewUser(User user) {
        Session session = HyberSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    @Override
    public List<User> loadUsers() {
        Session session = HyberSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createSQLQuery("SELECT * FROM users").addEntity(User.class);
        List<User> userList = query.getResultList();
        tx1.commit();
        session.close();
        return userList;

    }

    @Override
    public void update(User user) {

    }
}
