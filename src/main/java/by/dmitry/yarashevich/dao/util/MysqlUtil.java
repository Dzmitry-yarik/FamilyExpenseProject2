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
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void executeSqlQueryTryWithResources(String sql) {
        try (Connection connection = MysqlUtil.getConnection();
             Statement statement = connection.createStatement()) {
            //4. execute sql query
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ArrayList<T> executeSqlReadQuery(String sql, ResultSetParser parser) {
        ArrayList<T> objectList = new ArrayList<>();
        try {
            //2. connection
            Connection connection = MysqlUtil.getConnection();

            //3. statement
            Statement statement = connection.createStatement();

            //4. execute sql query
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

//        public static <T> ArrayList<T> executeSqlReadQuery(String sql) {
//        ArrayList<T> list = new ArrayList<>();
//        String type = "";
//        T object = null;
//        try {
//            Connection connection = MysqlUtil.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            if (obj instanceof String) {
//                System.out.println("Объект obj является экземпляром класса String.");
//            } else if (obj instanceof Integer) {
//                System.out.println("Объект obj является экземпляром класса Integer.");
//            } else {
//                System.out.println("Объект obj не является экземпляром ни одного из указанных классов.");
//            }
//
//            while (resultSet.next()) {
//
//                long id = resultSet.getLong(1);
//                T fieldFirst = (T) resultSet.getObject(2);
//                T fieldSecond = (T) resultSet.getObject(3);
//
//                switch (type) {
//                    case "person":
//                        object = (T) new Person(id, (String) fieldFirst, (int) fieldSecond);
//                        break;
//                    case "phone":
//                        object = (T) new Phone(id, (Integer) fieldFirst, (Integer) fieldSecond);
//                        break;
//                    case "passport":
//                        object = (T) new Passport(id, (Integer) fieldFirst, (String) fieldSecond);
//                        break;
//                    case "car":
//                        object = (T) new Car(id, (String) fieldFirst, (String) fieldSecond);
//                        break;
//                    default:
//                        System.out.println("Неизвестный объект");
//                }
//
//                list.add((T) object);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }
}