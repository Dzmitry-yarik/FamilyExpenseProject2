package by.dmitry.yarashevich.dao.parser;

import by.dmitry.yarashevich.models.ExpenseCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetCategoryParser implements ResultSetParser {
    @Override
    public <T> T parserObject(ResultSet rs) throws SQLException {
        return (T) ExpenseCategory.builder()
                .category_id(rs.getInt("category_id"))
                .name(rs.getString("name"))
                .build();
    }
}