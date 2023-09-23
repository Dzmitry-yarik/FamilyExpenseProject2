package by.dmitry.yarashevich.dao.category.impl;

import by.dmitry.yarashevich.dao.parser.ResultSetCategoryParser;
import by.dmitry.yarashevich.dao.category.ExpenseCategoryDao;
import by.dmitry.yarashevich.dao.util.MysqlUtil;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.User;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryJdbcMysqlDao implements ExpenseCategoryDao {

    private Connection connection = MysqlUtil.getConnection();

    public static void main(String[] args) {
//        new PersonJdbcMysqlDao().incrementUserAge();
//        new UserJdbcMysqlDao().createUser(new User("zak", "31"));
        System.out.println( new CategoryJdbcMysqlDao().readAllExpenseCategory());
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
    public void createCategory(ExpenseCategory category) {
        String sql = "INSERT INTO `expense_project`.`expensecategory` (`name`) VALUES (?)";

        PreparedStatement statement = null;
        try {

            //3. statement
            statement = connection.prepareStatement(sql);
            statement.setString(1, category.getName());

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

    @Override
    public List<ExpenseCategory> readAllExpenseCategory() {
        String sql = "SELECT * FROM expense_project.expensecategory";

        ArrayList<ExpenseCategory> categories = MysqlUtil.executeSqlReadQuery(sql, new ResultSetCategoryParser());
        return categories;
    }

    @Override
    public ExpenseCategory getCategoryById(int categoryId) {
        String sql = String.format("SELECT * FROM expense_project.expensecategory where category_id = %d", categoryId);

        ArrayList<ExpenseCategory> categories = MysqlUtil.executeSqlReadQuery(sql, new ResultSetCategoryParser());
        if (CollectionUtils.isNotEmpty(categories)) {
            return categories.get(0);
        }
        return new ExpenseCategory();
    }

    @Override
    public ExpenseCategory getCategoryByName(String categoryName) {
        String sql = String.format("SELECT * FROM expense_project.expensecategory where name = %s", categoryName);

        ArrayList<ExpenseCategory> categories = MysqlUtil.executeSqlReadQuery(sql, new ResultSetCategoryParser());
        if (CollectionUtils.isNotEmpty(categories)) {
            return categories.get(0);
        }
        return new ExpenseCategory();
    }

    @Override
    public void updateCategory(ExpenseCategory updateCategory) {
        StringBuilder sql = new StringBuilder("UPDATE  `expense_project`.`expensecategory` SET `name` = '" +
                updateCategory.getName() + "' WHERE (`category_id` = '" + updateCategory.getCategory_id() + "')");
        MysqlUtil.executeSqlQueryTryWithResources(String.valueOf(sql));
    }

    @Override
    public void deleteCategory(int categoryId) {
        StringBuilder sql = new StringBuilder("DELETE FROM `expense_project`.`expensecategory` WHERE (`category_id` = '" +
                categoryId + "');");
        MysqlUtil.executeSqlQueryTryWithResources(String.valueOf(sql));
    }
}
