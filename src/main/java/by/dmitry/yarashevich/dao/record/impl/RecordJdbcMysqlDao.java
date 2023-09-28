package by.dmitry.yarashevich.dao.record.impl;

import by.dmitry.yarashevich.dao.parser.ResultSetRecordParser;
import by.dmitry.yarashevich.dao.record.ExpenseRecordDao;
import by.dmitry.yarashevich.dao.util.MysqlUtil;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordJdbcMysqlDao implements ExpenseRecordDao {

    private Connection connection = MysqlUtil.getConnection();


    public static void main(String[] args) {
        System.out.println(new RecordJdbcMysqlDao().readAllExpenseRecord());
    }

    @Override
    public ExpenseRecord getRecordById(int recordId) {
        String sql = String.format("SELECT * FROM expense_project.expenserecord where record_id = %d", recordId);

        ArrayList<ExpenseRecord> records = MysqlUtil.executeSqlReadQuery(sql, new ResultSetRecordParser());
        if (CollectionUtils.isNotEmpty(records)) {
            return records.get(0);
        }
        return new ExpenseRecord();
    }

    @Override
    public List<ExpenseRecord> getRecordsByCategory(ExpenseCategory category) {
        String sql = String.format("SELECT * FROM expense_project.expenserecord where category_id = %d", category.getCategory_id());

        ArrayList<ExpenseRecord> records = MysqlUtil.executeSqlReadQuery(sql, new ResultSetRecordParser());
        if (CollectionUtils.isNotEmpty(records)) {
            return records;
        }
        return new ArrayList<>();
    }

    @Override
    public List<ExpenseRecord> readAllExpenseRecord() {
        String sql = "SELECT * FROM expense_project.expenserecord";

        ArrayList<ExpenseRecord> records = MysqlUtil.executeSqlReadQuery(sql, new ResultSetRecordParser());
        return records;
    }

    @Override
    public void createRecord(ExpenseRecord record) {
        String sql = "INSERT INTO `expense_project`.`expenserecord` (`name`, `user`, `category`, `amount`, `date`) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(sql);
            statement.setString(1, record.getName());
            statement.setDouble(2, record.getAmount());
            statement.setDate(3, Date.valueOf(record.getDate()));
            statement.setObject(4, record.getUser());
            statement.setObject(5, record.getCategory());

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
    public void updateRecord(ExpenseRecord updateRecord) {
        StringBuilder sql = new StringBuilder("UPDATE  `expense_project`.`expenserecord` SET " +
                "`user_id` = '" + updateRecord.getUser() +
                "', `category_id` = '" + updateRecord.getCategory() +
                "', `amount` = '" + updateRecord.getAmount() +
                "', `date` = '" + updateRecord.getDate() +
                "' WHERE (`record_id` = '" + updateRecord.getRecord_id() + "')");
        MysqlUtil.executeSqlQueryTryWithResources(String.valueOf(sql));
    }

    @Override
    public void deleteRecord(int recordId) {
        StringBuilder sql = new StringBuilder("DELETE FROM `expense_project`.`expenserecord` " +
                "WHERE (`record_id` = '" + recordId + "');");
        MysqlUtil.executeSqlQueryTryWithResources(String.valueOf(sql));
    }
}
