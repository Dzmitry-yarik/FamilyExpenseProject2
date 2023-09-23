package by.dmitry.yarashevich.dao.user;

import by.dmitry.yarashevich.models.User;

import java.util.List;

public interface UserDao {
    void createUser(User user);
    List<User> readAllUsers();
    User getUserById(int userId);
    User getUserByName(String userName);
    void updateUser(User user);
    void deleteUser(int userId);
}
