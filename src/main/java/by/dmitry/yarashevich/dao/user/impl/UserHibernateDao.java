package by.dmitry.yarashevich.dao.user.impl;

import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.dao.util.HibernateUtil;
import by.dmitry.yarashevich.models.User;
import java.util.List;
import java.util.Optional;

public class UserHibernateDao implements UserDao {

    @Override
    public void create(User user) {
        HibernateUtil.executeTransaction(session -> {
            session.persist(user);
            return Optional.empty();
        });
    }

    @Override
    public List<User> readAll() {
        return HibernateUtil.executeTransaction(session ->
                session.createQuery("from User", User.class).getResultList());
    }

    @Override
    public User get(int userId) {
        return HibernateUtil.executeTransaction(session -> session.get(User.class, userId));
    }

    @Override
    public User getUserByName(String userName) {
        return HibernateUtil.executeTransaction(session ->
                session.createQuery("from User u where u.name = :name", User.class)
                        .setParameter("username", userName)
                        .uniqueResult());
    }

    @Override
    public void update(User updatedUser) {
        HibernateUtil.executeTransaction(session -> {
            session.merge(updatedUser);
            return Optional.empty();
        });
    }

    @Override
    public void delete(int userId) {
        HibernateUtil.executeTransaction(session -> {
            User userById = session.get(User.class, userId);
            if (userById != null) {
                session.remove(userById);
            }
            return Optional.empty();
        });
    }
}