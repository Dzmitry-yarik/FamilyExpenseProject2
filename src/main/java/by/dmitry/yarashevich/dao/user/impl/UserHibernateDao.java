package by.dmitry.yarashevich.dao.user.impl;

import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.dao.util.HibernateUtil;
import by.dmitry.yarashevich.models.User;
import java.util.List;

public class UserHibernateDao implements UserDao {

    @Override
    public void createUser(User user) {
        HibernateUtil.executeTransaction(session -> {
            session.persist(user);
            return null;
        });
    }

    @Override
    public List<User> readAllUsers() {
        return HibernateUtil.executeTransaction(session ->
                session.createQuery("from User", User.class).getResultList());
    }

    @Override
    public User getUserById(int userId) {
        return HibernateUtil.executeTransaction(session -> session.get(User.class, userId));
    }

    @Override
    public User getUserByName(String username) {
        return HibernateUtil.executeTransaction(session ->
                session.createQuery("from User u where u.name = :name", User.class)
                        .setParameter("username", username)
                        .uniqueResult());
    }

    @Override
    public void updateUser(User updatedUser) {
        HibernateUtil.executeTransaction(session -> {
            session.merge(updatedUser);
            return null;
        });
    }

    @Override
    public void deleteUser(int userId) {
        HibernateUtil.executeTransaction(session -> {
            User userById = session.get(User.class, userId);
            if (userById != null) {
                session.remove(userById);
            }
            return null;
        });
    }
}