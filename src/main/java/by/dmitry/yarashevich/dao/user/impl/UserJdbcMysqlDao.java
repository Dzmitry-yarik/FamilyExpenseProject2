package by.dmitry.yarashevich.dao.user.impl;

import by.dmitry.yarashevich.dao.parser.ResultSetUserParser;
import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.dao.util.MysqlUtil;
import by.dmitry.yarashevich.models.User;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserJdbcMysqlDao implements UserDao {

    private final Connection connection = MysqlUtil.getConnection();

    @Override
    public List<User> readAll() {
        return MysqlUtil.executeSqlReadQuery( "SELECT * FROM expense_project.user", new ResultSetUserParser());
    }
//или
//    @Override
//    public List<User> readAll() {
//        String sql = "SELECT * FROM expense_project.user";
//        ArrayList<User> users = MysqlUtil.executeSqlReadQuery(sql, new ResultSetUserParser());
//        return users;
//    }

    @Override
    public User get(int userId) {
        String sql = String.format("SELECT * FROM expense_project.user where user_id = %d", userId);
        ArrayList<User> users = MysqlUtil.executeSqlReadQuery(sql, new ResultSetUserParser());
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return new User();
    }

    @Override
    public User getUserByName(String userName) {
        String sql = String.format("SELECT * FROM expense_project.user where name = %s", userName);
        ArrayList<User> users = MysqlUtil.executeSqlReadQuery(sql, new ResultSetUserParser());
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return new User();
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO `expense_project`.`user` (`name`, `password`) VALUES (?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void create(List<User> userList) {
        //Statement для примера, почти всегда лучше использовать PreparedStatement
        try (Statement statement = connection.createStatement()) {
//  отключает автоматическое фиксирование транзакций, что позволяет выполнять несколько операций в рамках одной транзакции
            connection.setAutoCommit(false);
            try {
                for (User user : userList) {
                    String sql = String.format("INSERT INTO `expense_project`.`user` (`name`, `password`) VALUES ('%s', %s)", user.getName(), user.getPassword());
                    statement.executeUpdate(sql);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User updatedUser) {
        String sql = "UPDATE  `expense_project`.`user` SET `name` = ?, `password` = ? WHERE (`user_id` = ?)";
        MysqlUtil.executeSqlQueryTryWithResources(sql, updatedUser.getName(), updatedUser.getPassword(), updatedUser.getUser_id());
    }

    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM `expense_project`.`user` WHERE (`user_id` = ?)";
        MysqlUtil.executeSqlQueryTryWithResources(sql, userId);
    }
}
