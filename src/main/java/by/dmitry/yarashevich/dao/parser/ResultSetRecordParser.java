package by.dmitry.yarashevich.dao.parser;

import by.dmitry.yarashevich.dao.category.impl.CategoryJdbcMysqlDao;
import by.dmitry.yarashevich.dao.user.impl.UserJdbcMysqlDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetRecordParser implements ResultSetParser {
    @Override
    public <T> T parserObject(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        User user = new UserJdbcMysqlDao().getUserById(userId); // метод для получения объекта User по ID

        int categoryId = rs.getInt("category_id");
        ExpenseCategory category = new CategoryJdbcMysqlDao().getCategoryById(categoryId); // метод для получения объекта ExpenseCategory по ID

        return (T) ExpenseRecord.builder()
                .record_id(rs.getInt("record_id"))
                .name(rs.getString("name"))
                .amount(Double.parseDouble(rs.getString("amount")))
                .date(rs.getDate("date").toLocalDate())
                .user(user)
                .category(category)
                .build();
    }
}