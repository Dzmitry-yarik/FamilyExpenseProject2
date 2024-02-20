package by.dmitry.yarashevich;

import by.dmitry.yarashevich.dao.category.impl.CategoryHibernateDao;
import by.dmitry.yarashevich.dao.category.impl.CategoryJdbcMysqlDao;
import by.dmitry.yarashevich.dao.user.impl.UserHibernateDao;
import by.dmitry.yarashevich.dao.user.impl.UserJdbcMysqlDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.User;

// http://localhost:8080/user?action=list - начальная
public class Main {
    public static void main(String[] args) {
        UserHibernateDao userDao = new UserHibernateDao();
        CategoryHibernateDao categoryHibernateDao = new CategoryHibernateDao();
//        System.out.println(userDao.get(1));
//        System.out.println(categoryHibernateDao.get(1));
//        System.out.println(categoryHibernateDao.readAll());
        UserJdbcMysqlDao userJdbcMysqlDao = new UserJdbcMysqlDao();
        userJdbcMysqlDao.update(new User(1,"Dima", "111"));
        CategoryJdbcMysqlDao categoryJdbcMysqlDao = new CategoryJdbcMysqlDao();
        categoryJdbcMysqlDao.update(new ExpenseCategory(1, "Продукты"));

    }
}
