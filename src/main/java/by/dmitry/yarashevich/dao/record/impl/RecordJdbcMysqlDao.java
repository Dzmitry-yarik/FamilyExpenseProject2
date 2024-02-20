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
import java.util.Optional;

public class RecordJdbcMysqlDao implements ExpenseRecordDao {

    private final Connection connection = MysqlUtil.getConnection();

    @Override
    public ExpenseRecord get(int recordId) {
        String sql = String.format("SELECT * FROM expense_project.expenserecord where record_id = %d", recordId);

        ArrayList<ExpenseRecord> records = MysqlUtil.executeSqlReadQuery(sql, new ResultSetRecordParser());
        if (CollectionUtils.isNotEmpty(records)) {
            return records.get(0);
        }
        return new ExpenseRecord();
    }

    @Override
    public Optional<List<ExpenseRecord>> getRecordsByCategory(ExpenseCategory category) {
        String sql = String.format("SELECT * FROM expense_project.expenserecord where category_id = %d", category.getCategory_id());

        ArrayList<ExpenseRecord> records = MysqlUtil.executeSqlReadQuery(sql, new ResultSetRecordParser());
        if (CollectionUtils.isNotEmpty(records)) {
            return Optional.of(records);
        }
        return Optional.empty();
    }

    @Override
    public List<ExpenseRecord> readAll() {
        String sql = "SELECT * FROM expense_project.expenserecord";

        ArrayList<ExpenseRecord> records = MysqlUtil.executeSqlReadQuery(sql, new ResultSetRecordParser());
        return records;
    }

    @Override
    public void create(ExpenseRecord record) {
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
    public void update(ExpenseRecord updateRecord) {
        String sql = "UPDATE  `expense_project`.`expenserecord` SET " +
                "`user_id` = ?, `category_id` = ?, `amount` = ?, `date` = ? WHERE (`record_id` = ?)";
        MysqlUtil.executeSqlQueryTryWithResources(sql, updateRecord.getUser(), updateRecord.getCategory(),
                updateRecord.getAmount(), updateRecord.getDate(),updateRecord.getRecord_id());
    }

    @Override
    public void delete(int recordId) {
        String sql = "DELETE FROM `expense_project`.`expenserecord` WHERE (`record_id` = ?)";
        MysqlUtil.executeSqlQueryTryWithResources(sql, recordId);
    }
}
