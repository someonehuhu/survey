package by.yatsukovich.repository.jdbc;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class CommonJdbcCRUDRepo implements JdbcCRUDRepo {
    private final JdbcConnectionProvider connectionProvider;

    public <T> T createOne(String createQuery, CRUDMapper<T> mapper, T domain) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionProvider.getConnection();
            statement = connection.prepareStatement(createQuery, mapper.getGenKeyNames());
            mapper.prepareStatementForCreateOne(statement, domain);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new RuntimeException("Failed to insert object :");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return mapper.updateGenKeys(resultSet, domain);
            } else {
                throw new RuntimeException("Failed to retrieve generated object id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionProvider.close(statement);
            connectionProvider.close(connection);
        }
    }
}
