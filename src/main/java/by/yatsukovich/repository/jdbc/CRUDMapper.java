package by.yatsukovich.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
public interface CRUDMapper<T> {

    T mapRow(ResultSet rs) throws SQLException;

    String[] getGenKeyNames();

    void prepareStatementForCreateOne(PreparedStatement statement, T domain) throws SQLException;

    T updateGenKeys(ResultSet rs, T domain) throws SQLException;

}
