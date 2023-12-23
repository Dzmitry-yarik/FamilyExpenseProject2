package by.dmitry.yarashevich.dao.util;

import by.dmitry.yarashevich.dao.user.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Настройте SessionFactory из hibernate.cfg.xml
        return new Configuration().configure().buildSessionFactory();
    }

    public static <R> R executeTransaction(Function<Session, R> action) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        R result = null;
        try {
            result = action.apply(session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return result;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}