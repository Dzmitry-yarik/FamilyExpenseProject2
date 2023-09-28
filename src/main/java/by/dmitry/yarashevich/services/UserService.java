package by.dmitry.yarashevich.services;

import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.dao.user.impl.UserHibernateDao;
import by.dmitry.yarashevich.models.User;

import java.util.List;

public class UserService{
    private UserDao userDao = new UserHibernateDao();

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
        userDao.createUser(user);
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }

    public List<User> readAllUsers() {
        return userDao.readAllUsers();
    }

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public User getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }

    public void updatePerson(int userIdParam, String updatedUserNameParam, String updatedUserPasswordParam) {
        User user = new User(userIdParam, updatedUserNameParam, updatedUserPasswordParam);
        updateUser(user);
    }
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(int userId) {
        userDao.deleteUser(userId);
    }
}
