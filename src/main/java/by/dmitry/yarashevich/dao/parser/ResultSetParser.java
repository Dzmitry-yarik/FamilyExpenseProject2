package by.dmitry.yarashevich.dao.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetParser {
    <T> T parserObject(ResultSet resultSet) throws SQLException;
}
