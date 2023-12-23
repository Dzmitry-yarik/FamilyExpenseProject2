package by.dmitry.yarashevich.dao.util;

import by.dmitry.yarashevich.dao.parser.ResultSetParser;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;

public class MysqlUtil {
    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/expense_project";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASSWORD = "yarik1696";

    private static HikariDataSource dataSource = null;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(JDBC_USER);
        config.setPassword(JDBC_PASSWORD);
        config.addDataSourceProperty("minimumIdle", "5");
        config.addDataSourceProperty("maximumPoolSize", "25");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            connection = dataSource.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void executeSqlQueryTryWithResources(String sql) {
        try (Connection connection = MysqlUtil.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ArrayList<T> executeSqlReadQuery(String sql, ResultSetParser parser) {
        ArrayList<T> objectList = new ArrayList<>();
        try {
            Connection connection = MysqlUtil.getConnection();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                T object = (T) parser.parserObject(resultSet);
                objectList.add(object);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return objectList;
    }
}