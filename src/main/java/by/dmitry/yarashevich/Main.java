package by.dmitry.yarashevich;

import by.dmitry.yarashevich.dao.user.impl.UserHibernateDao;

// http://localhost:8080/user?action=list - начальная
public class Main {
    public static void main(String[] args) {
        UserHibernateDao userDao = new UserHibernateDao();
        System.out.println(userDao.readAllUsers());
    }
}
