package by.yatsukovich.repository.jdbc.mapper;

import by.yatsukovich.domain.User;
import by.yatsukovich.repository.jdbc.CRUDMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
//@Component
public class UserCRUDMapper implements CRUDMapper<User> {

    @Override
    public User mapRow(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public String[] getGenKeyNames() {
        return new String[]{"user_id"};
    }

    @Override
    public void prepareStatementForCreateOne(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getMail());
    }

    @Override
    public User updateGenKeys(ResultSet rs, User domain) throws SQLException {
        domain.setId(rs.getLong(1));
        return domain;
    }
}
