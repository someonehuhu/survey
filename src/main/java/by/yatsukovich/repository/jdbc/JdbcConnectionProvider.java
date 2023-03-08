package by.yatsukovich.repository.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface JdbcConnectionProvider {
    Connection getConnection();

    default void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
