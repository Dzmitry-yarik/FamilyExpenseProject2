package by.dmitry.yarashevich.dao.user;

import by.dmitry.yarashevich.dao.ObjectDao;
import by.dmitry.yarashevich.models.User;

import java.util.List;

public interface UserDao extends ObjectDao<User> {
    User getUserByName(String userName);

}
