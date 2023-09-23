package by.dmitry.yarashevich.dao.user.impl;

import by.dmitry.yarashevich.dao.parser.ResultSetUserParser;
import by.dmitry.yarashevich.dao.user.UserDao;
import by.dmitry.yarashevich.models.User;
import by.dmitry.yarashevich.dao.util.MysqlUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcMysqlDao implements UserDao {

    private Connection connection = MysqlUtil.getConnection();


    public static void main(String[] args) {
//        new PersonJdbcMysqlDao().incrementUserAge();
//        new UserJdbcMysqlDao().createUser(new User("zak", "31"));
        System.out.println( new UserJdbcMysqlDao().readAllUsers());

    }

//    public void incrementUserAge(){
//        String sql = "call increment_person_age";
//        Connection connection = null;
//        CallableStatement statement = null;
//        try {
//            //2. connection
//            connection = MysqlUtil.getConnection();
//            //3. statement
//            statement = connection.prepareCall(sql);
//            //4. execute sql query
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                statement.close();
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public List<User> readAllUsers() {
        String sql = "SELECT * FROM expense_project.user";

        ArrayList<User> users = MysqlUtil.executeSqlReadQuery(sql, new ResultSetUserParser());
        return users;
    }

    @Override
    public User getUserById(int userId) {
        String sql = String.format("SELECT * FROM expense_project.user where user_id = %d", userId);

        ArrayList<User> users = MysqlUtil.executeSqlReadQuery(sql, new ResultSetUserParser());
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return new User();
    }

    @Override
    public User getUserByName(String username) {
        String sql = String.format("SELECT * FROM expense_project.user where name = %s", username);

        ArrayList<User> users = MysqlUtil.executeSqlReadQuery(sql, new ResultSetUserParser());
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return new User();
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO `expense_project`.`user` (`name`, `password`) VALUES (?, ?)";

        PreparedStatement statement = null;
        try {

            //3. statement
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());

            //4. execute sql query
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createUser(List<User> personList) {
        try (Statement statement = connection.createStatement()) {

            //begin transaction in manually mode
            connection.setAutoCommit(false);
            try {
                for (User user : personList) {
                    String sql = String.format("INSERT INTO `expense_project`.`user` (`name`, `password`) VALUES ('%s', %s)", user.getName(), user.getPassword());

                    //4. execute sql query
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
    public void updateUser(User updatedUser) {
        StringBuilder sql = new StringBuilder("UPDATE  `expense_project`.`user` SET `name` = '" + updatedUser.getName() +
                "', `password` = '" + updatedUser.getPassword() + "' WHERE (`user_id` = '" + updatedUser.getUser_id() + "')");
        MysqlUtil.executeSqlQueryTryWithResources(String.valueOf(sql));
    }

    @Override
    public void deleteUser(int userId) {
        StringBuilder sql = new StringBuilder("DELETE FROM `expense_project`.`user` WHERE (`user_id` = '" + userId + "');");
        MysqlUtil.executeSqlQueryTryWithResources(String.valueOf(sql));
    }
}
