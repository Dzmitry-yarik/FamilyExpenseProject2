package by.dmitry.yarashevich.dao.category.impl;

import by.dmitry.yarashevich.dao.category.ExpenseCategoryDao;
import by.dmitry.yarashevich.dao.parser.ResultSetCategoryParser;
import by.dmitry.yarashevich.dao.util.MysqlUtil;
import by.dmitry.yarashevich.models.ExpenseCategory;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.dmitry.yarashevich.dao.util.MysqlUtil.executeSqlQueryTryWithResources;

public class CategoryJdbcMysqlDao implements ExpenseCategoryDao {

    private final Connection connection = MysqlUtil.getConnection();

    @Override
    public void create(ExpenseCategory category) {
        String sql = "INSERT INTO `expense_project`.`expensecategory` (`name`) VALUES (?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, category.getName());
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
    public List<ExpenseCategory> readAll() {
        String sql = "SELECT * FROM expense_project.expensecategory";
        ArrayList<ExpenseCategory> categories = MysqlUtil.executeSqlReadQuery(sql, new ResultSetCategoryParser());
        return categories;
    }

    @Override
    public ExpenseCategory get(int categoryId) {
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
    public void update(ExpenseCategory updateCategory) {
        String sql = "UPDATE `expense_project`.`expensecategory` SET `name` = ? WHERE `category_id` = ?";
        executeSqlQueryTryWithResources(sql, updateCategory.getName(), updateCategory.getCategory_id());
    }

    @Override
    public void delete(int categoryId) {
        String sql = "DELETE FROM `expense_project`.`expensecategory` WHERE `category_id` = ?";
        executeSqlQueryTryWithResources(sql, categoryId);
    }
}
