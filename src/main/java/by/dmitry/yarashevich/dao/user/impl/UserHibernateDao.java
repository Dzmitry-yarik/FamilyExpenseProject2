package by.dmitry.yarashevich.dao.user.impl;

import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class UserHibernateDao implements UserDao {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        UserHibernateDao userDao = new UserHibernateDao();
        userDao.createUser(new User("Jon", "Smit"));
    }

    @Override
    public void createUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> readAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<User> list = session.createQuery("from user", User.class).list();

            transaction.commit();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            return new ArrayList<>();
        }
    }

    @Override
    public User getUserById(int userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User userById = session.get(User.class, userId);
            transaction.commit();
            session.close();
            System.out.println();
            return userById;
        } catch (Exception e) {
            transaction.rollback();
            return new User();
        }
    }

    @Override
    public User getUserByName(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User userById = session.get(User.class, username);
            transaction.commit();
            session.close();
            System.out.println();
            return userById;
        } catch (Exception e) {
            transaction.rollback();
            return new User();
        }
    }

//    public List<Person> readAllPersonsNameStartWithAndAgeBetween(String prefixName, int minAge, int maxAge) {
//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            Criteria criteria = session.createCriteria(Person.class);
////            criteria.add();
//            criteria.add(Restrictions.and(
//                    Restrictions.ilike("name", prefixName + "%"),
//                    Restrictions.ge("age", minAge),
//                    Restrictions.le("age", maxAge)));
////            criteria.add(Restrictions.between("age", minAge, maxAge));
//
//            List<Person> list = criteria.list();
//            transaction.commit();
//            System.out.println(list);
//            return list;
//        } catch (Exception e) {
//            transaction.rollback();
//            return new ArrayList<Person>();
//        }
//    }

    @Override
    public void updateUser(User updatedUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(updatedUser);
            transaction.commit();             //подтверждаем изменения
        } catch (Exception e) {
            transaction.rollback();           //отказываем изменения
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(int userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User userById = session.get(User.class, userId);
            session.delete(userById);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }
}