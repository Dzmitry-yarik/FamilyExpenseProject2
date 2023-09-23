package by.dmitry.yarashevich.dao.parser;

import by.dmitry.yarashevich.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetUserParser implements ResultSetParser {
    @Override
    public <T> T parserObject(ResultSet rs) throws SQLException {
        return (T) User.builder()
                .user_id(rs.getInt("user_id"))
                .name(rs.getString("name"))
                .password(rs.getString("password"))
                .build();
    }
}