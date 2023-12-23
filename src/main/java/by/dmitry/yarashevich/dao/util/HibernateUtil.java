package by.dmitry.yarashevich.dao.util;

import by.dmitry.yarashevich.dao.user.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Function;

public class HibernateUtil {
// http://localhost:8080/user?action=list - для запуска приложения
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static <T> T executeTransaction(Function<Session, T> action) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        T result;
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
}