package ru.nik.dbpackage;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.nik.businessLogic.ticketClasses.Coordinates;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.serverPackage.accounts.User;

public class HyberSessionFactory {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Ticket.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Coordinates.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }
}
