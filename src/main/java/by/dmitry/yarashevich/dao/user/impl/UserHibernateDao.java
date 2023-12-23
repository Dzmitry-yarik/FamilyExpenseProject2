package by.dmitry.yarashevich.dao.user.impl;

import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.dao.util.HibernateUtil;
import by.dmitry.yarashevich.models.User;
import java.util.List;

public class UserHibernateDao implements UserDao {

    private HibernateUtil hibernateUtil = new HibernateUtil();

    public static void main(String[] args) {
        UserHibernateDao userDao = new UserHibernateDao();
        userDao.createUser(new User("Jon", "Smit"));
    }

    @Override
    public void createUser(User user) {
        HibernateUtil.executeTransaction(session -> {
            session.save(user);
            return null;
        });
    }

    @Override
    public List<User> readAllUsers() {
        return HibernateUtil.executeTransaction(session ->
                session.createQuery("from user", User.class).getResultList());
    }

    @Override
    public User getUserById(int userId) {
        return HibernateUtil.executeTransaction(session -> session.get(User.class, userId));
    }

    @Override
    public User getUserByName(String username) {
        return HibernateUtil.executeTransaction(session ->
                session.createQuery("from user u where u.name = :name", User.class)
                        .setParameter("username", username)
                        .uniqueResult());
    }

    @Override
    public void updateUser(User updatedUser) {
        HibernateUtil.executeTransaction(session -> {
            session.update(updatedUser);
            return null;
        });
    }

    @Override
    public void deleteUser(int userId) {
        HibernateUtil.executeTransaction(session -> {
            User userById = session.get(User.class, userId);
            if (userById != null) {
                session.delete(userById);
            }
            return null;
        });
    }
}