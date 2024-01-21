package by.dmitry.yarashevich.services;

import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.dao.user.impl.UserHibernateDao;
import by.dmitry.yarashevich.models.User;

import java.util.List;

public class UserService{
    private final UserDao userDao = new UserHibernateDao();

    public UserService() {
    }

    public void createUser(int id, String name, String password) {
        boolean createUserFlag;
        do {
            if (id <= 0 ||name.isEmpty() || password.isEmpty()) {
                createUserFlag = false;
                System.out.println("Ошибка: все поля должны быть заполнены.");
            } else  createUserFlag = true;
        } while (!createUserFlag);
        User user = new User(id, name, password);
        userDao.create(user);
    }

    public void createUser(User user) {
        userDao.create(user);
    }

    public List<User> readAllUsers() {
        return userDao.readAll();
    }

    public User getUserById(int userId) {
        return userDao.get(userId);
    }

    public User getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }

    public void updateUser(int userIdParam, String updatedUserNameParam, String updatedUserPasswordParam) {
        User user = new User(userIdParam, updatedUserNameParam, updatedUserPasswordParam);
        updateUser(user);
    }
    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(int userId) {
        userDao.delete(userId);
    }
}
