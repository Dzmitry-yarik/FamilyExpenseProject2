package by.dmitry.yarashevich.dao.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.function.Function;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
//        return new Configuration().configure(new File("src/main/resources/hibernate.cfg.xml")).buildSessionFactory();
        return new Configuration().configure(new File("C:/Users/Professional/IdeaProjects/FamilyExpenseProject/src/main/resources/hibernate.cfg.xml")).buildSessionFactory();

    }

    public static <T> T executeTransaction(Function<Session, T> action) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        T result;
        try {
            result = action.apply(session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return result;
    }
}